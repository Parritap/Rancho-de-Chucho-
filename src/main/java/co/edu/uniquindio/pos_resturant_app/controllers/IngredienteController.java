package co.edu.uniquindio.pos_resturant_app.controllers;

import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteReadDTO;
import co.edu.uniquindio.pos_resturant_app.dto.unidadMedida.UnidadMedidaReadDTO;
import co.edu.uniquindio.pos_resturant_app.dto.web.MensajeDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.CascadeEffectException;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;
import co.edu.uniquindio.pos_resturant_app.model.UnidadMedida;
import co.edu.uniquindio.pos_resturant_app.repository.UnidadMedidaRepo;
import co.edu.uniquindio.pos_resturant_app.services.specifications.IngredienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.ExecChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingrediente")
@Slf4j
public class IngredienteController {

    private final IngredienteService ingredienteService;
    private final UnidadMedidaRepo unidadMedidaRepo;

    /**
     * saveProduct()
     * # Mapea la funcion IntentoryService.saveProduct() en el front end
     * Más especificamente si la unidad de medida indicada por alguna razón no existe en la base de datos
     *
     * @param dto
     * @return MensajeDTO con el id del ingrediente creado
     * @throws RecordNotFoundException si la unidad de medida no existe
     * @throws Exception               si ocurre un error inesperado
     */
    @PostMapping("/save")
    public ResponseEntity<MensajeDTO<Integer>> create(@Valid @RequestBody IngredienteCreateDTO dto) {
        try {
            int entityId = ingredienteService.create(dto);
            if (entityId > 0) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new MensajeDTO<>(false, entityId));
            } else {
                return ResponseEntity.badRequest()
                        .body(new MensajeDTO<>(true, -1));
            }
        } catch (RecordNotFoundException e) {
            log.error("No existe una unidad de medida con el id: {}", dto.unidad_medida());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MensajeDTO<>(true, -1));
        } catch (Exception e) {
            log.error("Error inesperado creando ingrediente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MensajeDTO<>(true, -1));
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
        ingredienteService.addStock(id, cantidad);
        return ResponseEntity.ok(new MensajeDTO<>(true, false));
    }

    // editProduct()
    // # Mapea la funcion IntentoryService.editProduct() en el front end
    @PutMapping("/{id}/update")
    public ResponseEntity<MensajeDTO<Boolean>> update(@PathVariable int id, @Valid @RequestBody IngredienteCreateDTO dto) throws Exception {
        boolean updated = ingredienteService.update(dto, id);
        return ResponseEntity.ok(new MensajeDTO<>(true, false));
    }

    /**
     * Method for deleting an ingredient. And ingredient can only be deleted if it is not used in any recipe.
     *
     * @param id
     * @return MensajeDTO with a boolean indicating if the ingredient was deleted or not
     * @throws RecordNotFoundException if the ingredient with specified id does not exist
     * @throws CascadeEffectException  if the ingredient is used in any recipe
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MensajeDTO<Boolean>> delete(@PathVariable Integer id) throws Exception {
        ingredienteService.delete(id);
        return ResponseEntity.ok(new MensajeDTO<>(true, true));
    }

    @GetMapping("/getAll")
    public ResponseEntity<MensajeDTO<List<IngredienteReadDTO>>> getAll() {
        var ingredientes = ingredienteService.getAll();
        return ResponseEntity.ok().body(new MensajeDTO<>(false, ingredientes));
    }

    @GetMapping("/getUnidadMedida")
    public ResponseEntity<MensajeDTO<List<UnidadMedida>>> getAllUnidaddes() {
        var unidades = unidadMedidaRepo.findAll().stream().map(UnidadMedida::toUnidadMedida).toList();
        return ResponseEntity.ok().body(new MensajeDTO<>(false, unidades));
    }
}


