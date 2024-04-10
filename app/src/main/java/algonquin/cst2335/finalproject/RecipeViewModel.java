package algonquin.cst2335.finalproject;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeDao recipeDao;
    private LiveData<List<RecipeEntity>> allRecipes;

    /**
     * Constructor for the RecipeViewModel.
     *
     * @param application The application context.
     */
    public RecipeViewModel(Application application) {
        super(application);
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "recipes-database").build();
        recipeDao = db.recipeDao();
        allRecipes = recipeDao.getAllRecipes();
    }

    /**
     * Retrieves all recipes from the database.
     *
     * @return LiveData containing a list of all recipes.
     */
    public LiveData<List<RecipeEntity>> getAllRecipes() {
        return allRecipes;
    }

    /**
     * Retrieves a specific recipe from the database based on its ID.
     *
     * @param id The ID of the recipe to retrieve.
     * @return LiveData containing the requested recipe entity.
     */
    public LiveData<RecipeEntity> getRecipeById(int id) {
        return recipeDao.getRecipeById(id);
    }

    /**
     * Inserts a new recipe into the database.
     *
     * @param recipe The recipe entity to be inserted.
     */
    public void insertRecipe(RecipeEntity recipe) {
        AppDatabase.databaseWriteExecutor.execute(() -> recipeDao.insertRecipe(recipe));
    }

    /**
     * Deletes a recipe from the database based on its ID.
     *
     * @param id The ID of the recipe to be deleted.
     */
    public void deleteRecipe(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> recipeDao.deleteRecipe(id));
    }

    public void deleteAllRecipes() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            recipeDao.deleteAllRecipes();
        });
    }


}
