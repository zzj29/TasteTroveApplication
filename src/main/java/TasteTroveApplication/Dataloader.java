package TasteTroveApplication;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import TasteTroveApplication.dal.IngredientRepository;
import TasteTroveApplication.dal.RecipeIngredientRepository;
import TasteTroveApplication.dal.RecipeRepository;
import TasteTroveApplication.dal.UserRepository;
import TasteTroveApplication.models.Ingredient;
import TasteTroveApplication.models.Recipe;
import TasteTroveApplication.models.RecipeIngredient;
import TasteTroveApplication.models.Role;
import TasteTroveApplication.models.User;
import TasteTroveApplication.service.UserService;



@Service
public class Dataloader implements ApplicationRunner {

	UserRepository userRepo;
	UserService userServ;
    RecipeRepository recipeRepo;
    IngredientRepository ingredientRepo;
    RecipeIngredientRepository recipeIngredientRepo;

	@Autowired
	public Dataloader(UserRepository userRepo, UserService userServ, RecipeRepository recipeRepo, IngredientRepository ingredientRepo, RecipeIngredientRepository recipeIngredientRepo) {
        this.userRepo = userRepo;
        this.userServ = userServ;
        this.recipeRepo = recipeRepo;
        this.ingredientRepo = ingredientRepo;
        this.recipeIngredientRepo = recipeIngredientRepo;
    }

	public Dataloader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// User data
		User adminUser = new User("john_doe", "P@ssw0rd123", "john.doe@example.com", Role.Role_Admin);
		User familyUser1 = new User("jane_smith", "SecretPass456", "jane.smith@example.com", Role.Role_User);
		User familyUser2 = new User("mary_jones", "StrongPass789", "mary.jones@example.com", Role.Role_User);

		userServ.registerUser(adminUser);
		userServ.registerUser(familyUser1);
		userServ.registerUser(familyUser2);
		
		// Ingredient data
		Ingredient chicken = new Ingredient("Chicken", "Meat");
		Ingredient beefMice = new Ingredient("BeefMice", "Meat");
		Ingredient lamb = new Ingredient("Lamb", "Meat");
		Ingredient pork = new Ingredient("Pork", "Meat");
		Ingredient beefSteak = new Ingredient("Beef Steak", "Meat");
		Ingredient salmon = new Ingredient("Salmon", "Seafood");
		Ingredient prawn = new Ingredient("Prawn", "Seafood");
		Ingredient tuna = new Ingredient("Tuna", "Seafood");
		Ingredient potato = new Ingredient("Potato", "Vegetable");
		Ingredient tomato = new Ingredient("Tomato", "Vegetable");
		Ingredient onion = new Ingredient("Onion", "Vegetable");
		Ingredient garlic = new Ingredient("Garlic", "Vegetable");
		Ingredient carrot = new Ingredient("Carrot", "Vegetable");
		Ingredient asparagus = new Ingredient("Asparagus", "Vegetable");
		Ingredient pasta = new Ingredient("Pasta", "Main");
		Ingredient rice = new Ingredient("Rice", "Main");
		
		ingredientRepo.save(chicken);
		ingredientRepo.save(beefMice);
		ingredientRepo.save(lamb);
		ingredientRepo.save(pork);
		ingredientRepo.save(beefSteak);
		ingredientRepo.save(salmon);
		ingredientRepo.save(prawn);
		ingredientRepo.save(tuna);
		ingredientRepo.save(potato);
		ingredientRepo.save(tomato);
		ingredientRepo.save(onion);
		ingredientRepo.save(garlic);
		ingredientRepo.save(carrot);
		ingredientRepo.save(asparagus);
		ingredientRepo.save(pasta);
		ingredientRepo.save(rice);
		
		// Recipe data
		Recipe chicken_curry = new Recipe("Chicken Curry","Spicy chicken curry with rice.", 
							"1. Fry the chicken skin side down to render the fat.\n"
							+ "2. Stir-fry with chicken fat until onions change color.\n"
							+ "3. Add potato pieces and stir-fry until translucent.\n"
							+ "4. Add water, carrot pieces, and curry paste.\n"
							+ "5. Cook until potatoes can be pierced.",
							"chicken_curry.jpg", adminUser, true);
		
		Recipe crispy_skinned_Salmon = new Recipe("Crispy-skinned Salmon","Delicious crispy-skinned salmon with asparagus.",
							"1. Fry the garlic in olive oil.\n"
							+ "2. Fry the salmon skin-side down until golden brown.\n"
							+ "3. Flip and sprinkle with black pepper, then add a small piece of butter.\n"
							+ "4. Add the asparagus and fry until cooked.",
							"crispy_skinned_salmon.jpg",adminUser, true);
		
		Recipe prawn_pappardelle = new Recipe("Prawn Pappardelle","Pappardelle pasta with succulent prawns and aromatic garlic.",
							"1. Fry the prawns and galic until prawns curl and change colour.\n"
							+ "2. Stir-fry tomato until collapse.\n"
							+ "3. Cook pasta in a saucepan of boiling water following packet directions. \n"
							+ "4. Drain the pasta and mixed with prawns, spinach, and tomato.",
							"prawn_pappardelle.jpg",adminUser, false);
		
		Recipe beefMice_spaghetti = new Recipe("BeefMice Spaghetti","Classic beef mince spaghetti topped with cheese.",
							"1. Heat olive oil and fry onions and garlic until golden.\n"
							+ "2. Add beef mince and stir-fry until browned.\n"
							+ "3. Add tomatoes and stir-fry until they release their juices, then add tomato paste and water.\n"
							+ "4. Cook spaghetti until dente, then drain.",
							"beefMice_spaghetti.png",adminUser, false);
		recipeRepo.save(chicken_curry);
		recipeRepo.save(crispy_skinned_Salmon);
		recipeRepo.save(prawn_pappardelle);
		recipeRepo.save(beefMice_spaghetti);

