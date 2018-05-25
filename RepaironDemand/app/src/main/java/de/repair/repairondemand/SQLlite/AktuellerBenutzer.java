package de.repair.repairondemand.SQLlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AktuellerBenutzer {

    private SQLite sqLite;

    public String getId(Context context){
        sqLite = new SQLite(context);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        String id = null;
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_AKTUELLER_BENUTZER, // a. table
                            new String[]{SQLiteInit.COLUMN_BENUTZER_ID_PK}, // b. column names
                            null, // c. selections
                            null, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit

            if (cursor != null) {
                cursor.moveToFirst();
                id = cursor.getString(0);
            }
        }catch(Exception ex){
        }
        return id;
    }

    public void writeId(String id, Context context) {
        sqLite = new SQLite(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = sqLite.getReadableDatabase();
        try {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(SQLiteInit.COLUMN_BENUTZER_ID_PK, Integer.getInteger(id));

            db.update(SQLiteInit.TABLE_AKTUELLER_BENUTZER, values, " rowid = 1", null);
            Log.e("update", id);
        } catch (Exception ex) {
        }
    }

    public String getIdUser(Context context, String username){
        sqLite = new SQLite(context);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        String id = null;
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_BENUTZERKONTO, // a. table
                            new String[]{SQLiteInit.COLUMN_BENUTZER_ID_PK}, // b. column names
                            " username = ?", // c. selections
                            new String[]{username}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit

            if (cursor != null) {
                cursor.moveToFirst();
                id = cursor.getString(0);
            }
        }catch(Exception ex){
        }
        Log.e("readid", id);
        return id;
    }
}