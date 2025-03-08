package co.edu.uniquindio.pos_resturant_app.repository;

import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredienteRepo extends JpaRepository<Ingrediente, Integer> {

    Optional<Ingrediente> findFirstByNombreAndMarca(String nombre, String marca);
}