		Set<RecipeIngredient> chickenCurryIngredients = new HashSet<>();
        chickenCurryIngredients.add(new RecipeIngredient(chicken_curry, chicken, 500, "g"));
        chickenCurryIngredients.add(new RecipeIngredient(chicken_curry, potato, 2, "pcs"));
        chickenCurryIngredients.add(new RecipeIngredient(chicken_curry, carrot, 1, "pc"));
        chickenCurryIngredients.add(new RecipeIngredient(chicken_curry, rice, 1, "cup"));

        Set<RecipeIngredient> crispySkinnedSalmonIngredients = new HashSet<>();
        crispySkinnedSalmonIngredients.add(new RecipeIngredient(crispy_skinned_Salmon, salmon, 200, "g"));
        crispySkinnedSalmonIngredients.add(new RecipeIngredient(crispy_skinned_Salmon, asparagus, 8, "pcs"));
        crispySkinnedSalmonIngredients.add(new RecipeIngredient(crispy_skinned_Salmon, garlic, 2, "cloves"));

        Set<RecipeIngredient> prawnPappardelleIngredients = new HashSet<>();
        prawnPappardelleIngredients.add(new RecipeIngredient(prawn_pappardelle, prawn, 250, "g"));
        prawnPappardelleIngredients.add(new RecipeIngredient(prawn_pappardelle, tomato, 2, "pcs"));
        prawnPappardelleIngredients.add(new RecipeIngredient(prawn_pappardelle, garlic, 3, "cloves"));
        prawnPappardelleIngredients.add(new RecipeIngredient(prawn_pappardelle, pasta, 200, "g"));

        Set<RecipeIngredient> beefMiceSpaghettiIngredients = new HashSet<>();
        beefMiceSpaghettiIngredients.add(new RecipeIngredient(beefMice_spaghetti, beefMice, 300, "g"));
        beefMiceSpaghettiIngredients.add(new RecipeIngredient(beefMice_spaghetti, tomato, 3, "pcs"));
        beefMiceSpaghettiIngredients.add(new RecipeIngredient(beefMice_spaghetti, onion, 1, "pc"));
        beefMiceSpaghettiIngredients.add(new RecipeIngredient(beefMice_spaghetti, garlic, 2, "cloves"));
        beefMiceSpaghettiIngredients.add(new RecipeIngredient(beefMice_spaghetti, pasta, 250, "g"));

        recipeIngredientRepo.saveAll(chickenCurryIngredients);
        recipeIngredientRepo.saveAll(crispySkinnedSalmonIngredients);
        recipeIngredientRepo.saveAll(prawnPappardelleIngredients);
        recipeIngredientRepo.saveAll(beefMiceSpaghettiIngredients);
		
        // testing data
        
//		User foundUser = userRepo.findById(1).orElseThrow(() -> new RuntimeException("No user with ID 1"));
//		System.out.println("\nFound user with ID 1: " + foundUser);
//
//		User foundByName = userRepo.findByUsername("john_doe").orElseThrow(() -> new RuntimeException("No user with username john_doe"));
//		System.out.println("\nFound user with name john_doe: " + foundByName);
//		
//		Recipe foundRecipe = recipeRepo.findById(1).orElseThrow(() -> new RuntimeException("No recipe with ID 1"));
//		System.out.println("\nFound recipe with ID 1: " + foundRecipe);
//		
//		Recipe foundByRecipeName = recipeRepo.findRecipeByName("Chicken Curry");
//		System.out.println("\nFound recipe with name Chicken Curry: " + foundByRecipeName);
//		
//		List<Recipe> foundSelctedRecipes = recipeRepo.findBySelected(true);
//		System.out.println("\nSelected recipes: " + foundSelctedRecipes);
//		
//		List<Recipe> foundRecipesByIngredientId = recipeRepo.findRecipesByIngredientId(8);
//		System.out.println("\nRecipes contain ingredient 8: " + foundRecipesByIngredientId);
//		
//		List<Recipe> foundRecipesByIngredientName = recipeRepo.findRecipesByIngredientName("Garlic");
//		System.out.println("\nRecipes contain ingredient garlic: " + foundRecipesByIngredientName);
//		
//		Ingredient foundIngredient = ingredientRepo.findById(1).orElseThrow(() -> new RuntimeException("No ingredient with ID 1"));
//		System.out.println("\nFound ingredient with ID 1: " + foundIngredient);
//
//		Ingredient foundByIngreName = ingredientRepo.findIngredientByName("Pasta");
//		System.out.println("\nFound recipe with name Pasta: " + foundByIngreName);
//		
//		Set<Ingredient> foundIngredientsByRecipesId = ingredientRepo.findIngredientsByRecipesId(2);
//		System.out.println("\nIngredients for Receipe 2 Crispy-skinned Salmon are: " + foundIngredientsByRecipesId);
//		
//		Set<Ingredient> foundIngredientsByRecipesName = ingredientRepo.findIngredientsByRecipesName("Chicken Curry");
//		System.out.println("\nIngredients for Chicken Curry Receipe are: " + foundIngredientsByRecipesName);
//		
//		List<RecipeIngredient> foundlinkByRecipeId = recipeIngredientRepo.findByRecipeId(1);
//		System.out.println(foundlinkByRecipeId);
		
	}

}
