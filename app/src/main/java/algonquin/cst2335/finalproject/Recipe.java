package algonquin.cst2335.finalproject;

/**
 * Represents a recipe with its ID, title, and image.
 */
public class Recipe {
    private int id;
    private String title;
    private String image;

    /**
     * Constructs a new Recipe with the specified ID, title, and image.
     *
     * @param id    The ID of the recipe.
     * @param title The title of the recipe.
     * @param image The URL of the image associated with the recipe.
     */
    public Recipe(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    /**
     * Retrieves the ID of the recipe.
     *
     * @return The ID of the recipe.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the recipe.
     *
     * @param id The new ID to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the title of the recipe.
     *
     * @return The title of the recipe.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the recipe.
     *
     * @param title The new title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the URL of the image associated with the recipe.
     *
     * @return The URL of the image.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the URL of the image associated with the recipe.
     *
     * @param image The new image URL to be set.
     */
    public void setImage(String image) {
        this.image = image;
    }
}
