package de.repair.repairondemand;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import de.repair.repairondemand.SQLlite.Modells.Angebot;
import de.repair.repairondemand.SQLlite.SQLite;
import de.repair.repairondemand.SQLlite.SQLiteInit;

public class AngebotAbgebenDialog extends DialogFragment {


    private SQLite sqLite;


    private Angebot angebot;

    private Intent startActivityIntent;
    private  String username;

    public AngebotAbgebenDialog() {
    }

    public static AngebotAbgebenDialog newInstance(String title, Angebot angebot, String username) {
        AngebotAbgebenDialog frag = new AngebotAbgebenDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("preis", angebot.getmPreis());
        args.putString("start", angebot.getmZeitStart());
        args.putString("ende", angebot.getmZeitEnde());
        args.putString("bschreibung", angebot.getmBeschreibung());
        args.putString("anfrageId", angebot.getmAnfrageId());
        args.putString("benutzerId", angebot.getmBenutzerId());
        args.putString("username", username);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        angebot = new Angebot();
        this.angebot.setmPreis(getArguments().getString("preis"));
        this.angebot.setmZeitStart(getArguments().getString("start"));
        this.angebot.setmZeitEnde(getArguments().getString("ende"));
        this.angebot.setmBeschreibung(getArguments().getString("beschreibung"));
        this.angebot.setmBenutzerId(getArguments().getString("benutzerId"));
        this.angebot.setmAnfrageId(getArguments().getString("anfrageId"));
        this.username = getArguments().getString("username");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Angebot verbindlich abgeben?");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                writeAngebotDb();
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return alertDialogBuilder.create();
    }

    // hier wird das Angebot in die Datenbank geschrieben
    public void writeAngebotDb(){
        sqLite = new SQLite(getActivity());
        SQLiteDatabase db = sqLite.getWritableDatabase();
        // Map wird mit Variablen erzeugt, wo Spaltennamen die PKs sind
        ContentValues values = new ContentValues();
        values.put(SQLiteInit.COLUMN_PREISVORSTELLUNG, angebot.getmPreis());
        values.put(SQLiteInit.COLUMN_STARTTERMIN, angebot.getmZeitStart());
        values.put(SQLiteInit.COLUMN_ENDTERMIN, angebot.getmZeitEnde());
        values.put(SQLiteInit.COLUMN_BESCHREIBUNG, angebot.getmBeschreibung());
        values.put(SQLiteInit.COLUMN_ANFRAGE_ID_FK, angebot.getmAnfrageId());
        values.put(SQLiteInit.COLUMN_BENUTZER_ID_FK, angebot.getmBenutzerId());

        // neue Zeile wird eingefügt, PK der neuen Zeile wird zurückgegeben
        long newRowId = db.insert(SQLiteInit.TABLE_ANGEBOT, null, values);

        Toast.makeText(getActivity(), "Angebot verbindlich abgegeben.", Toast.LENGTH_LONG).show();
        startActivityIntent =  new Intent(getActivity(), Home.class);
        startActivityIntent.putExtra("username", username);
        startActivity(startActivityIntent);
    }
}

