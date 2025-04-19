package co.edu.uniquindio.pos_resturant_app.controllers;

import co.edu.uniquindio.pos_resturant_app.dto.plato.PlatoCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.plato.PlatoReadDTO;
import co.edu.uniquindio.pos_resturant_app.dto.web.MensajeDTO;
import co.edu.uniquindio.pos_resturant_app.repository.TipoPlatoRepo;
import co.edu.uniquindio.pos_resturant_app.services.specifications.PlatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plato")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class PlatoController {

    private final PlatoService platoService;
    private final TipoPlatoRepo tipoPlatoRepo;

    @PostMapping("/save")
    public ResponseEntity<MensajeDTO<Integer>> create(@Valid @RequestBody PlatoCreateDTO dto) {

        if (tipoPlatoRepo.findById(dto.id_tipo_plato()).isEmpty()) {
            log.error("No existe un tipo de plato con el id: {}", dto.id_tipo_plato());
            return ResponseEntity.status(400)
                    .body(new MensajeDTO<>(true, -1, "No existe un tipo de plato con el id: " + dto.id_tipo_plato()));
        }

        try {
            int entityId = platoService.create(dto);
            if (entityId > 0) {
                return ResponseEntity.status(201)
                        .body(new MensajeDTO<>(false, entityId));
            } else {
                return ResponseEntity.badRequest()
                        .body(new MensajeDTO<>(true, -1));
            }
        } catch (Exception e) {
            log.error("Error inesperado creando plato: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, -1));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<MensajeDTO<Boolean>> update(@Valid @RequestBody PlatoCreateDTO dto, @PathVariable Integer id) {
        if (tipoPlatoRepo.findById(dto.id_tipo_plato()).isEmpty()) {
            log.error("No existe un tipo de plato con el id: {}", dto.id_tipo_plato());
            return ResponseEntity.status(400)
                    .body(new MensajeDTO<>(true, false, "No existe un tipo de plato con el id: " + dto.id_tipo_plato()));
        }
        try {
            platoService.update(dto, id);
        } catch (Exception e) {
            log.error("Error inesperado actualizando plato, revise si el plato existe en la base de datos: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, false));
        }
        //Ok
        return ResponseEntity.ok(new MensajeDTO<>(false, true));
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MensajeDTO<Boolean>> logicalDelete(@PathVariable Integer id) {
        try {
            platoService.logicalDelete(id);
        } catch (Exception e) {
            log.error("Error inesperado eliminando plato, revise si el plato con id {} existe en la base de datos: {}", id, e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, false));
        }
        //Ok
        return ResponseEntity.ok(new MensajeDTO<>(false, true));
    }

    @GetMapping("{id}/find")
    public ResponseEntity<MensajeDTO<PlatoCreateDTO>> findById(@PathVariable Integer id) {
        try {
            PlatoCreateDTO plato = platoService.findById(id);
            return ResponseEntity.ok(new MensajeDTO<>(false, plato));
        } catch (Exception e) {
            log.error("Error inesperado buscando plato, revise si el plato con id {} existe en la base de datos: {}", id, e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<MensajeDTO<List<PlatoReadDTO>>> getAll() {
        try {
            var listaPlatos = platoService.getAll();
            return ResponseEntity.ok(new MensajeDTO<>(false, listaPlatos));
        } catch (Exception e) {
            log.error("Error inesperado obteniendo platos: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, null));
        }
    }

}
