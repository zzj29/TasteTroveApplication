package TasteTroveApplication.dal;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import TasteTroveApplication.models.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{

	Ingredient findIngredientByName(String name);
	
	@Query(value = "SELECT i.*, ri.quantity, ri.unit \n"
			+ "FROM recipe r \n"
			+ "INNER JOIN recipe_ingredient ri \n"
			+ "ON r.recipe_id = ri.fk_recipe_id \n"
			+ "INNER JOIN ingredient i \n"
			+ "ON i.ingredient_id = ri.fk_ingredient_id\n"
			+ "WHERE r.recipe_id = ?1",
			nativeQuery = true
			)
	Set<Ingredient> findIngredientsByRecipesId(int recipeId);
	
	@Query(value = "SELECT i.*, ri.quantity, ri.unit \n"
			+ "FROM recipe r \n"
			+ "INNER JOIN recipe_ingredient ri \n"
			+ "ON r.recipe_id = ri.fk_recipe_id \n"
			+ "INNER JOIN ingredient i \n"
			+ "ON i.ingredient_id = ri.fk_ingredient_id\n"
			+ "WHERE r.name = ?1",
			nativeQuery = true
			)
	Set<Ingredient> findIngredientsByRecipesName(String recipeName);
}
