package repository;

import co.edu.uniquindio.pos_resturant_app.model.Orden;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepo  extends JpaRepository<Orden, Integer> {

}
