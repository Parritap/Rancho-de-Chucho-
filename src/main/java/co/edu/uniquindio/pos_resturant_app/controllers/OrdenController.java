package co.edu.uniquindio.pos_resturant_app.controllers;

import co.edu.uniquindio.pos_resturant_app.dto.web.MensajeDTO;
import co.edu.uniquindio.pos_resturant_app.model.Mesero;
import co.edu.uniquindio.pos_resturant_app.model.Orden;
import co.edu.uniquindio.pos_resturant_app.repository.MesaRepo;
import co.edu.uniquindio.pos_resturant_app.repository.MeseroRepo;
import co.edu.uniquindio.pos_resturant_app.repository.OrdenRepo;
import co.edu.uniquindio.pos_resturant_app.repository.joints.OrdenPlatoRepo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orden")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class OrdenController {
    private final OrdenRepo ordenRepo;
    private final MesaRepo mesaRepo;
    private final MeseroRepo meseroRepo;
    private final OrdenPlatoRepo ordenPlatoRepo;

    @PostMapping("/mesa/{numeroMesa}/mesero/{cedulaMesero}")
    public ResponseEntity<MensajeDTO<Integer>> create(@PathVariable @NotNull Integer numeroMesa,
                                                      @PathVariable @NotNull String cedulaMesero) {
        log.info("Creando una nueva orden para la mesa {} y mesero {}", numeroMesa, cedulaMesero);
        try {
            var mesaOpt = mesaRepo.findById(numeroMesa);
            if (mesaOpt.isEmpty()) {
                log.error("No se encontró la mesa con número {}", numeroMesa);
                return ResponseEntity.badRequest()
                        .body(new MensajeDTO<>(true, -1, "No existe la mesa especificada"));
            }

            var mesero = meseroRepo.findByCedula(cedulaMesero);
            if (mesero == null) {
                log.error("No se encontró el mesero con cédula {}", cedulaMesero);
                return ResponseEntity.badRequest()
                        .body(new MensajeDTO<>(true, -1, "No existe el mesero especificado"));
            }

            Orden nuevaOrden = Orden.builder()
                    .mesa(mesaOpt.get())
                    .mesero((Mesero) mesero)
                    .fechaInicio(LocalDateTime.now())
                    .build();

            Orden ordenGuardada = ordenRepo.save(nuevaOrden);
            Integer idOrden = ordenGuardada.getIdOrden(); // Assuming getId() method exists

            log.info("Orden creada con éxito con ID: {}", idOrden);
            return ResponseEntity.status(201)
                    .body(new MensajeDTO<>(false, idOrden, "Orden creada con éxito"));

        } catch (Exception e) {
            log.error("Error inesperado creando orden: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, -1, "Error inesperado creando orden: " + e.getMessage()));
        }
    }


   public Responseentity<mensajedto<boolean>> close(@pathvariable @notnull Integer idOrden) {
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
}
