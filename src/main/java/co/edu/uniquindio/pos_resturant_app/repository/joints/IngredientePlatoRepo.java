package co.edu.uniquindio.pos_resturant_app.repository.joints;

import co.edu.uniquindio.pos_resturant_app.model.keys.IngredientePlatoID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientePlatoRepo extends JpaRepository<IngredientePlatoRepo, IngredientePlatoID> {
}
