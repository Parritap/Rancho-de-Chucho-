package co.edu.uniquindio.pos_resturant_app.services.specifications;

import co.edu.uniquindio.pos_resturant_app.dto.joints.IngredientesPlatoDTO;
import co.edu.uniquindio.pos_resturant_app.dto.plato.PlatoCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.plato.PlatoReadDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.CascadeEffectException;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;

import java.util.List;

public interface PlatoService {

    /**
     * Creates a new dish
     *
     * @param plato DTO with dish data
     * @return The ID of the created dish
     * @throws Exception If there's an error during creation
     */
    Integer create(PlatoCreateDTO plato) throws Exception;

    /**
     * Updates an existing dish
     *
     * @param plato DTO with updated dish data
     * @param id    ID of the dish to update
     * @return true if updated successfully, false if dish not found
     * @throws Exception If there's an error during update
     */
    boolean update(PlatoCreateDTO plato, int id) throws Exception;

    /**
     * Deletes a dish by its ID
     *
     * @param id ID of the dish to be deleted
     * @return true if deleted successfully, false if the dish was not found
     * @throws CascadeEffectException  If deletion would cause cascade effects
     * @throws RecordNotFoundException If dish with given ID doesn't exist
     * @throws Exception               For other errors
     */
    boolean delete(Integer id) throws CascadeEffectException, RecordNotFoundException, Exception;

    boolean logicalDelete(Integer id) throws RecordNotFoundException, Exception;

    /**
     * Finds a dish by its ID
     *
     * @param id ID of the dish to find
     * @return DTO with dish data
     * @throws RecordNotFoundException If dish with given ID doesn't exist
     * @throws Exception               For other errors
     */
    PlatoReadDTO findById(Integer id) throws RecordNotFoundException, Exception;

    /**
     * Gets all dishes
     *
     * @return List of all dishes
     * @throws Exception If there's an error retrieving dishes
     */
    List<PlatoReadDTO> getAll() throws Exception;

    /**
     * Changes the availability status of a dish
     *
     * @param id         ID of the dish
     * @param disponible New availability status
     * @return true if updated successfully
     * @throws RecordNotFoundException If dish with given ID doesn't exist
     * @throws Exception               For other errors
     */
    boolean cambiarEstado(Integer id, boolean disponible) throws RecordNotFoundException, Exception;

    IngredientesPlatoDTO getIngredientes(Integer id) throws RecordNotFoundException, Exception;
}
