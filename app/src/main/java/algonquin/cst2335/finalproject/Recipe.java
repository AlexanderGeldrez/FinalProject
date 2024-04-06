package algonquin.cst2335.finalproject;

public class Recipe {
    private int id;
    private String title;
    private String image;

    // Constructor
    public Recipe(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    // Getter for the ID
    public int getId() {
        return id;
    }

    // Setter for the ID, if needed
    public void setId(int id) {
        this.id = id;
    }

    // Getter for the title
    public String getTitle() {
        return title;
    }

    // Setter for the title, if needed
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for the image URL
    public String getImage() {
        return image;
    }

    // Setter for the image URL, if needed
    public void setImage(String image) {
        this.image = image;
    }

}