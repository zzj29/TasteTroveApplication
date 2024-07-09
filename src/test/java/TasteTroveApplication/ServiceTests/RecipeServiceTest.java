package TasteTroveApplication.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import TasteTroveApplication.dal.RecipeIngredientRepository;
import TasteTroveApplication.dal.RecipeRepository;
import TasteTroveApplication.exceptions.ValidationException;
import TasteTroveApplication.models.Recipe;
import TasteTroveApplication.models.RecipeIngredient;
import TasteTroveApplication.service.RecipeService;

@ExtendWith(MockitoExtension.class)	
public class RecipeServiceTest {

	private RecipeService recipeService;

	@Mock
	RecipeRepository recipeRepositoryMock;
	
	@Mock
	RecipeIngredientRepository recipeIngredientRepoMock;
	
	@BeforeEach
	public void setup() {
		recipeService = new RecipeService(recipeRepositoryMock, recipeIngredientRepoMock);
	}
	
	@Test
    public void testFindRecipeById() {
        int recipeId = 1;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        when(recipeRepositoryMock.findById(recipeId)).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.findRecipeById(recipeId);

        assertEquals(recipeId, result.getId());
        verify(recipeRepositoryMock, times(1)).findById(recipeId);
    }

	@Test
    public void testFindAllRecipes() {
        List<Recipe> recipes = Arrays.asList(new Recipe(), new Recipe());
        when(recipeRepositoryMock.findAll()).thenReturn(recipes);

        List<Recipe> result = recipeService.findAllRecipes();

        assertEquals(recipes.size(), result.size());
        verify(recipeRepositoryMock, times(1)).findAll();
    }	
	
	@Test
    public void testFindBySelected() {
        List<Recipe> recipes = Arrays.asList(new Recipe(), new Recipe());
        when(recipeRepositoryMock.findBySelected(true)).thenReturn(recipes);

        List<Recipe> result = recipeService.findBySelected();

        assertEquals(recipes.size(), result.size());
        verify(recipeRepositoryMock, times(1)).findBySelected(true);
    }
	
	@Test
    public void testSaveRecipe() {
        Recipe recipe = new Recipe();
        when(recipeRepositoryMock.save(recipe)).thenReturn(recipe);

        recipeService.saveRecipe(recipe);

        verify(recipeRepositoryMock, times(1)).existsById(recipe.getId());
        verify(recipeRepositoryMock, times(1)).save(recipe);
    }
	
	@Test
	public void testSaveRecipereturnException_whenExists() {
		Recipe existingRecipe = new Recipe();
        when(recipeRepositoryMock.existsById(existingRecipe.getId())).thenReturn(true);
        
        assertThrows(ValidationException.class, () -> {
        	recipeService.saveRecipe(existingRecipe);
        });
        verify(recipeRepositoryMock, times(1)).existsById(existingRecipe.getId());
	}
	
	@Test
    public void testUpdateRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        when(recipeRepositoryMock.existsById(recipe.getId())).thenReturn(true);

        recipeService.updateRecipe(recipe);

        verify(recipeRepositoryMock, times(1)).existsById(recipe.getId());
        verify(recipeRepositoryMock, times(1)).save(recipe);
    }
	
	@Test
    public void testDeleteById() {
        int recipeId = 1;
        List<RecipeIngredient> recipeIngredients = Arrays.asList(new RecipeIngredient(), new RecipeIngredient());
        when(recipeIngredientRepoMock.findByRecipeId(recipeId)).thenReturn(recipeIngredients);

        recipeService.deleteById(recipeId);

        verify(recipeIngredientRepoMock, times(1)).findByRecipeId(recipeId);
        verify(recipeIngredientRepoMock, times(recipeIngredients.size())).delete(any(RecipeIngredient.class));
        verify(recipeRepositoryMock, times(1)).deleteById(recipeId);
    }
	
	@Test
    public void testDeleteById_WithoutIngredients() {
        int recipeId = 1;
        when(recipeIngredientRepoMock.findByRecipeId(recipeId)).thenReturn(Arrays.asList());

        recipeService.deleteById(recipeId);

        verify(recipeIngredientRepoMock, times(1)).findByRecipeId(recipeId);
        verify(recipeRepositoryMock, times(1)).deleteById(recipeId);
    }

}
