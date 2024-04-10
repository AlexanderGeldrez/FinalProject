package algonquin.cst2335.finalproject;

public class RecipeDetailResponse {
    private String image;
    private String summary;
    private String spoonacularSourceUrl;

    // Getter methods
    /**
     * Retrieves the URL of the image associated with the recipe.
     *
     * @return The image URL.
     */
    public String getImage() {
        return image;
    }

    /**
     * Retrieves the summary of the recipe.
     *
     * @return The recipe summary.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Retrieves the source URL of the recipe on Spoonacular.
     *
     * @return The Spoonacular source URL.
     */
    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    /**
     * Sets the URL of the image associated with the recipe.
     *
     * @param image The image URL to set.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Sets the summary of the recipe.
     *
     * @param summary The recipe summary to set.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Sets the source URL of the recipe on Spoonacular.
     *
     * @param spoonacularSourceUrl The Spoonacular source URL to set.
     */
    public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
        this.spoonacularSourceUrl = spoonacularSourceUrl;
    }
}