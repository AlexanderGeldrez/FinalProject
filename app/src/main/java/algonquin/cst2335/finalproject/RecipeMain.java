package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeMain extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton, viewSavedRecipesButton;
    private RecyclerView recipesRecyclerView;
    private RecipesAdapter recipesAdapter;
    private RecipeViewModel viewModel;
    private final String apiKey = "740132ab259d463e8250e8ac1f38bc09";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recipesRecyclerView = findViewById(R.id.recipesRecyclerView);
        viewSavedRecipesButton = findViewById(R.id.viewSavedRecipesButton); // Add this button in your layout XML

        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipesAdapter = new RecipesAdapter(this, new ArrayList<>());
        recipesRecyclerView.setAdapter(recipesAdapter);

        viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        searchButton.setOnClickListener(view -> performSearch());

        recipesAdapter.setClickListener((view, position) -> {
            Recipe recipe = recipesAdapter.getItem(position);
            openRecipeDetails(recipe.getId());
        });

        viewSavedRecipesButton.setOnClickListener(view -> loadSavedRecipes());
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

    private void openRecipeDetails(int recipeId) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }
}
