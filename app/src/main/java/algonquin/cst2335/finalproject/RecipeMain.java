package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeMain extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton, viewSavedRecipesButton;
    private RecyclerView recipesRecyclerView;
    private RecipesAdapter recipesAdapter;
    private RecipeViewModel viewModel;
    private final String apiKey = "6c22d1a8b6ab41e79773c28f1c6ed9eb";

    /**
     * Initializes the activity, setting up the UI components and event listeners.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recipesRecyclerView = findViewById(R.id.recipesRecyclerView);
        viewSavedRecipesButton = findViewById(R.id.viewSavedRecipesButton);

        Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(view -> showHelpDialog());

        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipesAdapter = new RecipesAdapter(this, new ArrayList<>());
        recipesRecyclerView.setAdapter(recipesAdapter);

        viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        searchButton.setOnClickListener(view -> performSearch());

        recipesAdapter.setClickListener((view, position) -> {
            Recipe recipe = recipesAdapter.getItem(position);
            openRecipeDetails(recipe.getId());
        });

        // AlertDialog setup for viewSavedRecipesButton is maintained
        viewSavedRecipesButton.setOnClickListener(view -> {
            new AlertDialog.Builder(RecipeMain.this)
                    .setTitle("Load Saved Recipes")
                    .setMessage("Are you sure you want to view saved recipes?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> loadSavedRecipes())
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.navigation) // Make sure this drawable exists
                    .show();
        });

        // Modify this part to correctly implement the UNDO feature
        findViewById(R.id.deleteAllRecipesButton).setOnClickListener(view -> {
            // Ensure we fetch and store the current list of recipes first
            viewModel.getAllRecipes().observe(this, recipeEntities -> {
                List<RecipeEntity> deletedRecipes = new ArrayList<>(recipeEntities);

                viewModel.deleteAllRecipes(); // This deletes all recipes from your database.

                Snackbar.make(findViewById(android.R.id.content), "All recipes deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> {
                            // Re-insert the deleted recipes
                            for (RecipeEntity recipe : deletedRecipes) {
                                viewModel.insertRecipe(recipe);
                            }
                        }).show();

                // Detach the observer to prevent this block from running again unnecessarily
                viewModel.getAllRecipes().removeObservers(this);
            });
        });
    }

    private void performSearch() {
        String query = searchEditText.getText().toString();
        RetrofitClientInstance.getRetrofitInstance().create(SpoonacularAPIService.class)
                .searchRecipes(query, apiKey).enqueue(new Callback<RecipeSearchResponse>() {
                    @Override
                    public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            recipesAdapter.updateRecipes(response.body().getResults());
                            Log.d("API Response", "Response: " + response.body());
                            Snackbar.make(findViewById(android.R.id.content), "Recipes loaded successfully", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RecipeMain.this, "Failed to fetch recipes.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                        Toast.makeText(RecipeMain.this, "Network error, please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Shows an AlertDialog with the description of the recipe application.
     */
    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("About Recipe App")
                .setMessage("This recipe application allows you to search for various recipes, view details of specific recipes, save your favorite recipes, and manage them easily. Use the 'Search' to find recipes, 'View Saved Recipes' to see your saved list, and the 'Delete All Recipes' feature to manage your saved recipes.")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private void loadSavedRecipes() {
        viewModel.getAllRecipes().observe(this, recipeEntities -> {
            // Assuming you have a method in your adapter to update the dataset
            recipesAdapter.updateRecipesFromEntities(recipeEntities); // You may need to implement this method
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LastSearch", searchEditText.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String lastSearch = sharedPreferences.getString("LastSearch", "");
        searchEditText.setText(lastSearch);
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