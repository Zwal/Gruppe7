package de.repair.repairondemand;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import de.repair.repairondemand.SQLlite.AktuellerBenutzer;
import de.repair.repairondemand.SQLlite.SQLite;
import de.repair.repairondemand.SQLlite.SQLiteInit;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mBtnZurück;
    private ImageView mLogohome;
    private TextView mTvName, mTvQuali, mUsername;

    private ImageView mIvDStar1,mIvDStar2,mIvDStar3,mIvDStar4,mIvDStar5,
            mIvKStar1,mIvKStar2,mIvKStar3,mIvKStar4,mIvKStar5,
            mIvPStar1,mIvPStar2,mIvPStar3,mIvPStar4,mIvPStar5,
            mIvFStar1,mIvFStar2,mIvFStar3,mIvFStar4,mIvFStar5,
            mIvGStar1,mIvGStar2,mIvGStar3,mIvGStar4,mIvGStar5;

    private ImageView[] AiVd;
    private ImageView[] AiVk;
    private ImageView[] AiVp;
    private ImageView[] AiVf;
    private ImageView[] AiVg;
    ArrayAdapter<CharSequence> adapterSpinnerProfile;
    private Spinner mSpinProfile;
    private String[] mSpinnerCont;

    String anfrageId = null;

    private String kategorie, anfang, ende, radius, username;

    ArrayList<String> anfrageIds = null;
    ArrayList<String> kompetenz = null;
    ArrayList<String> pünktlichkeit = null;
    ArrayList<String> freundlichkeit = null;
    ArrayList<String> gesamteindruck = null;

    private SQLite sqLite;

    private Intent startActivityIntent;

    protected void onCreate(Bundle savedInstanceState) {
        username = getIntent().getExtras().getString("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        anfrageId = getIntent().getExtras().getString("anfrageId");
        if(getIntent().hasExtra("kategorie")) {
            kategorie = getIntent().getExtras().getString("kategorie");
            anfang = getIntent().getExtras().getString("anfang");
            ende = getIntent().getExtras().getString("ende");
            radius = getIntent().getExtras().getString("radius");
        }
        bindViews();
        init();
    }

    private void bindViews() {
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mTvName = this.findViewById(R.id.name);
        mTvQuali = this.findViewById(R.id.qualifikation);
        mUsername = this.findViewById(R.id.Benutzername);
        mLogohome = this.findViewById(R.id.logohome);

        mIvDStar1 = this.findViewById(R.id.dStar1);
        mIvDStar2 = this.findViewById(R.id.dStar2);
        mIvDStar3 = this.findViewById(R.id.dStar3);
        mIvDStar4 = this.findViewById(R.id.dStar4);
        mIvDStar5 = this.findViewById(R.id.dStar5);

        mIvKStar1 = this.findViewById(R.id.kStar1);
        mIvKStar2 = this.findViewById(R.id.kStar2);
        mIvKStar3 = this.findViewById(R.id.kStar3);
        mIvKStar4 = this.findViewById(R.id.kStar4);
        mIvKStar5 = this.findViewById(R.id.kStar5);

        mIvPStar1 = this.findViewById(R.id.pStar1);
        mIvPStar2 = this.findViewById(R.id.pStar2);
        mIvPStar3 = this.findViewById(R.id.pStar3);
        mIvPStar4 = this.findViewById(R.id.pStar4);
        mIvPStar5 = this.findViewById(R.id.pStar5);

        mIvFStar1 = this.findViewById(R.id.fStar1);
        mIvFStar2 = this.findViewById(R.id.fStar2);
        mIvFStar3 = this.findViewById(R.id.fStar3);
        mIvFStar4 = this.findViewById(R.id.fStar4);
        mIvFStar5 = this.findViewById(R.id.fStar5);

        mIvGStar1 = this.findViewById(R.id.gStar1);
        mIvGStar2 = this.findViewById(R.id.gStar2);
        mIvGStar3 = this.findViewById(R.id.gStar3);
        mIvGStar4 = this.findViewById(R.id.gStar4);
        mIvGStar5 = this.findViewById(R.id.gStar5);
        mSpinProfile = this.findViewById(R.id.spinnerProfile);
        adapterSpinnerProfile = ArrayAdapter.createFromResource(this, R.array.spinnerProfile,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinProfile.setAdapter(adapterSpinnerProfile);
    }

    private void init() {
        mLogohome.setOnClickListener(this);
        mBtnZurück.setOnClickListener(this);
        AiVd = new ImageView[]{mIvDStar1,mIvDStar2,mIvDStar3,mIvDStar4,mIvDStar5};
        AiVk = new ImageView[]{mIvKStar1,mIvKStar2,mIvKStar3,mIvKStar4,mIvKStar5};
        AiVp = new ImageView[]{mIvPStar1,mIvPStar2,mIvPStar3,mIvPStar4,mIvPStar5};
        AiVf = new ImageView[]{mIvFStar1,mIvFStar2,mIvFStar3,mIvFStar4,mIvFStar5};
        AiVg = new ImageView[]{mIvGStar1,mIvGStar2,mIvGStar3,mIvGStar4,mIvGStar5};
        mUsername.setText(username);
        mSpinnerCont = getResources().getStringArray(R.array.spinnerProfile);
        mSpinProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(mSpinnerCont[position].equals("Ausloggen")){
                    ausloggen();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        getAnfrageUserData(getAnfrageUserId());

        getAnfrageIds(getAnfrageUserId());
    }

    // hier wird der User ausgeloggt
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
                startActivityIntent =  new Intent(this, AuftragAnsicht.class);
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

    // hier wird die User Id zu einer Anfrage abgefragt
    public String getAnfrageUserId(){
        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        String fk = null;
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_ANFRAGE, // a. table
                            new String[]{SQLiteInit.COLUMN_BENUTZER_ID_FK}, // b. column names
                            " anfrage_id_pk = ?", // c. selections
                            new String[] {anfrageId}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit
            if(cursor != null){
                cursor.moveToFirst();
                fk = cursor.getString(0);
            }
        }catch(Exception ex){
        }
        return fk;
    }

    // hier werden der Name und die Qualifikation eines Auftrags aus der Datenbank abgefragt
    // und den Feldern zur Anzeige zugewiesen
    public void getAnfrageUserData(String anfrageUserId){
        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_PRIVATPERSON, // a. table
                            new String[]{SQLiteInit.COLUMN_NAME, SQLiteInit.COLUMN_QUALIFIKATION}, // b. column names
                            " benutzer_id_fk = ?", // c. selections
                            new String[] {anfrageUserId}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit
            if(cursor != null){
                cursor.moveToFirst();
                mTvName.setText("Name: " + cursor.getString(0));
                mTvQuali.setText("Qualifikation: " + cursor.getString(1));
            }else{
                cursor =
                        db.query(SQLiteInit.TABLE_FIRMA, // a. table
                                new String[]{SQLiteInit.COLUMN_NAME, SQLiteInit.COLUMN_TÄTIGKEITSFELDER}, // b. column names
                                " benutzer_id_fk = ?", // c. selections
                                new String[] {anfrageUserId}, // d. selections args
                                null, // e. group by
                                null, // f. having
                                null, // g. order by
                                null); // h. limit
                cursor.moveToFirst();
                mTvName.setText("Name: " + cursor.getString(0));
                mTvQuali.setText("Qualifikation: " + cursor.getString(1));
            }
        }catch(Exception ex){
        }
    }

    // hier werden alle Anfragen Ids des Users abgefragt, welcher die aktuelle Anfrage gestellt hat
    public void getAnfrageIds(String anfrageUserId){
        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        anfrageIds = new ArrayList<String>();
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_ANFRAGE, // a. table
                            new String[]{SQLiteInit.COLUMN_ANFRAGE_ID_PK}, // b. column names
                            " benutzer_id_fk = ?", // c. selections
                            new String[] {anfrageUserId}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit

            if (cursor != null) {
                int count = cursor.getCount()-1;
                cursor.moveToFirst();
                while(count >= 0) {
                    this.anfrageIds.add(cursor.getString(0));
                    count--;
                    if(count >= 0){
                        cursor.moveToNext();
                    }
                }
                if(this.anfrageIds.size() != 0) {
                    getFeedback();
                }
            }
        }catch(Exception ex){
        }
    }

    // hier werden die Feedbacks zu allen Anfragen geholt, die vom aktuellen User gestellt worden sind
    public void getFeedback(){
        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        kompetenz = new ArrayList<String>();
        pünktlichkeit = new ArrayList<String>();
        freundlichkeit = new ArrayList<String>();
        gesamteindruck = new ArrayList<String>();

        for(String id : anfrageIds) {
            try {
                Cursor cursor =
                        db.query(SQLiteInit.TABLE_AUFTRAGS_FEEDBACK, // a. table
                                new String[]{SQLiteInit.COLUMN_KOMPETENZ, SQLiteInit.COLUMN_FREUNDLICHKEIT,
                                        SQLiteInit.COLUMN_PÜNKTLICHKEIT, SQLiteInit.COLUMN_GESAMTEINDRUCK}, // b. column names
                                " anfrage_id_fk = ?", // c. selections
                                new String[]{id}, // d. selections args
                                null, // e. group by
                                null, // f. having
                                null, // g. order by
                                null); // h. limit

                if (cursor != null) {
                    cursor.moveToFirst();
                    kompetenz.add(cursor.getString(0));
                    freundlichkeit.add(cursor.getString(1));
                    pünktlichkeit.add(cursor.getString(2));
                    gesamteindruck.add(cursor.getString(3));
                }
            } catch (Exception ex) {
            }
        }
        feedback();
    }

    // hier werden die Methoden zur Feedback Berechnung und zum Feedback selbst aufgerufen
    public void feedback(){
        int kompetenz = getMittelwert(this.kompetenz);
        int freundlichkeit = getMittelwert(this.freundlichkeit);
        int pünktlichkeit = getMittelwert(this.pünktlichkeit);
        int gesamteindruck = getMittelwert(this.gesamteindruck);
        int mittelwert = countMiddle / this.kompetenz.size();

        setFeedback(kompetenz, AiVk);
        setFeedback(freundlichkeit, AiVf);
        setFeedback(pünktlichkeit, AiVp);
        setFeedback(gesamteindruck, AiVg);
        setFeedback(mittelwert, AiVd);
    }

    // hier werden die entsprechenden Feedback Sterne gesetzt
    public void setFeedback(int count, ImageView[] iVA){
        for(int i = 0; i < count; i++){
            iVA[i].setImageDrawable(this.getDrawable(R.mipmap.baseline_star_black_24));
        }
    }

    int countMiddle = 0;

    // hier werden die Feedbacks ausgerechnet
    public int getMittelwert(ArrayList<String> list){
        int count = 0;
        for(String zahl : list){
            count = count + Integer.parseInt(zahl);
        }
        countMiddle+=count;
        int middle = count / list.size();
        return middle;
    }
}
