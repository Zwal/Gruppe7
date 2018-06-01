package de.repair.repairondemand;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
//import android.widget.ListView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.repair.repairondemand.AuftragList.AuftragAdapter;
import de.repair.repairondemand.SQLlite.AktuellerBenutzer;
import de.repair.repairondemand.SQLlite.Modells.Adresse;
import de.repair.repairondemand.SQLlite.Modells.Anfrage;
import de.repair.repairondemand.SQLlite.SQLite;
import de.repair.repairondemand.SQLlite.SQLiteInit;


public class AufträgeSuchen extends AppCompatActivity implements View.OnClickListener {
    private SQLite sqLite;
    private int checkBtn;
    public Button mBtnZurück, mBtnRepAnfang, mBtnRepEnde, mBtnSuchen, mBtnKlappen;
    private Spinner mSpinKategorie;
    private SeekBar mSeekbar;
    private TextView mTvRadius, mTvDateError, mTvKeineAufträge;
    private ListView mLv;
    private View mklapView;
    private String check = null;
    private boolean keineAufträge;
    private String kategorie, anfang, ende, radius;
    private Intent startActivityIntent;
    // private ListView mListResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anfrage_suche);
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
        mTvKeineAufträge = this.findViewById(R.id.txtKeineAufträge);
        mklapView = this.findViewById(R.id.constraintLayout1);
        mLv = this.findViewById(R.id.listV);
        mBtnKlappen = this.findViewById(R.id.btnKlappen);
        mBtnZurück = this.findViewById(R.id.zurück);
        mBtnRepAnfang = this.findViewById(R.id.btnDateRepAnfang);
        mBtnRepEnde = this.findViewById(R.id.btnDateRepEnde);
        mSpinKategorie = this.findViewById(R.id.kategorie);
        mBtnSuchen = this.findViewById(R.id.btnSuchen);
        mTvRadius = this.findViewById(R.id.txtRadius);
        mTvDateError = this.findViewById(R.id.txtDateError);
        ArrayAdapter<CharSequence> adapterKategorie = ArrayAdapter.createFromResource(this, R.array.category,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinKategorie.setAdapter(adapterKategorie);
        mSeekbar = this.findViewById(R.id.seekBar);
        check = "zu";
    }

