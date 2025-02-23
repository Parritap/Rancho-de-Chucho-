package repository;

import co.edu.uniquindio.pos_resturant_app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * This interface should be enough for managing all the entities that extend Usuario.
 * This is because said entities only exist to persist roles.
 */
@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
}
