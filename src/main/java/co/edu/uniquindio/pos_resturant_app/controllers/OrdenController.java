package co.edu.uniquindio.pos_resturant_app.controllers;

import co.edu.uniquindio.pos_resturant_app.dto.orden.OrdenCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.orden.OrdenReadDTO;
import co.edu.uniquindio.pos_resturant_app.dto.orden.PlatoOrdenadoDTO;
import co.edu.uniquindio.pos_resturant_app.dto.web.MensajeDTO;
import co.edu.uniquindio.pos_resturant_app.model.Orden;
import co.edu.uniquindio.pos_resturant_app.model.enums.EstadoOrden;
import co.edu.uniquindio.pos_resturant_app.repository.MesaRepo;
import co.edu.uniquindio.pos_resturant_app.repository.MeseroRepo;
import co.edu.uniquindio.pos_resturant_app.repository.OrdenRepo;
import co.edu.uniquindio.pos_resturant_app.repository.PlatoRepo;
import co.edu.uniquindio.pos_resturant_app.repository.joints.OrdenPlatoRepo;
import co.edu.uniquindio.pos_resturant_app.services.specifications.OrdenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orden")
@Transactional
@Slf4j
public class OrdenController {
    private final OrdenRepo ordenRepo;
    private final MesaRepo mesaRepo;
    private final MeseroRepo meseroRepo;
    private final OrdenPlatoRepo ordenPlatoRepo;
    private final PlatoRepo platoRepo;

    private final OrdenService ordenService;

    /**
     * Metodo para guardar la información de una nueva orden
     *
     * @param dto
     * @return
     */

    // TODO Reimplementar. i.e. Desacoplar codigo. Deal with exceptions.
    @PostMapping("/open")
    public ResponseEntity<MensajeDTO<Integer>> open(@RequestBody @Valid OrdenCreateDTO dto) {
        log.info("Creando una nueva orden para la mesa {} y mesero {}", dto.idMesa(), dto.cedulaMesero());
        var idOrden = ordenService.openOrden(dto);
        return ResponseEntity.status(200).body(
                new MensajeDTO<>(false, idOrden, "Orden creada con exito")
        );
    }


    @GetMapping("/orden/cerrar/{idOrden}")
    public ResponseEntity<MensajeDTO<Boolean>> close(@PathVariable("idOrden") @NotNull Integer idOrden) {
        log.info("Cerrando la orden con ID {}", idOrden);
        ordenService.closeOrden(idOrden);
        return ResponseEntity.status(200).body(
                new MensajeDTO<>(false, true, "La orden ha sido cerrada con éxito")
        );
    }



    //Metodo para obtener todas las ordenes
    //TODO Paginar las ordenes.
    @GetMapping("/getAll")
    public ResponseEntity<MensajeDTO<List<OrdenReadDTO>>> getAll() {
        try {
            List<OrdenReadDTO> dtoList = ordenRepo.findAll().stream()
                    .map(orden -> new OrdenReadDTO(
                            orden.getIdOrden(),
                            orden.getFechaInicio(),
                            orden.getSubtotal(),
                            orden.getImpuestos(),
                            orden.getMesa().getId(),
                            orden.getMesero().getCedula(),
                            orden.getEstado(),
                            orden.getListaDetalles().stream()
                                    .map(ordenPlato -> new PlatoOrdenadoDTO(
                                            ordenPlato.getPlato().getNombre(),
                                            ordenPlato.getCantidad(),
                                            ordenPlato.getPlato().getPrecio() // Asegúrate que Plato tenga getPrecio()
                                    )).toList()
                    ))
                    .toList();

            return ResponseEntity.ok(new MensajeDTO<>(false, dtoList, "Órdenes obtenidas correctamente"));
        } catch (Exception e) {
            log.error("Error al obtener las órdenes: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, null, "Error al obtener las órdenes: " + e.getMessage()));
        }
    }

    @PutMapping("/cancelar/{idOrden}")
    public ResponseEntity<MensajeDTO<Boolean>> cancelarOrden(@PathVariable("idOrden") @NotNull Integer idOrden) {
        log.info("Cancelando la orden con ID {}", idOrden);

        try {
            var ordenOpt = ordenRepo.findById(idOrden);
            if (ordenOpt.isEmpty()) {
                log.error("No se encontró la orden con ID {}", idOrden);
                return ResponseEntity.badRequest()
                        .body(new MensajeDTO<>(true, false, "No existe la orden especificada"));
            }

            Orden orden = ordenOpt.get();

            if (orden.getEstado() == EstadoOrden.FINALIZADA || orden.getEstado() == EstadoOrden.CANCELADA) {
                return ResponseEntity.badRequest()
                        .body(new MensajeDTO<>(true, false, "No se puede cancelar una orden finalizada o ya cancelada"));
            }

            orden.setEstado(EstadoOrden.CANCELADA);
            orden.setFechaCierre(LocalDateTime.now());

            ordenRepo.save(orden);

            log.info("Orden cancelada correctamente");
            return ResponseEntity.ok(new MensajeDTO<>(false, true, "Orden cancelada correctamente"));

        } catch (Exception e) {
            log.error("Error inesperado cancelando orden: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, false, "Error inesperado cancelando orden: " + e.getMessage()));
        }
    }


}
