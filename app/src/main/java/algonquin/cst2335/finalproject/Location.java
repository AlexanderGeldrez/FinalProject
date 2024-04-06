package algonquin.cst2335.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations") // Ensure table name is specified
public class Location {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "latitude")
    public String latitude;

    @ColumnInfo(name = "longitude")
    public String longitude;

    // Constructor
    public Location(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }
    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
}
