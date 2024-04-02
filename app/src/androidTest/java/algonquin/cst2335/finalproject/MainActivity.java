package algonquin.cst2335.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recipesRecyclerView;
    private RecipesAdapter recipesAdapter;
    private final String apiKey = "132a86e4cc03475f9bfbe3e4493a314a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recipesRecyclerView = findViewById(R.id.recipesRecyclerView);

        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipesAdapter = new RecipesAdapter(this, new ArrayList<>());
        recipesRecyclerView.setAdapter(recipesAdapter);

        searchButton.setOnClickListener(view -> performSearch());
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

                        }
                        Log.d("API Response", "Response: " + response.body());

                    }


                    @Override
                    public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                        // Handle failure
                    }
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

}
