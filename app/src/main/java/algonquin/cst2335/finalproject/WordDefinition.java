package algonquin.cst2335.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "WordDefintion")
public class WordDefinition {

    @PrimaryKey(autoGenerate = true)
        private int id;

    @ColumnInfo(name = "word")
        private String word; // The word
    @ColumnInfo(name = "defintion")
    private String definition; // The definition of the word

    public WordDefinition(String definition, String word, int id) {
        this.definition = definition;
        this.word = word;
        this.id = id;
    }

    public WordDefinition() {

    }


    public String getDefinition() {
        return definition;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
