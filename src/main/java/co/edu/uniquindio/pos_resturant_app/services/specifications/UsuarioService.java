package co.edu.uniquindio.pos_resturant_app.services.specifications;


import co.edu.uniquindio.pos_resturant_app.model.Usuario;

public interface UsuarioService {

    // Create only the basic crud operations
    public int create(Usuario usuario) throws Exception;
    public int update(Usuario usuario) throws  Exception;
    public void delete(String id)throws  Exception;
}
