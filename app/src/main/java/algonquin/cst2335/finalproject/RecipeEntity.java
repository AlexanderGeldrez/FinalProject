package algonquin.cst2335.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class RecipeEntity {


    @PrimaryKey(autoGenerate = true)
    public int id; // Letting Room auto-generate ID

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "summary")
    public String summary;

    @ColumnInfo(name = "source_url")
    public String sourceUrl;
}