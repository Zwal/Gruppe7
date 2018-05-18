package de.repair.repairondemand;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.repair.repairondemand.SQLlite.Modells.Anfrage;
import de.repair.repairondemand.SQLlite.SQLite;
import de.repair.repairondemand.SQLlite.SQLiteInit;

public class AnfrageErstellen extends AppCompatActivity implements View.OnClickListener {

    private SQLite sqLite;

    private int checkBtn;

    public Button mBtnZurück, mBtnErstellen, mBtnKamera, mBtnUpload, mBtnRepAnfang, mBtnRepEnde
            , mBtnRepAblauf;
    private EditText mTxtBeschreibung, mTxtStraße, mTxtStadt, mTxtPlz, mTxtPreisvorstellung;
    private Spinner mSpinLand, mSpinKategorie;
    private CheckBox mCboFirma, mCboPrivat;
    private TextView mTvHinweisLandText, mTvHinweisRepzAnfangText, mTvHinweisRepzEndeText,
            mTvHinweisAblaufText, mTvHinweisKategorieText, mTvHinweisChecboxText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anfrage_erstellen);
        bindViews();
        init();
    }


    private void bindViews() {
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mBtnErstellen = this.findViewById(R.id.btnErstellen);
        mBtnKamera = this.findViewById(R.id.btnKamera);
        mBtnUpload = this.findViewById(R.id.btnUpload);
        mBtnRepAnfang = this.findViewById(R.id.btnDateRepAnfang);
        mBtnRepEnde = this.findViewById(R.id.btnDateRepEnde);
        mBtnRepAblauf = this.findViewById(R.id.btnAblaufdatum);

        mTxtBeschreibung = this.findViewById(R.id.beschreibung);
        mTxtStraße= this.findViewById(R.id.straße);
        mTxtStadt = this.findViewById(R.id.stadt);
        mTxtPlz = this.findViewById(R.id.plz);
        mTxtPreisvorstellung = this.findViewById(R.id.preisvorstellung);

        mSpinLand = this.findViewById(R.id.land);
        mSpinKategorie = this.findViewById(R.id.kategorie);

        mCboFirma = this.findViewById(R.id.checkBoxFirma);
        mCboPrivat = this.findViewById(R.id.checkBoxPrivat);

        mTvHinweisLandText = this.findViewById(R.id.hinweisLandText);
        mTvHinweisRepzAnfangText = this.findViewById(R.id.hinweisRepzAnfangText);
        mTvHinweisRepzEndeText = this.findViewById(R.id.hinweisRepzEndeText);
        mTvHinweisAblaufText = this.findViewById(R.id.hinweisAblaufdatumText);
        mTvHinweisKategorieText = this.findViewById(R.id.hinweisKategorieText);
        mTvHinweisChecboxText = this.findViewById(R.id.hinweisCheckboxText);

        btnColor("white");
    }

    private void init() {
        mBtnErstellen.setOnClickListener(this);
        mBtnRepAnfang.setOnClickListener(this);
        mBtnRepEnde.setOnClickListener(this);
        mBtnRepAblauf.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btnErstellen:
                check();
                break;
            case R.id.btnDateRepAnfang:
                checkBtn = 1;
                showDatePickerDialog();
                break;
            case R.id.btnDateRepEnde:
                checkBtn = 2;
                showDatePickerDialog();
                break;
            case R.id.btnAblaufdatum:
                checkBtn = 3;
                showDatePickerDialog();
                break;
        }
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
            AnfrageErstellen anfrageErstellen = (AnfrageErstellen) getActivity();
            anfrageErstellen.date(year, month, day);
        }
    }

    public void date(int year, int month, int day){
        if(checkBtn==1) {
            mBtnRepAnfang.setText(String.valueOf(day) + "." + String.valueOf(month) + "." +
                    String.valueOf(year));
        }else if(checkBtn==2) {
            mBtnRepEnde.setText(String.valueOf(day) + "." + String.valueOf(month) + "." +
                String.valueOf(year));
        }else if(checkBtn==3){
            mBtnRepAblauf.setText(String.valueOf(day) + "." + String.valueOf(month) + "." +
                    String.valueOf(year));
        }
    }

    public void btnColor(String color){
        if(color.equals("white")){
            mTvHinweisLandText.setTextColor(Color.WHITE);
            mTvHinweisRepzAnfangText.setTextColor(Color.WHITE);
            mTvHinweisRepzEndeText.setTextColor(Color.WHITE);
            mTvHinweisAblaufText.setTextColor(Color.WHITE);
            mTvHinweisKategorieText.setTextColor(Color.WHITE);
            mTvHinweisChecboxText.setTextColor(Color.WHITE);
        }else if(color.equals("red")){
            mTvHinweisLandText.setTextColor(Color.RED);
            mTvHinweisRepzAnfangText.setTextColor(Color.RED);
            mTvHinweisRepzEndeText.setTextColor(Color.RED);
            mTvHinweisAblaufText.setTextColor(Color.RED);
            mTvHinweisKategorieText.setTextColor(Color.RED);
            mTvHinweisChecboxText.setTextColor(Color.RED);
        }
    }

    public void check(){
        /*private String mBeschreibung;
        private String mStarttermin;
        private String mEndtermin;
        private String mAblaufdatum;
        private String mPreisvorstellung;
        private String mFirma;
        private String mPrivat;
        private String mKategorie;
        private Blob mBild;*/
        long adressId;
        String dateAnfang = mBtnRepAnfang.getText().toString();
        String dateEnde = mBtnRepEnde.getText().toString();
        String dateAblauf = mBtnRepAblauf.getText().toString();
        Cursor c =(Cursor) mSpinLand.getSelectedItem();
        String land = "bla"; //c.getString(c.getColumnIndex("titel"));
        c =(Cursor) mSpinKategorie.getSelectedItem();
        String kategorie = "la"; //c.getString(c.getColumnIndex("titel"));
        if(dateAnfang.equals("Anfang")||dateEnde.equals("Ende")||dateAblauf.equals("Ablauf")||
                land.equals("Land")||kategorie.equals("Kategorie")||
                !(mCboFirma.isChecked()||mCboPrivat.isChecked())){
            btnColor("red");
        }else {
            btnColor("white");
            Anfrage anfrage = new Anfrage();
            anfrage.setmBeschreibung(mTxtBeschreibung.getText().toString());
            anfrage.setmStarttermin(dateAnfang);
            anfrage.setmEndtermin(dateEnde);
            anfrage.setmAblaufdatum(dateAblauf);
            anfrage.setmPreisvorstellung(mTxtPreisvorstellung.getText().toString());
            anfrage.setmFirma(String.valueOf(mCboFirma.isChecked()));
            anfrage.setmPrivat(String.valueOf(mCboPrivat.isChecked()));
            anfrage.setmKategorie(kategorie);
            String strasse = mTxtStraße.getText().toString();
            String plz = mTxtPlz.getText().toString();
            String stadt = mTxtStadt.getText().toString();
            if(strasse != null) {
                adressId = writeDbAdresse(land, strasse, stadt, plz);
            }
        }
    }

    public long writeDbAdresse(String land, String straße, String stadt, String plz) {
        sqLite = new SQLite(this);
        // Gets the data repository in write mode
        SQLiteDatabase db = sqLite.getReadableDatabase();
        ///(\w*-*\w*-*\w*\s)([0-9])/g
        Pattern sPattern = Pattern.compile("(\\w*-*\\w*-*\\w*\\s)([0-9])");
        Matcher m = sPattern.matcher(straße);
        String hausnummerS = "0";
        if(m.find()) {
            Log.e("bla", m.group(0));
            hausnummerS = m.group(0);
        }
        int hausnummer = Integer.getInteger(hausnummerS);
        Toast.makeText(this, hausnummer, Toast.LENGTH_LONG).show();

        Cursor cursor =
                db.query(SQLiteInit.TABLE_ADRESSE, // a. table
                        new String[]{SQLiteInit.COLUMN_ADRESSE_ID_PK}, // b. column names
                        " strasse = ? and hausnummer = ? and plz = ? and land = ?", // c. selections
                        new String[] {straße, hausnummerS, plz, land}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
        long newRowId;
        if (cursor != null) {
            cursor.moveToFirst();
            newRowId = Long.getLong(cursor.getString(0));
        }else{
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(SQLiteInit.COLUMN_STRASSE, straße);
            values.put(SQLiteInit.COLUMN_HAUSNUMMER, hausnummer);
            values.put(SQLiteInit.COLUMN_PLZ, Integer.getInteger(plz));
            values.put(SQLiteInit.COLUMN_LAND, land);
            newRowId = db.insert(SQLiteInit.TABLE_ADRESSE, null, values);
        }

        return newRowId;
    }

    public void writeDb(Anfrage anfrage, int userId) {
        sqLite = new SQLite(this);
        // Gets the data repository in write mode
        SQLiteDatabase db = sqLite.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQLiteInit.COLUMN_BESCHREIBUNG, mTxtBeschreibung.getText().toString());
        values.put(SQLiteInit.COLUMN_STARTTERMIN, "start");
        values.put(SQLiteInit.COLUMN_ENDTERMIN, "end");
        values.put(SQLiteInit.COLUMN_ABLAUFDATUM, "ablauf");
        values.put(SQLiteInit.COLUMN_PREISVORSTELLUNG, "preisv");
        values.put(SQLiteInit.COLUMN_FIRMA, "firma");
        values.put(SQLiteInit.COLUMN_PRIVAT, "privat");
        values.put(SQLiteInit.COLUMN_KATEGORIE_ID_FK, 1);
        values.put(SQLiteInit.COLUMN_BILD, "");
        values.put(SQLiteInit.COLUMN_BENUTZER_ID_FK, 2);
        values.put(SQLiteInit.COLUMN_ADRESSE_ID_FK, 3);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SQLiteInit.TABLE_ANFRAGE, null, values);

        Toast.makeText(this, String.valueOf(newRowId), Toast.LENGTH_LONG).show();
    }

}
