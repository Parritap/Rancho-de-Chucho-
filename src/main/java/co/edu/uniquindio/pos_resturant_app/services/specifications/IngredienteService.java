package co.edu.uniquindio.pos_resturant_app.services.specifications;

import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteReadDTO;
import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;

import java.util.List;

public interface IngredienteService {

    public int create(IngredienteCreateDTO ingrediente) throws Exception;

    public boolean update(IngredienteReadDTO ingrediente) throws Exception;

    public void delete(String id) throws Exception;

    //
    public IngredienteCreateDTO findById(String id) throws Exception;

    //make a get all method to retrieve all the ingredients
    public List<Ingrediente> getAll() throws Exception;


    //Todo cambiar nombre en el front
    public boolean editStock(String id, int cantidad) throws Exception;


}
