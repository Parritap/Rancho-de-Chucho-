package co.edu.uniquindio.pos_resturant_app.repository.joints;

import co.edu.uniquindio.pos_resturant_app.model.joints.OrdenPlato;
import co.edu.uniquindio.pos_resturant_app.model.keys.OrdenPlatoID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenPlatoRepo extends JpaRepository<OrdenPlato, OrdenPlatoID> {
}
