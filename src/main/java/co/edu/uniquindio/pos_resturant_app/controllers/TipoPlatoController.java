package co.edu.uniquindio.pos_resturant_app.controllers;

import co.edu.uniquindio.pos_resturant_app.dto.tipoPlato.TipoPlatoCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.web.MensajeDTO;
import co.edu.uniquindio.pos_resturant_app.model.TipoPlato;
import co.edu.uniquindio.pos_resturant_app.repository.TipoPlatoRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tipoPlato")
@Slf4j
public class TipoPlatoController {

    private final TipoPlatoRepo tipoPlatoRepo;


    @PostMapping("/save")
    public ResponseEntity<MensajeDTO<Integer>> create(@Valid @RequestBody TipoPlatoCreateDTO dto) {
        try {
            if (tipoPlatoRepo.findByNombre(dto.nombre()) != null) {
                log.error("Ya existe un tipo de plato con ese nombre");
                return ResponseEntity.status(400)
                        .body(new MensajeDTO<>(true, -1, "Ya existe un tipo de plato con ese nombre"));
            }
            int entityId = tipoPlatoRepo.save(dto.toEntity()).getId_tipo_plato();
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
    public ResponseEntity<MensajeDTO<Boolean>> update(@Valid @RequestBody TipoPlatoCreateDTO dto, @PathVariable Integer id) {
        try {
            tipoPlatoRepo.findById(id).orElseThrow(() -> new Exception("Tipo de plato no encontrado"));
            tipoPlatoRepo.save(dto.toEntity());
        } catch (Exception e) {
            log.error("Error inesperado actualizando plato, revise si el plato existe en la base de datos: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, false, "Error inesperado actualizando plato"));
        }
        //Ok
        return ResponseEntity.ok(new MensajeDTO<>(false, true));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MensajeDTO<Boolean>> delete(@PathVariable Integer id) {
        try {
            tipoPlatoRepo.deleteById(id);
        } catch (Exception e) {
            log.error("Error inesperado eliminando plato, revise si el plato con id {} existe en la base de datos: {}", id, e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, false, "Error inesperado eliminando plato"));
        }
        //Ok
        return ResponseEntity.ok(new MensajeDTO<>(false, true));
    }

    @GetMapping("getAll")
    public ResponseEntity<MensajeDTO<List<TipoPlatoCreateDTO>>> getAll() {
        try {
            var tipoPlatos = tipoPlatoRepo.findAll().stream().map(TipoPlato::toTipoPlatoCreateDTO).toList();
            return ResponseEntity.ok(new MensajeDTO<>(false, tipoPlatos));
        } catch (Exception e) {
            log.error("Error inesperado buscando todos los tipos de plato: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new MensajeDTO<>(true, null, "Error inesperado buscando todos los tipos de plato"));
        }
    }
}
