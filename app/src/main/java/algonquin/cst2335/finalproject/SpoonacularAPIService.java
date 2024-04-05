package algonquin.cst2335.finalproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularAPIService {
    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> searchRecipes(@Query("query") String query, @Query("apiKey") String apiKey);

    @GET("recipes/{id}/information")
    Call<RecipeDetailResponse> getRecipeDetails(@Path("id") int id, @Query("apiKey") String apiKey);

}
