package TasteTroveApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TasteTroveApplication.dal.RecipeIngredientRepository;
import TasteTroveApplication.dal.RecipeRepository;
import TasteTroveApplication.exceptions.NotFoundException;
import TasteTroveApplication.exceptions.ValidationException;
import TasteTroveApplication.models.Recipe;
import TasteTroveApplication.models.RecipeIngredient;

@Service
public class RecipeService {

	RecipeRepository recipeRepo;
    RecipeIngredientRepository recipeIngredientRepo;

	@Autowired
	public RecipeService(RecipeRepository recipeRepo, RecipeIngredientRepository recipeIngredientRepo) {
		super();
		this.recipeRepo = recipeRepo;
		this.recipeIngredientRepo = recipeIngredientRepo;
	}

	public Recipe findRecipeById(int id) {
		return recipeRepo.findById(id).orElseThrow(() -> new NotFoundException("Recipe with ID " + id + " not found."));
	}


	public List<Recipe> findBySelected() {
		return recipeRepo.findBySelected(true);
	}
	
	public List<Recipe> findAllRecipes() {
		return recipeRepo.findAll();
	}

	public Recipe saveRecipe(Recipe recipe) {
		if (recipeRepo.existsById(recipe.getId())) {
			throw new ValidationException("Recipe with name: " + recipe.getName() + " already exist.");
		} else {
			return recipeRepo.save(recipe);
		}
	}

	public void updateRecipe(Recipe recipe) {
		if (recipeRepo.existsById(recipe.getId())) {
		recipeRepo.save(recipe);
		} else {
			throw new ValidationException("Recipe " + recipe.getName() + " does not exist.");
		}
	}

	public void deleteById(int id) {
		List<RecipeIngredient> foundlinkByRecipeId = recipeIngredientRepo.findByRecipeId(id);
		if (!foundlinkByRecipeId.isEmpty()) {
			// If the foundlinkByRecipeId is not empty, break the link before deleting the recipe
			for (RecipeIngredient link: foundlinkByRecipeId) {
				recipeIngredientRepo.delete(link);;
			}
			recipeRepo.deleteById(id);
		} else {
			recipeRepo.deleteById(id);
		}
	}

}
