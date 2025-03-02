package co.edu.uniquindio.pos_resturant_app.services.specifications;

import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteReadDTO;
import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;

import java.util.List;

public interface IngredienteService {

    public int create(IngredienteCreateDTO ingrediente) throws Exception;

    public boolean update(IngredienteCreateDTO ingrediente, int id) throws Exception;

    /**
     *
     * @param id id of the ingredient to be deleted
     * @return true if deleted successfully, false if the ingredient was not found
     * @throws Exception
     */
    public boolean delete(String id) throws Exception;

    //
    public IngredienteCreateDTO findById(String id) throws Exception;

    //make a get all method to retrieve all the ingredients
    public List<Ingrediente> getAll() throws Exception;


    //Todo cambiar nombre en el front
    public boolean editStock(String id, int cantidad) throws Exception;


}
