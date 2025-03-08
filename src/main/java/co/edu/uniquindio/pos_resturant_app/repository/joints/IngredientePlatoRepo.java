package co.edu.uniquindio.pos_resturant_app.repository.joints;

import co.edu.uniquindio.pos_resturant_app.model.joints.IngredientePlato;
import co.edu.uniquindio.pos_resturant_app.model.keys.IngredientePlatoID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IngredientePlatoRepo extends JpaRepository<IngredientePlato, IngredientePlatoID> {

    @Modifying
    @Transactional
    @Query("DELETE FROM IngredientePlato ip WHERE ip.ingrediente.id = :ingredienteId")
    void deleteByIngredienteId(int ingredienteId);


    @Query("SELECT COUNT(ip) > 0 FROM IngredientePlato ip WHERE ip.ingrediente.id = :ingredienteId")
    boolean existsByIngredienteId(@Param("ingredienteId") int ingredienteId);
}
