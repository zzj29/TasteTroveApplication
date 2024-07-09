package TasteTroveApplication.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import TasteTroveApplication.dal.IngredientRepository;
import TasteTroveApplication.dal.RecipeIngredientRepository;
import TasteTroveApplication.models.Ingredient;
import TasteTroveApplication.service.IngredientService;

@ExtendWith(MockitoExtension.class)	
public class IngredientServiceTest {

	private IngredientService ingredientService;

	@Mock
	IngredientRepository ingredientRepositoryMock;
	
	@Mock
	RecipeIngredientRepository recipeIngredientRepoMock;
	
	@BeforeEach
	public void setup() {
		ingredientService = new IngredientService(ingredientRepositoryMock, recipeIngredientRepoMock);
	}
	
	@Test
    public void testFindIngredientById() {
        int ingredientId = 1;
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        when(ingredientRepositoryMock.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        Ingredient result = ingredientService.findIngredientById(ingredientId);

        assertEquals(ingredientId, result.getId());
        verify(ingredientRepositoryMock, times(1)).findById(ingredientId);
    }
	
	@Test
    public void testFindAllIngredients() {
        List<Ingredient> ingredients = Arrays.asList(new Ingredient(), new Ingredient());
        when(ingredientRepositoryMock.findAll()).thenReturn(ingredients);

        List<Ingredient> result = ingredientService.findAllIngredients();

        assertEquals(ingredients.size(), result.size());
        verify(ingredientRepositoryMock, times(1)).findAll();
    }
	
	 @Test
	 public void testSave() {
		 Ingredient ingredient = new Ingredient();
		 when(ingredientRepositoryMock.save(ingredient)).thenReturn(ingredient);

	     ingredientService.save(ingredient);

	     verify(ingredientRepositoryMock, times(1)).save(ingredient);
	 }
	 
	 @Test
	 public void testUpdate() {
	     Ingredient ingredient = new Ingredient();

	     ingredientService.update(ingredient);

	     verify(ingredientRepositoryMock, times(1)).save(ingredient);
	 }	 
	 
	 @Test
	 public void testDeleteById() {
	     int ingredientId = 1;
	     when(recipeIngredientRepoMock.findByIngredientId(ingredientId)).thenReturn(Arrays.asList());

	     ingredientService.deleteById(ingredientId);
	     
	     verify(recipeIngredientRepoMock, times(1)).findByIngredientId(ingredientId);
	     verify(ingredientRepositoryMock, times(1)).deleteById(ingredientId);
	 }
}
