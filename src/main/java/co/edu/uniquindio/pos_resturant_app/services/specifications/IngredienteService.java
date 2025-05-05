package co.edu.uniquindio.pos_resturant_app.services.specifications;

import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteReadDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.CascadeEffectException;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;

import java.util.List;

public interface IngredienteService {

    int create(IngredienteCreateDTO ingrediente) throws Exception;

    boolean update(IngredienteCreateDTO ingrediente, int id) throws Exception;

    /**
     * @param id id of the ingredient to be deleted
     * @return true if deleted successfully, false if the ingredient was not found
     * @throws Exception
     */
    boolean delete(Integer id) throws Exception, CascadeEffectException, RecordNotFoundException;

    //
    IngredienteCreateDTO findById(String id) throws RecordNotFoundException;

    //make a get all method to retrieve all the ingredients
    List<IngredienteReadDTO> getAll() throws Exception;


    //Todo cambiar nombre en el front
    boolean addStock(String id, int cantidad) throws Exception;


}
