package algonquin.cst2335.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DictionaryDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "items_db";

    private static final String TABLE_ITEMS = "item";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    public DictionaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT" + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }

    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);

            do {
                int id = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);

                if (id >= 0) {
                    Item item = new Item();
                    item.setId(id);
                    item.setName(name);
                    itemList.add(item);
                } else {
                    // Handle invalid id
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, COLUMN_ID + " = ?", new String[]{String.valueOf(item.getId())});
        db.close();
    }
}
