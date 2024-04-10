package algonquin.cst2335.finalproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * This class represents the Room database for storing word definitions.
 * It provides access to the WordDefinitionDAO to interact with the database.
 *
 * @author Tamana Kaushal
 */
@Database(entities = {WordDefinition.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Get an instance of the WordDefinitionDAO.
     *
     * @return The WordDefinitionDAO instance.
     */
    public abstract WordDefinitionDAO wordDefinitionDAO();

    private static volatile AppDatabase INSTANCE;

    /**
     * Get a singleton instance of the AppDatabase.
     *
     * @param context The application context.
     * @return The singleton instance of AppDatabase.
     */
    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "WordDefinition_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
