package co.edu.uniquindio.pos_resturant_app.repository;

import co.edu.uniquindio.pos_resturant_app.model.Plato;
import co.edu.uniquindio.pos_resturant_app.model.TipoPlato;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepo extends JpaRepository<Plato, Integer> {

    List<Plato> findByNombre(String nombre);
}
