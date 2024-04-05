package algonquin.cst2335.finalproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DictionaryDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Dictionary";
    private static final String COLUMN_WORD = "word";
    private static final String COLUMN_DEFINITION = "definition";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_WORD + " TEXT, " +
                COLUMN_DEFINITION + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveDefinition(String word, String definition) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORD, word);
        values.put(COLUMN_DEFINITION, definition);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<DictionaryModel> getDefinitions(String word) {
        ArrayList<DictionaryModel> definitions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_WORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{word});
        if (cursor.moveToFirst()) {
            do {
                int definitionIndex = cursor.getColumnIndex(COLUMN_DEFINITION);
                String definition = cursor.getString(definitionIndex);
definitions.add(new DictionaryModel(word, definition));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return definitions;
    }

    public ArrayList<String> getSavedTerms() {
        ArrayList<String> savedTerms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMN_WORD + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                savedTerms.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return savedTerms;
    }

    public void deleteDefinition(String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_WORD + "=?", new String[]{word});
        db.close();
    }
}
