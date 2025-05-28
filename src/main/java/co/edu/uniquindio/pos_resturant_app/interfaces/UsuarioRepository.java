package co.edu.uniquindio.pos_resturant_app.interfaces;

import java.util.Optional;

public interface UsuarioRepository<T> {
    Optional<T> findByUsername(String username);
}
