package co.edu.uniquindio.pos_resturant_app.repository;

import co.edu.uniquindio.pos_resturant_app.model.TipoPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPlatoRepo extends JpaRepository<TipoPlato, Integer> {

}
