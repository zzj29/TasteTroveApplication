package TasteTroveApplication.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import TasteTroveApplication.models.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>{

	public Recipe findRecipeByName(String name);

	@Query(value = "SELECT r.* \n"
			+ "FROM recipe r \n"
			+ "INNER JOIN recipe_ingredient ri \n"
			+ "ON r.recipe_id = ri.fk_recipe_id \n"
			+ "INNER JOIN ingredient i \n"
			+ "ON i.ingredient_id = ri.fk_ingredient_id\n"
			+ "WHERE i.ingredient_id = ?1",
			nativeQuery = true)
	public List<Recipe> findRecipesByIngredientId(int ingredientId);
	
	@Query(value = "SELECT r.* \n"
			+ "FROM recipe r \n"
			+ "INNER JOIN recipe_ingredient ri \n"
			+ "ON r.recipe_id = ri.fk_recipe_id \n"
			+ "INNER JOIN ingredient i \n"
			+ "ON i.ingredient_id = ri.fk_ingredient_id\n"
			+ "WHERE i.name = ?1",
			nativeQuery = true)
	public List<Recipe> findRecipesByIngredientName(String ingredientName);
	
	public List<Recipe> findBySelected(boolean isSelected);
}
