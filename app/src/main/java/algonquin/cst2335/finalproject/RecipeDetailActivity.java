package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView recipeImage;
    private TextView recipeSummary, recipeSourceUrl;
    private Button saveRecipeButton, deleteRecipeButton;
    private RecipeViewModel viewModel;
    private RecipeEntity currentRecipe = null;
    private final String apiKey = "6c22d1a8b6ab41e79773c28f1c6ed9eb";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        recipeImage = findViewById(R.id.recipeImage);
        recipeSummary = findViewById(R.id.recipeSummary);
        recipeSourceUrl = findViewById(R.id.recipeSourceUrl);
        saveRecipeButton = findViewById(R.id.saveRecipeButton);
        deleteRecipeButton = findViewById(R.id.deleteRecipeButton); // Corrected

        int recipeId = getIntent().getIntExtra("recipeId", -1);
        if (recipeId != -1) {
            fetchRecipeDetails(recipeId);
        }

        saveRecipeButton.setOnClickListener(v -> saveRecipe());
        deleteRecipeButton.setOnClickListener(v -> deleteRecipe());
    }
    private void fetchRecipeDetails(int recipeId) {
        RetrofitClientInstance.getRetrofitInstance().create(SpoonacularAPIService.class)
                .getRecipeDetails(recipeId, apiKey).enqueue(new Callback<RecipeDetailResponse>() {
                    @Override
                    public void onResponse(Call<RecipeDetailResponse> call, Response<RecipeDetailResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            RecipeDetailResponse details = response.body();
                            currentRecipe = new RecipeEntity();
                            currentRecipe.id = recipeId; // You might need to handle ID differently
                            currentRecipe.image = details.getImage();
                            currentRecipe.summary = details.getSummary();
                            currentRecipe.sourceUrl = details.getSpoonacularSourceUrl();
                            updateUIWithRecipeDetails(currentRecipe);
                            updateButtonsVisibility(true); // Show save button
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeDetailResponse> call, Throwable t) {
                        Toast.makeText(RecipeDetailActivity.this, "Error fetching recipe details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveRecipe() {
        if (currentRecipe != null) {
            viewModel.insertRecipe(currentRecipe);
            Toast.makeText(this, "Recipe saved!", Toast.LENGTH_SHORT).show();
            updateButtonsVisibility(false); // Hide save, show delete button
        }
    }

    private void deleteRecipe() {
        if (currentRecipe != null) {
            viewModel.deleteRecipe(currentRecipe.id);
            Toast.makeText(this, "Recipe deleted!", Toast.LENGTH_SHORT).show();
            updateButtonsVisibility(true); // Show save, hide delete button
        }
    }

    private void updateUIWithRecipeDetails(RecipeEntity recipe) {
        Picasso.get().load(recipe.image).into(recipeImage);
        recipeSummary.setText(recipe.summary);
        recipeSourceUrl.setText(recipe.sourceUrl);
    }

    private void updateButtonsVisibility(boolean showSave) {
        saveRecipeButton.setVisibility(showSave ? View.VISIBLE : View.GONE);
        deleteRecipeButton.setVisibility(showSave ? View.GONE : View.VISIBLE);
    }
}