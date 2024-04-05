package algonquin.cst2335.finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    void insertRecipe(RecipeEntity recipe);

    @Query("DELETE FROM recipes WHERE id = :recipeId")
    void deleteRecipe(int recipeId);

    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeEntity>> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE id = :recipeId LIMIT 1")
    LiveData<RecipeEntity> getRecipeById(int recipeId);
}