    private void init() {
        mTvDateError.setTextColor(Color.WHITE);
        mTvKeineAufträge.setTextColor(Color.WHITE);
        mBtnZurück.setOnClickListener(this);
        mBtnKlappen.setOnClickListener(this);
        mBtnRepAnfang.setOnClickListener(this);
        mBtnRepEnde.setOnClickListener(this);
        mBtnSuchen.setOnClickListener(this);
        mSeekbar.setMax(100);
        mSeekbar.setProgress(50);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Write code to perform some action when progress is changed.
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is started.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is stopped.
                mTvRadius.setText(String.valueOf(seekBar.getProgress()) + " km");
            }
        });
        mLv.setVisibility(ListView.INVISIBLE);
        if(kategorie != null){
            mSpinKategorie.setSelection(Integer.parseInt(getKategorie(kategorie)));
            mBtnRepAnfang.setText(anfang);
            mBtnRepEnde.setText(ende);
            mSeekbar.setProgress(Integer.valueOf(radius));
        }
    }

    boolean b;

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.zurück:
                startActivityIntent = new Intent(this, Home.class);
                startActivity(startActivityIntent);
                break;
            case R.id.btnDateRepAnfang:
                checkBtn = 1;
                showDatePickerDialog();
                break;
            case R.id.btnDateRepEnde:
                checkBtn = 2;
                showDatePickerDialog();
                break;
            case R.id.btnSuchen:
                b = checkDate();
                if(b) {
                    getAufträgeDb();
                    if(!keineAufträge) {
                        klappen();
                    }
                }
                break;
            case R.id.btnKlappen:
                klappen();
                break;
        }
    }

    public boolean checkDate(){
        boolean b = true;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date dateVon = formatter.parse(mBtnRepAnfang.getText().toString());
            Date dateBis = formatter.parse(mBtnRepEnde.getText().toString());

            if(dateBis.compareTo(dateVon) < 0 ){
                mTvDateError.setTextColor(Color.RED);
                b = false;
            }else{
                mTvDateError.setTextColor(Color.WHITE);
            }
        } catch (ParseException e) {
            Log.e("except", e.getMessage());
        }
        return b;
    }

    public void klappen(){
        if(check.equals("offen")){
            slideUp(mklapView);
            mLv.setVisibility(ListView.INVISIBLE);
            check = "zu";
        }else{
            slideDown(mklapView);
            mLv.setVisibility(ListView.VISIBLE);
            check = "offen";
        }
    }

    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                view.getHeight(),                 // toXDelta
                0,                 // fromYDelta
                0); // toYDelta
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                view.getHeight(),                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            AufträgeSuchen aufträgeSuchen = (AufträgeSuchen) getActivity();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = format.format(calendar.getTime());

            aufträgeSuchen.date(strDate);
        }
    }

    public void date(String date){
        if(checkBtn==1) {
            mBtnRepAnfang.setText(date);
        }else if(checkBtn==2) {
            mBtnRepEnde.setText(date);
        }
    }

    Geocoder geocoder;
    List<Address> address;

    public int getDistance(String adresse1, String adresse2){
        // Hier Wert der bei der Suche nicht ausgewählt werden kann,
        // falls es zu einem Fehler bei der Abfrage kommt wird der Aufrag dann nicht angezeigt.
        int distance = 500;
        try {
            geocoder = new Geocoder(this);

            address = geocoder.getFromLocationName(adresse1, 1);
            Address adre = address.get(0);

            address = geocoder.getFromLocationName(adresse2, 1);
            Address adre1 = address.get(0);

            LatLng ladre = new LatLng(adre.getLatitude(), adre.getLongitude());
            LatLng ladre1 = new LatLng(adre1.getLatitude(), adre1.getLongitude());

            distance = (int) SphericalUtil.computeDistanceBetween(ladre, ladre1)/1000;
            Log.e("Distance", String.valueOf(distance));
        }catch(Exception ex){
            Log.e("Location", ex.getMessage());
            Log.e("Location", "Fail");
        }

        return distance;
    }

    ArrayList<Anfrage> anfrageList;
    ArrayList<byte[]> byteList = new ArrayList<byte[]>(){};
    ArrayList<Adresse> adresseList;

    public void extractAufträge(){
        int radius = mSeekbar.getProgress();
        Adresse adresse;
        ArrayList<Anfrage> anfrageList = new ArrayList<Anfrage>(){};
        ArrayList<byte[]> byteList = new ArrayList<byte[]>(){};
        this.adresseList = new ArrayList<Adresse>(){};

        Adresse adresseUser = getAdresse(getAdresseFk(new AktuellerBenutzer().getId(this)));
        int count = 0;
        Log.e("currentU", adresseUser.toString());

        for(Anfrage a : this.anfrageList){
            byte[] by = this.byteList.get(count);
            adresse = getAdresse(a.getmAdresseIdFk());
            if(getDistance(adresseUser.toString(), adresse.toString()) <= radius){
                Log.e("add", "list");
                anfrageList.add(a);
                byteList.add(by);
                this.adresseList.add(adresse);
            }
            count++;
        }

        if(anfrageList != null){
            this.anfrageList = anfrageList;
            this.byteList = byteList;
            setAufträge();
        }
    }

    public void setAufträge(){
        // Adapter setzen
        mLv.setAdapter(new AuftragAdapter(this, mLv, this.anfrageList, this.byteList, this.adresseList,
                mSpinKategorie.getSelectedItem().toString(), mBtnRepAnfang.getText().toString(),
                mBtnRepEnde.getText().toString(), String.valueOf(mSeekbar.getProgress())));
    }


    public String getAdresseFk(String id){
        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        String fk = null;
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_BENUTZERKONTO, // a. table
                            new String[]{SQLiteInit.COLUMN_USERNAME}, // b. column names
                            " benutzer_id_pk = ?", // c. selections
                            new String[] {id}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit


            cursor.moveToFirst();
            String username = cursor.getString(0);

            cursor =
                    db.query(SQLiteInit.TABLE_PRIVATPERSON, // a. table
                            new String[]{SQLiteInit.COLUMN_ADRESSE_ID_FK}, // b. column names
                            " email = ?", // c. selections
                            new String[] {username}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit
            if(cursor != null){
                cursor.moveToFirst();
                fk = cursor.getString(0);
            }else{
                cursor =
                        db.query(SQLiteInit.TABLE_FIRMA, // a. table
                                new String[]{SQLiteInit.COLUMN_ADRESSE_ID_FK}, // b. column names
                                " email = ?", // c. selections
                                new String[] {username}, // d. selections args
                                null, // e. group by
                                null, // f. having
                                null, // g. order by
                                null); // h. limit
                cursor.moveToFirst();
                fk = cursor.getString(0);
            }
        }catch(Exception ex){
        }
        db.close();
        return fk;
    }

    public Adresse getAdresse(String fk){
        Adresse adresse = new Adresse();
        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_ADRESSE, // a. table
                            new String[]{SQLiteInit.COLUMN_ORT,
                                    SQLiteInit.COLUMN_PLZ, SQLiteInit.COLUMN_LAND}, // b. column names
                            " adresse_id_pk = ?", // c. selections
                            new String[] {fk}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit

            if (cursor != null) {
                cursor.moveToFirst();
                adresse.setmOrt(cursor.getString(0));
                adresse.setmPlz(cursor.getString(1));
                adresse.setmLand(cursor.getString(2));
            }
        }catch(Exception ex){
        }
        db.close();
        return adresse;
    }

    public void getAufträgeDb(){

        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        Anfrage anfrage;
        this.anfrageList = new ArrayList<Anfrage>(){};
        String type = new AktuellerBenutzer().getTypeUser(this);
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_ANFRAGE, // a. table
                            new String[]{SQLiteInit.COLUMN_BESCHREIBUNG,
                                    SQLiteInit.COLUMN_ADRESSE_ID_FK, SQLiteInit.COLUMN_ANFRAGE_ID_PK,
                                    SQLiteInit.COLUMN_BILD}, // b. column names
                            type + " = ? and benutzer_id_fk != ? and kategorie_id_fk = ? and starttermin BETWEEN  ? and ? " +
                                    "and endtermin BETWEEN ? and ?", // c. selections
                            new String[] {"true", new AktuellerBenutzer().getId(this),
                                    getKategorie(mSpinKategorie.getSelectedItem().toString()),
                            mBtnRepAnfang.getText().toString(), mBtnRepEnde.getText().toString(),
                                    mBtnRepAnfang.getText().toString(), mBtnRepEnde.getText().toString()}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit
            if (cursor != null) {
                int count = cursor.getCount()-1;

                cursor.moveToFirst();
                while(count >= 0) {
                    anfrage = new Anfrage();

                    anfrage.setmBeschreibung(cursor.getString(0));
                    anfrage.setmAdresseIdFk(cursor.getString(1));
                    anfrage.setmId(cursor.getString(2));

                    this.anfrageList.add(anfrage);
                    this.byteList.add(cursor.getBlob(3));
                    count--;
                    if(count >= 0){
                        cursor.moveToNext();
                    }
                }
                if(this.anfrageList.size() != 0) {
                    Log.e("extrAuftr", "");
                    mTvKeineAufträge.setTextColor(Color.WHITE);
                    keineAufträge = false;
                    extractAufträge();
                }
            }
        }catch(Exception ex){
            keineAufträge = true;
            mTvKeineAufträge.setTextColor(Color.RED);
            ex.printStackTrace();
        }
        db.close();
    }

    public String getKategorie(String kategorie){
        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getReadableDatabase();
        String id = null;
        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_KATEGORIE, // a. table
                            new String[]{SQLiteInit.COLUMN_KATEGORIE_ID_PK}, // b. column names
                            " beschreibung = ?", // c. selections
                            new String[] {kategorie}, // d. selections args
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
        db.close();
        return id;
    }
}