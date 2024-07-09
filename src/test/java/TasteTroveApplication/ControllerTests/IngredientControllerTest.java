package TasteTroveApplication.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import TasteTroveApplication.controller.IngredientController;
import TasteTroveApplication.models.Ingredient;
import TasteTroveApplication.service.IngredientService;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {

	private IngredientController ingredientController;
	
	@Mock
	private IngredientService ingredientServiceMock;
	
	@BeforeEach
	public void setup() {
		ingredientController = new IngredientController(ingredientServiceMock);
	}
	
	@Test
    public void testGetIngredientById() {
        int ingredientId = 1;
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        when(ingredientServiceMock.findIngredientById(ingredientId)).thenReturn(ingredient);

        Ingredient result = ingredientController.getIngredientById(ingredientId);

        assertEquals(ingredientId, result.getId());
        verify(ingredientServiceMock, times(1)).findIngredientById(ingredientId);
    }
	
    @Test
    public void testGetAllIngredient() {
        List<Ingredient> ingredients = Arrays.asList(new Ingredient(), new Ingredient());
        when(ingredientServiceMock.findAllIngredients()).thenReturn(ingredients);

        List<Ingredient> result = ingredientController.getAllIngredient();

        assertEquals(ingredients.size(), result.size());
        verify(ingredientServiceMock, times(1)).findAllIngredients();
    }
    
    @Test
    public void testAddNewIngredient() {
        Ingredient ingredient = new Ingredient();

        ingredientController.addNewIngredient(ingredient);

        verify(ingredientServiceMock, times(1)).save(ingredient);
    }
    
    @Test
    public void testUpdateIngredient() {
        Ingredient ingredient = new Ingredient();

        ingredientController.updateIngredient(ingredient);

        verify(ingredientServiceMock, times(1)).update(ingredient);
    }
    
    @Test
    public void testDeleteIngredientById() {
        int ingredientId = 1;

        ingredientController.deleteIngredientById(ingredientId);

        verify(ingredientServiceMock, times(1)).deleteById(ingredientId);
    }
}
