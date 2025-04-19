package co.edu.uniquindio.pos_resturant_app.repository;

import co.edu.uniquindio.pos_resturant_app.model.Mesero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeseroRepo extends JpaRepository<Mesero, Integer> {
    Optional<Mesero> findByUsername(String username);
}
