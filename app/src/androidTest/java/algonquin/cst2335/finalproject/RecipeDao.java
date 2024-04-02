package algonquin.cst2335.finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    // Insert a recipe into the database
    @Insert
    void insertRecipe(RecipeEntity recipe);

    // Delete a specific recipe from the database by its ID
    @Query("DELETE FROM recipes WHERE id = :recipeId")
    void deleteRecipe(int recipeId);

    // Query all recipes in the database
    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeEntity>> getAllRecipes();
}
