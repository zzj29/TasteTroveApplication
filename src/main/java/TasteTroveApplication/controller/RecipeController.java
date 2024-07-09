package TasteTroveApplication.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TasteTroveApplication.dal.IngredientRepository;
import TasteTroveApplication.dal.RecipeIngredientRepository;
import TasteTroveApplication.models.Ingredient;
import TasteTroveApplication.models.Recipe;
import TasteTroveApplication.models.RecipeIngredient;
import TasteTroveApplication.service.RecipeService;

@RestController
@RequestMapping("/recipes")
@CrossOrigin("http://localhost:5173/")
public class RecipeController {

	RecipeService recipeServ;
	IngredientRepository ingredientRepo;
    RecipeIngredientRepository recipeIngredientRepo;

	@Autowired
	public RecipeController(RecipeService recipeServ, IngredientRepository ingredientRepo, RecipeIngredientRepository recipeIngredientRepo) {
		super();
		this.recipeServ = recipeServ;
		this.ingredientRepo = ingredientRepo;
        this.recipeIngredientRepo = recipeIngredientRepo;
	}

	// http://localhost:8088/TasteTroveApplication/recipes/id
	@GetMapping("{id}")
	public Recipe getRecipeById(@PathVariable int id) {
		return recipeServ.findRecipeById(id);
	}
	
	// http://localhost:8088/TasteTroveApplication/recipes/selected
	@GetMapping("selected")
	public List<Recipe> getSelectedRecipes() {
		return recipeServ.findBySelected();
	}
	
	// http://localhost:8088/TasteTroveApplication/recipes
	@GetMapping
	public List<Recipe> getAllRecipes() {
		return recipeServ.findAllRecipes();
	}
	
	// http://localhost:8088/TasteTroveApplication/recipes/id/ingredients
	@GetMapping("{id}/ingredients")
	public Set<Ingredient> getIngredientOfRecipe(@PathVariable int id) {
		return ingredientRepo.findIngredientsByRecipesId(id);
	}
	
	// http://localhost:8088/TasteTroveApplication/recipes/selected/ingredients
	@GetMapping("selected/ingredients")
	public Set<Ingredient> getAllIngredientsOfSelectedRecipes() {
		Set<Ingredient> ingredients = new HashSet<>(); 
		List<Recipe> selectedRecipes = recipeServ.findBySelected();
		for (Recipe recipe : selectedRecipes) {
			int id = recipe.getId();
			ingredients.addAll(ingredientRepo.findIngredientsByRecipesId(id));
		}
		return ingredients;
	}
	
	// http://localhost:8088/TasteTroveApplication/recipes/register
	@PostMapping("register")
	public Recipe addNewRecipe(@RequestBody Recipe recipe) {
		return recipeServ.saveRecipe(recipe);
	}
	
    // http://localhost:8088/TasteTroveApplication/recipes/{id}/linkingredient
	@PostMapping("{id}/linkingredient")
	public void linkIngredientToRecipe(@PathVariable("id") int recipeID, @RequestBody Set<Integer> ingredientIDs) {
		// Get recipe based on id provided
		Recipe recipe = recipeServ.findRecipeById(recipeID);
		
		// Get ingredients based on id provided 
		Set<Ingredient> ingredients = new HashSet<>();
		for (int integerId : ingredientIDs) {
			Ingredient ingredient = ingredientRepo.findById(integerId).orElse(null);
			if (ingredient != null) {
	            ingredients.add(ingredient);
	        }
		}
		
        // Create RecipeIngredient objects and link them to the recipe
        Set<RecipeIngredient> recipeIngredients = ingredients.stream()
                .map(ingredient -> {
                    RecipeIngredient recipeIngredient = new RecipeIngredient();
                    recipeIngredient.setRecipe(recipe);
                    recipeIngredient.setIngredient(ingredient);
                    return recipeIngredient;
                })
                .collect(Collectors.toSet());
		
		recipeIngredientRepo.saveAll(recipeIngredients);
	}
	
	// http://localhost:8088/TasteTroveApplication/recipes/update
	@PutMapping("update")
	public void updateRecipe(@RequestBody Recipe recipe) {
		recipeServ.updateRecipe(recipe);
	}
	
	// http://localhost:8088/TasteTroveApplication/recipes/id
	@DeleteMapping("{id}")
	public void deleteRecipeById(@PathVariable int id) {
		recipeServ.deleteById(id);
	}
}
