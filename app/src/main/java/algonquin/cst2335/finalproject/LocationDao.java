package algonquin.cst2335.finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert
    void insert(Location location);



    @Query("DELETE FROM locations WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM locations")
    LiveData<List<Location>> getAllLocations();
}
