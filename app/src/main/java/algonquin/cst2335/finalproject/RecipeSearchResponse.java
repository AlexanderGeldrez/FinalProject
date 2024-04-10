package algonquin.cst2335.finalproject;

import java.util.List;

public class RecipeSearchResponse {
    private List<Recipe> results;

    /**
     * Retrieves the list of Recipe objects from the response.
     *
     * @return The list of Recipe objects.
     */
    public List<Recipe> getResults() {
        return results;
    }

    /**
     * Sets the list of Recipe objects in the response.
     *
     * @param results The list of Recipe objects to set.
     */
    public void setResults(List<Recipe> results) {
        this.results = results;
    }
}