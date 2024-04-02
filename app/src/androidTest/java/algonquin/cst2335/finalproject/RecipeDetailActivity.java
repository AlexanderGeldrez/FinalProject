package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.squareup.picasso.Picasso;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView recipeImage;
    private TextView recipeSummary;
    private TextView recipeSourceUrl;

    private AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail); // Only call this once

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "recipes").build();

        recipeImage = findViewById(R.id.image_recipe);
        recipeSummary = findViewById(R.id.text_summary);
        recipeSourceUrl = findViewById(R.id.text_source_url);

        int recipeId = getIntent().getIntExtra("recipeId", 0);
        if (recipeId != 0) {
            fetchRecipeDetails(recipeId);
        }
    }

    private void fetchRecipeDetails(int recipeId) {
        RetrofitClientInstance.getRetrofitInstance().create(SpoonacularAPIService.class)
                .getRecipeDetails(recipeId, "132a86e4cc03475f9bfbe3e4493a314a")
                .enqueue(new Callback<RecipeDetailResponse>() {
                    @Override
                    public void onResponse(Call<RecipeDetailResponse> call, Response<RecipeDetailResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            RecipeDetailResponse details = response.body();
                            Picasso.get().load(details.getImage()).into(recipeImage);
                            recipeSummary.setText(details.getSummary());
                            recipeSourceUrl.setText(details.getSpoonacularSourceUrl());
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeDetailResponse> call, Throwable t) {
                        // Consider adding logging or user feedback here
                    }
                });
    }


    private void saveRecipe(RecipeEntity recipe) {
        new Thread(() -> db.recipeDao().insertRecipe(recipe)).start();
    }

    private void deleteRecipe(int recipeId) {
        new Thread(() -> db.recipeDao().deleteRecipe(recipeId)).start();
    }
}
