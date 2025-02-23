package repository;

import co.edu.uniquindio.pos_resturant_app.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatoRepo extends JpaRepository<Plato, Integer> {

}
