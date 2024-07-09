package TasteTroveApplication.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import TasteTroveApplication.models.RecipeIngredient;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer>{

	public List<RecipeIngredient> findByRecipeId(int recipeId);
	public List<RecipeIngredient> findByIngredientId(int ingredientId);

}
