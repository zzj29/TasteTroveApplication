package TasteTroveApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TasteTroveApplication.dal.IngredientRepository;
import TasteTroveApplication.dal.RecipeIngredientRepository;
import TasteTroveApplication.exceptions.ConflictException;
import TasteTroveApplication.exceptions.NotFoundException;
import TasteTroveApplication.models.Ingredient;
import TasteTroveApplication.models.RecipeIngredient;

@Service
public class IngredientService {

	IngredientRepository ingredientRepo;
    RecipeIngredientRepository recipeIngredientRepo;
	
	@Autowired
	public IngredientService(IngredientRepository ingredientRepo, RecipeIngredientRepository recipeIngredientRepo) {
		super();
		this.ingredientRepo = ingredientRepo;
		this.recipeIngredientRepo = recipeIngredientRepo;
	}

	public Ingredient findIngredientById(int id) {
		return ingredientRepo.findById(id).orElseThrow(()->new NotFoundException("Ingredient with ID " + id + " not found."));
	}

	public List<Ingredient> findAllIngredients() {
		return ingredientRepo.findAll();
	}

	public void save(Ingredient ingredient) {
		ingredientRepo.save(ingredient);
	}

	public Ingredient update(Ingredient ingredient) {
		return ingredientRepo.save(ingredient);		
	}

	public void deleteById(int id) {
		List<RecipeIngredient> foundlinkByIngredientId = recipeIngredientRepo.findByIngredientId(id);
		if (foundlinkByIngredientId.isEmpty()) {
		ingredientRepo.deleteById(id);
		} else {
			throw new ConflictException("Cannot delete ingredient because it is used in recipes.");
		}
	}
}
