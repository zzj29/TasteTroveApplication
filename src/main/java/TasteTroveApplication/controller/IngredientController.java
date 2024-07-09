package TasteTroveApplication.controller;

import java.util.List;

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

import TasteTroveApplication.models.Ingredient;
import TasteTroveApplication.service.IngredientService;

@RestController
@RequestMapping("/ingredients")
@CrossOrigin("http://localhost:5173/")
public class IngredientController {

	IngredientService ingredientServ;
	
	@Autowired
	public IngredientController(IngredientService ingredientServ) {
		super();
		this.ingredientServ = ingredientServ;
	}

	// http://localhost:8088/TasteTroveApplication/ingredients/id
	@GetMapping("{id}")
	public Ingredient getIngredientById(@PathVariable int id) {
		return ingredientServ.findIngredientById(id);
	}
	
	// http://localhost:8088/TasteTroveApplication/ingredients
	@GetMapping
	public List<Ingredient> getAllIngredient() {
		return ingredientServ.findAllIngredients();
	}
	
	// http://localhost:8088/TasteTroveApplication/ingredients/register
	@PostMapping("register")
	public void addNewIngredient(@RequestBody Ingredient ingredient) {
		ingredientServ.save(ingredient);
	}
	
	// http://localhost:8088/TasteTroveApplication/ingredients/update
	@PutMapping("update")
	public Ingredient updateIngredient(@RequestBody Ingredient ingredient) {
		return ingredientServ.update(ingredient);
	}
	
	// http://localhost:8088/TasteTroveApplication/ingredients/id
	@DeleteMapping("{id}")
	public void deleteIngredientById(@PathVariable int id) {
		ingredientServ.deleteById(id);
	}
}
