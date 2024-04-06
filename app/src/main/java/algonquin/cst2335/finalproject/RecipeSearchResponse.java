package algonquin.cst2335.finalproject;

import java.util.List;

public class RecipeSearchResponse {
    private List<Recipe> results;

    // Getter for the list of Recipe objects
    public List<Recipe> getResults() {
        return results;
    }

    // Setter for the list of Recipe objects
    public void setResults(List<Recipe> results) {
        this.results = results;
    }
}