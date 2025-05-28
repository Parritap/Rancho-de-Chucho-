package co.edu.uniquindio.pos_resturant_app.repository;

import co.edu.uniquindio.pos_resturant_app.interfaces.UsuarioRepository;
import co.edu.uniquindio.pos_resturant_app.model.Cocinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
// In CocineroRepo.java
public interface CocineroRepo extends JpaRepository<Cocinero, String>, UsuarioRepository<Cocinero> {
    Optional<Cocinero> findByUsername(String username);
}
