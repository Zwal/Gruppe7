package de.repair.repairondemand;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import de.repair.repairondemand.SQLlite.AktuellerBenutzer;
import de.repair.repairondemand.SQLlite.SQLite;
import de.repair.repairondemand.SQLlite.SQLiteInit;

public class AuftragAnsicht extends AppCompatActivity implements View.OnClickListener {

    private SQLite sqLite;
    private Button mBtnProfil, mBtnAngebotAbgeben;
    private ImageButton mBtnZurück;
    private ImageView mImViAuftragBild;
    private ImageView mLogohome;
    private TextView mTvBeschreibung, mTvStandort;

    private Intent startActivityIntent;

    private String anfrageId;

    private String kategorie, anfang, ende, radius, username;
    private String[] mSpinnerCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auftrag_ansicht);
        bindViews();
        init();
        // über Intent von AngebotSuchen.java, kommt für den
        // folgenden Aufruf die ID des Auftrags
        username = getIntent().getExtras().getString("username");
        if(getIntent().hasExtra("anfrageId")) {
            anfrageId = getIntent().getExtras().getString("anfrageId");
            kategorie = getIntent().getExtras().getString("kategorie");
            anfang = getIntent().getExtras().getString("anfang");
            ende = getIntent().getExtras().getString("ende");
            radius = getIntent().getExtras().getString("radius");
            getAuftrag(anfrageId);
        }
    }


    private void bindViews() {
        mLogohome = this.findViewById(R.id.logohome);
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mBtnProfil = this.findViewById(R.id.btnProfilansehen);
        mBtnAngebotAbgeben = this.findViewById(R.id.btnAngebotabgeben);
        mImViAuftragBild = this.findViewById(R.id.auftrag_bild);
        mTvBeschreibung = this.findViewById(R.id.beschreibung);
        mTvStandort = this.findViewById(R.id.standort);
    }

    private void init() {
        mLogohome.setOnClickListener(this);
        mBtnZurück.setOnClickListener(this);
        mBtnProfil.setOnClickListener(this);
        mBtnAngebotAbgeben.setOnClickListener(this);
        mSpinnerCont = getResources().getStringArray(R.array.spinnerProfile);
    }

    // hier meldet sich der User ab
    public void ausloggen(){
        new AktuellerBenutzer().deleteAktuellerUser(this);
        startActivityIntent =  new Intent(this, MainActivity.class);
        startActivity(startActivityIntent);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.logohome:
                startActivityIntent =  new Intent(this, Home.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnZurück:
                startActivityIntent = new Intent(this, AufträgeSuchen.class);
                startActivityIntent.putExtra("kategorie", kategorie);
                startActivityIntent.putExtra("anfang", anfang);
                startActivityIntent.putExtra("ende", ende);
                startActivityIntent.putExtra("radius", radius);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnAngebotabgeben:
                startActivityIntent = new Intent(this, AngebotAbgeben.class);
                startActivityIntent.putExtra("anfrageId", anfrageId);
                startActivityIntent.putExtra("kategorie", kategorie);
                startActivityIntent.putExtra("anfang", anfang);
                startActivityIntent.putExtra("ende", ende);
                startActivityIntent.putExtra("radius", radius);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnProfilansehen:
                startActivityIntent = new Intent(this, Profile.class);
                startActivityIntent.putExtra("anfrageId", anfrageId);
                startActivityIntent.putExtra("kategorie", kategorie);
                startActivityIntent.putExtra("anfang", anfang);
                startActivityIntent.putExtra("ende", ende);
                startActivityIntent.putExtra("radius", radius);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
        }
    }

    // hier werden Daten von einer Anfrage zu einer Anfrage Id abgefragt
    public void getAuftrag(String auftragsId){
        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        String beschreibung;
        String adresse_fk;
        byte[] bild;
        String stadt;
        try{
            Log.e("cursor","start");
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_ANFRAGE, // a. table
                            new String[]{SQLiteInit.COLUMN_BESCHREIBUNG, SQLiteInit.COLUMN_ADRESSE_ID_FK,
                            SQLiteInit.COLUMN_BILD}, // b. column names
                            " anfrage_id_pk = ? ", // c. selections
                            new String[] {auftragsId}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit
            cursor.moveToFirst();
            beschreibung = cursor.getString(0);
            adresse_fk = cursor.getString(1);
            bild = cursor.getBlob(2);

            cursor =
                    db.query(SQLiteInit.TABLE_ADRESSE, // a. table
                            new String[]{SQLiteInit.COLUMN_ORT}, // b. column names
                            " adresse_id_pk = ? ", // c. selections
                            new String[] {adresse_fk}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit
            cursor.moveToFirst();
            stadt = cursor.getString(0);
            setValues(beschreibung, stadt, bild);
        }catch(Exception ex){
        }
    }

    // hier werden die Daten der zu anzeigenden Anfrage gesetzt
    public void setValues(String beschreibung, String stadt, byte[] bild) {
        mTvBeschreibung.setText(beschreibung);
        mTvStandort.setText(stadt);
        if(bild != null){
            mImViAuftragBild.setImageBitmap(
                    BitmapFactory.decodeByteArray(bild, 0, bild.length));
        }
    }
}
