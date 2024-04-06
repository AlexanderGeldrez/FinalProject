package algonquin.cst2335.finalproject;

public class RecipeDetailResponse {
    private String image;
    private String summary;
    private String spoonacularSourceUrl;

    // Getter methods
    public String getImage() {
        return image;
    }

    public String getSummary() {
        return summary;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    // Setter methods (if needed)
    public void setImage(String image) {
        this.image = image;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
        this.spoonacularSourceUrl = spoonacularSourceUrl;
    }
}