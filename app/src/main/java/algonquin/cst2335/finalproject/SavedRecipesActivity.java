package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);

        recyclerView = findViewById(R.id.recipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize your adapter without the lambda expression
        adapter = new RecipesAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build(); // Note: allowMainThreadQueries is for simplicity, consider using AsyncTask or coroutines for database operations
        RecipeDao dao = db.recipeDao();

        // Assuming you have a method to convert entities from the database to Recipe objects
        dao.getAllRecipes().observe(this, recipeEntities -> {
            List<Recipe> recipes = convertEntitiesToRecipes(recipeEntities); // Implement this method
            adapter.updateRecipes(recipes);
        });

        adapter.setClickListener(new RecipesAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Recipe recipe = adapter.getItem(position);
                // Assuming you have a method or logic here to handle the click, such as opening details for this recipe
                openRecipeDetails(recipe.getId()); // Make sure this method exists and does what you expect
            }
        });
    }

    /**
     * Converts a list of RecipeEntity objects to a list of Recipe objects.
     *
     * @param entities The list of RecipeEntity objects to convert.
     * @return The converted list of Recipe objects.
     */
    private List<Recipe> convertEntitiesToRecipes(List<RecipeEntity> entities) {
        // Implement conversion logic
        return new ArrayList<>(); // Return the converted list
    }

    /**
     * Opens the recipe details activity for the specified recipe ID.
     *
     * @param recipeId The ID of the recipe to display details for.
     */

    private void openRecipeDetails(int recipeId) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }
}