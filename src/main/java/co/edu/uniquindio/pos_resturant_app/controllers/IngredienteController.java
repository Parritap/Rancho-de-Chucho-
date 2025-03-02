package co.edu.uniquindio.pos_resturant_app.controllers;

import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.web.MensajeDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;
import co.edu.uniquindio.pos_resturant_app.services.specifications.IngredienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingrediente")
@Slf4j
public class IngredienteController {

    private final IngredienteService ingredienteService;

    /**
     * saveProduct()
     * # Mapea la funcion IntentoryService.saveProduct() en el front end
     * Más especificamente si la unidad de medida indicada por alguna razón no existe en la base de datos
     *
     * @param dto
     * @return MensajeDTO con el id del ingrediente creado
     */
    @PostMapping("/save")
    public ResponseEntity<MensajeDTO<Integer>> create(@Valid @RequestBody IngredienteCreateDTO dto) {
        try {
            int entityId = ingredienteService.create(dto);
            if (entityId > 0) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new MensajeDTO<>(true, entityId));
            } else {
                return ResponseEntity.badRequest()
                        .body(new MensajeDTO<>(false, -1));
            }
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeDTO<>(false, -1));
        } catch (Exception e) {
            log.error("Error inesperado creando ingrediente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MensajeDTO<>(false, -1));
        }
    }

    /**
     * addToStock()
     * Mapea la funcion IntentoryService.addToStock() en el front end
     * Yo le llamé editStock porque no se si se va a restar o sumar, pero al fin y al cabo se va a editar el stock
     * Tambien me tomé la libertad de cambiar el nombre de la ruta para que sea algo más semantica
     *
     * @param id
     * @param cantidad
     * @return
     */
    @PutMapping("/updateStock/{id}/{cantidad}/")
    public ResponseEntity<MensajeDTO<Boolean>> editStock(@PathVariable String id, @PathVariable int cantidad) throws Exception {
        var succcess = ingredienteService.editStock(id, cantidad);
        if (succcess) {
            return ResponseEntity.ok(new MensajeDTO<>(true, false));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // editProduct()
    // # Mapea la funcion IntentoryService.editProduct() en el front end
    @PutMapping("/{id}/update")
    public ResponseEntity<MensajeDTO<Boolean>> update(@PathVariable int id, @Valid @RequestBody IngredienteCreateDTO dto) {
        try {
            boolean updated = ingredienteService.update(dto, id);
            if (updated) {
                return ResponseEntity.ok(new MensajeDTO<>(true, false));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeDTO<>(false, false));
        } catch (Exception e) {
            log.error("Error inesperado actualizando ingrediente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MensajeDTO<>(false, false));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MensajeDTO<Boolean>> delete(@PathVariable String id) {
        try {
            ingredienteService.delete(id);
            return ResponseEntity.ok(new MensajeDTO<>(false, true));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeDTO<>(false, false));
        } catch (Exception e) {
            log.error("Error inesperado eliminando ingrediente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MensajeDTO<>(false, false));
        }
    }


}
