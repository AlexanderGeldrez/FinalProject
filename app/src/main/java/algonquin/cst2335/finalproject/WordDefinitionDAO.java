package algonquin.cst2335.finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface WordDefinitionDAO {
    @Insert
    void insert(WordDefinition wordDefinition);
    @Query("DELETE FROM worddefintion WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM worddefintion")
    void deleteAll();

    @Query("SELECT * FROM worddefintion")
    LiveData<List<WordDefinition>> getAllWordDefinition();
}


