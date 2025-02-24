package co.edu.uniquindio.pos_resturant_app.repository;

import co.edu.uniquindio.pos_resturant_app.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepo extends JpaRepository<Mesa,Integer> {

}
