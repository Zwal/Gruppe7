package de.repair.repairondemand.SQLlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AktuellerBenutzer {

    private SQLite sqLite;

    // Hier wird die Id des aktuell eingelogten Users geholt
    public String getId(Context context){
        sqLite = new SQLite(context);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        String id = null;
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_AKTUELLER_BENUTZER, // a. table
                            new String[]{SQLiteInit.COLUMN_BENUTZER_ID_PK}, // b. column names
                            " rowid = ?", // c. selections
                            new String[]{"1"}, // d. selections args
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

    // hier wird die Ids des aktuell eingolggten Users in der Tabelle aktueller_benutzer gespeichert
    public void writeId(String id, Context context) {
        sqLite = new SQLite(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = sqLite.getReadableDatabase();
        try {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(SQLiteInit.COLUMN_BENUTZER_ID_PK, id);

            db.delete(SQLiteInit.TABLE_AKTUELLER_BENUTZER, "rowid" + "=" + 1, null);
            long row = db.insert(SQLiteInit.TABLE_AKTUELLER_BENUTZER, null, values);
            db.close();
            Log.e("insertUser", "" + id + " " + getId(context) + " " + row);
        } catch (Exception ex) {
        }
    }

    // hier wird die Id des Users aus der Tabelle Benutzerkonto geholt, der sich anmeldet
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
        return id;
    }

    // hier wird abgefragt ob der aktuell angemeldete User eine Privatperson oder eine Firma ist
    public String getTypeUser(Context context){
        sqLite = new SQLite(context);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        String type = null;
        String id = getId(context);
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_PRIVATPERSON, // a. table
                            new String[]{SQLiteInit.COLUMN_NAME}, // b. column names
                            " benutzer_id_fk = ?", // c. selections
                            new String[] {id}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit
            if(cursor != null){
                cursor.moveToFirst();
                type = "privat";
            }else{
                cursor =
                        db.query(SQLiteInit.TABLE_FIRMA, // a. table
                                new String[]{SQLiteInit.COLUMN_NAME}, // b. column names
                                " benutzer_id_fk = ?", // c. selections
                                new String[] {id}, // d. selections args
                                null, // e. group by
                                null, // f. having
                                null, // g. order by
                                null); // h. limit
                cursor.moveToFirst();
                type = "firma";
            }
        }catch(Exception ex){
        }
        return type;
    }

    // hier wird der aktuelle User in die Datenbank geschrieben
    public void deleteAktuellerUser(Context context){
        sqLite = new SQLite(context);
        // Gets the data repository in write mode
        SQLiteDatabase db = sqLite.getReadableDatabase();
        try {
            int i = db.delete(SQLiteInit.TABLE_AKTUELLER_BENUTZER, "rowid" + "=" + 1, null);
            Log.e("tag", ""+ i);
            db.close();
        } catch (Exception ex) {
        }
    }
}
