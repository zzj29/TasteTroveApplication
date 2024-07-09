package TasteTroveApplication.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import TasteTroveApplication.controller.RecipeController;
import TasteTroveApplication.dal.IngredientRepository;
import TasteTroveApplication.dal.RecipeIngredientRepository;
import TasteTroveApplication.models.Ingredient;
import TasteTroveApplication.models.Recipe;
import TasteTroveApplication.service.RecipeService;

@ExtendWith(MockitoExtension.class)	
public class RecipeControllerTest {

	private RecipeController recipeController;
	
	@Mock
	private RecipeService recipeServiceMock;
	
	@Mock 
	private IngredientRepository ingredientRepositoryMock;
	
	@Mock
	private RecipeIngredientRepository recipeIngredientRepo;
	
	@BeforeEach
	public void setup() {
		recipeController = new RecipeController(recipeServiceMock, ingredientRepositoryMock, recipeIngredientRepo);
	}
	
	@Test
	public void testGetRecipeById() {
		int recipeId = 1;
		Recipe recipe = new Recipe();
	    recipe.setId(recipeId);
	    when(recipeServiceMock.findRecipeById(recipeId)).thenReturn(recipe);

	    Recipe result = recipeController.getRecipeById(recipeId);

	    assertEquals(recipeId, result.getId());
	    verify(recipeServiceMock, times(1)).findRecipeById(recipeId);
	}

	@Test
    public void testGetSelectedRecipes() {
        List<Recipe> selectedRecipes = Arrays.asList(new Recipe(), new Recipe());
        when(recipeServiceMock.findBySelected()).thenReturn(selectedRecipes);

        List<Recipe> result = recipeController.getSelectedRecipes();

        assertEquals(selectedRecipes.size(), result.size());
        verify(recipeServiceMock, times(1)).findBySelected();
    }
	
    @Test
    public void testGetAllRecipes() {
        List<Recipe> recipes = Arrays.asList(new Recipe(), new Recipe());
        when(recipeServiceMock.findAllRecipes()).thenReturn(recipes);

        List<Recipe> result = recipeController.getAllRecipes();

        assertEquals(recipes.size(), result.size());
        verify(recipeServiceMock, times(1)).findAllRecipes();
    }
	
    @Test
    public void testGetIngredientOfRecipe() {
        int recipeId = 1;
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient());
        when(ingredientRepositoryMock.findIngredientsByRecipesId(recipeId)).thenReturn(ingredients);

        Set<Ingredient> result = recipeController.getIngredientOfRecipe(recipeId);

        assertNotNull(result);
        verify(ingredientRepositoryMock, times(1)).findIngredientsByRecipesId(recipeId);
    }
	
    @Test
    public void testGetAllIngredientsOfSelectedRecipes() {
        List<Recipe> selectedRecipes = Arrays.asList(new Recipe(), new Recipe());
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient());
        ingredients.add(new Ingredient());
        when(recipeServiceMock.findBySelected()).thenReturn(selectedRecipes);
        when(ingredientRepositoryMock.findIngredientsByRecipesId(anyInt())).thenReturn(ingredients);

        Set<Ingredient> result = recipeController.getAllIngredientsOfSelectedRecipes();

        assertNotNull(result);
        assertEquals(ingredients.size(), result.size());
        verify(recipeServiceMock, times(1)).findBySelected();
        verify(ingredientRepositoryMock, times(selectedRecipes.size())).findIngredientsByRecipesId(anyInt());
    }
	
    @Test
    public void testAddNewRecipe() {
        Recipe recipe = new Recipe();

        recipeController.addNewRecipe(recipe);

        verify(recipeServiceMock, times(1)).saveRecipe(recipe);
    }

    @Test
    public void testUpdateRecipe() {
        Recipe recipe = new Recipe();

        recipeController.updateRecipe(recipe);

        verify(recipeServiceMock, times(1)).updateRecipe(recipe);
    }
	
    @Test
    public void testDeleteRecipeById() {
        int recipeId = 1;

        recipeController.deleteRecipeById(recipeId);

        verify(recipeServiceMock, times(1)).deleteById(recipeId);
    }
}
