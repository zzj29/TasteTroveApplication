package TasteTroveApplication.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipe")
public class Recipe {

	@Id
	@Column(name = "recipe_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String description;
	private String instructions;
	private String image;
	private boolean selected;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private User user;

	@OneToMany(mappedBy = "recipe")
	private Set<RecipeIngredient> recipeIngredient = new HashSet<>();
	
	public Recipe() {
		super();
	}

	public Recipe(String name, String description, String instructions, String image,
			User user, boolean selected) {
		super();
		this.name = name;
		this.description = description;
		this.instructions = instructions;
		this.image = image;
		this.user = user;
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", description=" + description
				+ ", image=" + image +"]";
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@JsonIgnore
	public Set<RecipeIngredient> getRecipeIngredient() {
		return recipeIngredient;
	}

	public void setRecipeIngredient(Set<RecipeIngredient> recipeIngredient) {
		this.recipeIngredient = recipeIngredient;
	}
}
