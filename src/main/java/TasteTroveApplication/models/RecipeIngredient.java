package TasteTroveApplication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "FK_recipe_id")
	private Recipe recipe;
	
	@ManyToOne
	@JoinColumn(name = "FK_ingredient_id")
	private Ingredient ingredient;
	
	private double quantity;
	
	private String unit;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	public Ingredient getIngredient() {
		return ingredient;
	}
	
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public RecipeIngredient(Recipe recipe, Ingredient ingredient, double quantity, String unit) {
		super();
		this.recipe = recipe;
		this.ingredient = ingredient;
		this.quantity = quantity;
		this.unit = unit;
	}
	
	public RecipeIngredient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "RecipeIngredient [id=" + id + ", recipe=" + recipe + ", ingredient=" + ingredient + ", quantity="
				+ quantity + ", unit=" + unit + "]";
	}
	
}
