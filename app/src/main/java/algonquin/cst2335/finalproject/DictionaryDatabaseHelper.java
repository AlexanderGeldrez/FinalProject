package algonquin.cst2335.finalproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DictionaryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_DEFINITIONS = "definitions";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_WORD = "word";
    private static final String COLUMN_DEFINITION = "definition";

    // SQL statement to create the definitions table
    private static final String CREATE_TABLE_DEFINITIONS = "CREATE TABLE " +
            TABLE_DEFINITIONS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_WORD + " TEXT," +
            COLUMN_DEFINITION + " TEXT" +
            ")";

    private SQLiteDatabase db;

    public DictionaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DEFINITIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEFINITIONS);
        onCreate(db);
    }

    public void addDefinition(String word, String definition) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORD, word);
        values.put(COLUMN_DEFINITION, definition);
        db.insert(TABLE_DEFINITIONS, null, values);
    }

    public List<String> getDefinitions(String word) {
        List<String> definitions = new ArrayList<>();
        Cursor cursor = db.query(TABLE_DEFINITIONS, new String[]{COLUMN_DEFINITION}, COLUMN_WORD + "=?", new String[]{word}, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex(COLUMN_DEFINITION);
            if (columnIndex != -1) {
                while (cursor.moveToNext()) {
                    String definition = cursor.getString(columnIndex);
                    definitions.add(definition);
                }
            }
            cursor.close();
        }
        return definitions;
    }



    public void deleteDefinition(String word, String definition) {
        db.delete(TABLE_DEFINITIONS, COLUMN_WORD + "=? AND " + COLUMN_DEFINITION + "=?", new String[]{word, definition});
    }
}
