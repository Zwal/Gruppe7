package de.repair.repairondemand.SQLlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class SQLite extends SQLiteOpenHelper{

    private static final String LOG_TAG = SQLite.class.getSimpleName();

    public static final String DB_NAME = "repair";
    public static final int DB_VERSION = 2;

    public SQLite(Context context) {
        //super(context, "PLATZHALTER_DATENBANKNAME", null, 1);
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    SQLiteInit sqLliteInit;

    String[] tableArray;

    // Die onCreate-Methode wird nur aufgerufen, falls die Datenbank noch nicht existiert
    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLliteInit = new SQLiteInit();
        tableArray = sqLliteInit.tableArray();
        try {
            for (String tableName : tableArray) {
                Log.d(LOG_TAG, "Die Tabelle: " + tableName + " wird angelegt.");
                db.execSQL(tableName);
            }
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
