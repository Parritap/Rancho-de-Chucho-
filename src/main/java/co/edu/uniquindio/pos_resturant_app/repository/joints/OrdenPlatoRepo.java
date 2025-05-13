package co.edu.uniquindio.pos_resturant_app.repository.joints;

import co.edu.uniquindio.pos_resturant_app.model.joints.OrdenPlato;
import co.edu.uniquindio.pos_resturant_app.model.keys.OrdenPlatoID;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface OrdenPlatoRepo extends JpaRepository<OrdenPlato, OrdenPlatoID> {

    @Modifying
    @Transactional
    @Query("DELETE FROM OrdenPlato op WHERE op.id.idPlato = ?1 AND op.id.idOrden = ?2")
    void deleteByPlatoIdAndOrdenId(Integer idPlato, Integer idOrden);


    @Query("SELECT COUNT(op) > 0 FROM OrdenPlato op WHERE op.id.idPlato = ?1 AND op.id.idOrden = ?2")
    boolean existsByIdPlatoIdAndIdOrdenId(Integer idPlato, Integer idOrden);

    @Query ("SELECT op FROM OrdenPlato op WHERE op.id.idPlato = ?1 AND op.id.idOrden = ?2")
    OrdenPlato findByIdPlatoAndIdOrden(Integer idPlato, Integer idOrden);

    @Query ("SELECT op FROM OrdenPlato op WHERE op.id.idOrden = ?1")
    List<OrdenPlato> findbyIdOrden(Integer idOrden);
}
