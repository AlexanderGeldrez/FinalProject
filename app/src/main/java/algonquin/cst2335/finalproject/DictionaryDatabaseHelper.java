package algonquin.cst2335.finalproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import  java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "search_terms";
    private static final String COLUMN_ID = "id";
    private static final String COL_WORD = "word";
    private static final String COLUMN_TERM = "term";

    public DictionaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TERM + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addSearchTerm(String term) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TERM, term);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteSearchTerm(String term) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_TERM + "=?", new String[]{term});
        db.close();
    }



    public List<String> getAllSearchTerms() {
        List<String> searchTerms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        String[] columnNames = cursor.getColumnNames();
        for (String columnName : columnNames) {
            Log.d("Column Name", columnName);
        }

        cursor.close();
        db.close();
        return searchTerms;
    }
}
