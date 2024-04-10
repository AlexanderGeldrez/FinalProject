package algonquin.cst2335.finalproject;
import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


    @Database(entities = {WordDefinition.class}, version = 1, exportSchema = false)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract WordDefinitionDAO wordDefinitionDAO();

        private static volatile AppDatabase INSTANCE;

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


