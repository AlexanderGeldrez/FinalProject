package algonquin.cst2335.finalproject;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeDao recipeDao;
    private LiveData<List<RecipeEntity>> allRecipes;

    public RecipeViewModel(Application application) {
        super(application);
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "recipes-database").build();
        recipeDao = db.recipeDao();
        allRecipes = recipeDao.getAllRecipes();
    }

    public LiveData<List<RecipeEntity>> getAllRecipes() {
        return allRecipes;
    }

    public LiveData<RecipeEntity> getRecipeById(int id) {
        return recipeDao.getRecipeById(id);
    }

    public void insertRecipe(RecipeEntity recipe) {
        AppDatabase.databaseWriteExecutor.execute(() -> recipeDao.insertRecipe(recipe));
    }

    public void deleteRecipe(int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> recipeDao.deleteRecipe(id));
    }
}
