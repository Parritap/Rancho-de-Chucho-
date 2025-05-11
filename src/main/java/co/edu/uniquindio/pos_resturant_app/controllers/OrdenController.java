package co.edu.uniquindio.pos_resturant_app.controllers;

import co.edu.uniquindio.pos_resturant_app.dto.orden.OrdenCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.orden.OrdenReadDTO;
import co.edu.uniquindio.pos_resturant_app.dto.orden.PlatoOrdenadoDTO;
import co.edu.uniquindio.pos_resturant_app.dto.web.MensajeDTO;
import co.edu.uniquindio.pos_resturant_app.model.Mesero;
import co.edu.uniquindio.pos_resturant_app.model.Orden;
import co.edu.uniquindio.pos_resturant_app.model.Plato;
import co.edu.uniquindio.pos_resturant_app.model.enums.EstadoOrden;
import co.edu.uniquindio.pos_resturant_app.model.joints.OrdenPlato;
import co.edu.uniquindio.pos_resturant_app.model.keys.OrdenPlatoID;
import co.edu.uniquindio.pos_resturant_app.repository.MesaRepo;
import co.edu.uniquindio.pos_resturant_app.repository.MeseroRepo;
import co.edu.uniquindio.pos_resturant_app.repository.OrdenRepo;
import co.edu.uniquindio.pos_resturant_app.repository.PlatoRepo;
import co.edu.uniquindio.pos_resturant_app.repository.joints.OrdenPlatoRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orden")
@Slf4j
public class OrdenController {
    private final OrdenRepo ordenRepo;
    private final MesaRepo mesaRepo;
    private final MeseroRepo meseroRepo;
    private final OrdenPlatoRepo ordenPlatoRepo;
    private final PlatoRepo platoRepo;
    /**
     * Metodo para guardar la información de una nueva orden
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<MensajeDTO<Integer>> create(@RequestBody @Valid OrdenCreateDTO dto) {
        log.info("Creando una nueva orden para la mesa {} y mesero {}", dto.idMesa(), dto.cedulaMesero());

        try {
            var mesaOpt = mesaRepo.findById(dto.idMesa());
            if (mesaOpt.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new MensajeDTO<>(true, -1, "No existe la mesa especificada"));
            }

            var mesero = meseroRepo.findByCedula(dto.cedulaMesero());
            if (mesero == null) {
                return ResponseEntity.badRequest()
                        .body(new MensajeDTO<>(true, -1, "No existe el mesero especificado"));
            }

            BigDecimal subtotal = BigDecimal.ZERO;

            for (OrdenCreateDTO.PlatilloCantidadDTO platillo : dto.platillos()) {
                var plato = platoRepo.findByNombre(platillo.nombre());
                if (plato == null) {
                    return ResponseEntity.badRequest()
                            .body(new MensajeDTO<>(true, -1, "No existe el platillo: " + platillo.nombre()));
                }

                BigDecimal precio = BigDecimal.valueOf(plato.get(0).getPrecio());
                BigDecimal cantidad = BigDecimal.valueOf(platillo.cantidad());
                subtotal = subtotal.add(precio.multiply(cantidad));
            }

            BigDecimal impuestos = subtotal.multiply(BigDecimal.valueOf(0.19));
            Orden nuevaOrden = Orden.builder()
                    .mesa(mesaOpt.get())
                    .mesero((Mesero) mesero)
                    .fechaInicio(LocalDateTime.now())
                    .subtotal(subtotal)
                    .impuestos(impuestos)
                    .estado(EstadoOrden.ESPERA)
                    .build();

            Orden ordenGuardada = ordenRepo.save(nuevaOrden);  // ← Aquí se guarda la orden

            // 🔽 🔽 Guardamos los platos asociados a la orden
            for (OrdenCreateDTO.PlatilloCantidadDTO platilloDTO : dto.platillos()) {
                Plato plato = platoRepo.findByNombre(platilloDTO.nombre()).get(0); // ya validado antes

                OrdenPlatoID id = new OrdenPlatoID(ordenGuardada.getIdOrden(), plato.getId_plato());

                OrdenPlato ordenPlato = new OrdenPlato();
                ordenPlato.setId(id);
                ordenPlato.setOrden(ordenGuardada);  // ← usamos ordenGuardada
                ordenPlato.setPlato(plato);
                ordenPlato.setCantidad(platilloDTO.cantidad());

                ordenPlatoRepo.save(ordenPlato);
            }

            return ResponseEntity.status(201)
                    .body(new MensajeDTO<>(false, ordenGuardada.getIdOrden(), "Orden creada con éxito"));

        } catch (Exception e) {
            log.error("Error inesperado creando orden: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, -1, "Error inesperado creando orden: " + e.getMessage()));
        }
    }



    @GetMapping("/orden/cerrar/{idOrden}")
    public ResponseEntity<MensajeDTO<Boolean>> close(@PathVariable("idOrden") @NotNull Integer idOrden) {
        log.info("Cerrando la orden con ID {}", idOrden);
        try {
            var ordenOpt = ordenRepo.findById(idOrden);
            if (ordenOpt.isEmpty()) {
                log.error("No se encontró la orden con ID {}", idOrden);
                return ResponseEntity.badRequest()
                        .body(new MensajeDTO<>(true, false, "No existe la orden especificada"));
            }

            Orden orden = ordenOpt.get();
            orden.setFechaCierre(LocalDateTime.now());
            ordenRepo.save(orden);

            log.info("Orden cerrada con éxito");
            return ResponseEntity.ok(new MensajeDTO<>(false, true, "Orden cerrada con éxito"));

        } catch (Exception e) {
            log.error("Error inesperado cerrando orden: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, false, "Error inesperado cerrando orden: " + e.getMessage()));
        }
    }

    //Metodo para obtener todas las ordenes

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
