package de.repair.repairondemand;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.Calendar;

import de.repair.repairondemand.SQLlite.Modells.Anfrage;
import de.repair.repairondemand.SQLlite.SQLite;
import de.repair.repairondemand.SQLlite.SQLiteInit;

public class AnfrageErstellen extends AppCompatActivity implements View.OnClickListener {

    private SQLite sqLite;

    private int checkBtn;
    private int checkBild;

    private Blob bild = null;

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
        ArrayAdapter<CharSequence> adapterLand = ArrayAdapter.createFromResource(this, R.array.land,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinLand.setAdapter(adapterLand);

        mSpinKategorie = this.findViewById(R.id.kategorie);
        ArrayAdapter<CharSequence> adapterKategorie = ArrayAdapter.createFromResource(this, R.array.category,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinKategorie.setAdapter(adapterKategorie);

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
        mBtnKamera.setOnClickListener(this);
        mBtnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btnZurück:
                finish();
                break;
            case R.id.btnErstellen:
                check(1);
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
            case R.id.btnKamera:
                checkBild = 1;
                bild();
                break;
            case R.id.btnUpload:
                checkBild =2;
                bild();
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

    public void check(int userId){
        String adressId = null;
        String dateAnfang = mBtnRepAnfang.getText().toString();
        String dateEnde = mBtnRepEnde.getText().toString();
        String dateAblauf = mBtnRepAblauf.getText().toString();
        String land = mSpinLand.getSelectedItem().toString();
        String kategorie = mSpinKategorie.getSelectedItem().toString();
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
            anfrage.setmKategorieIdFk(kategorie);
            String strasse = mTxtStraße.getText().toString();
            String plz = mTxtPlz.getText().toString();
            String stadt = mTxtStadt.getText().toString();
            adressId = writeDbAdresse(land, strasse, stadt, plz);

            anfrage.setmAdresseIdFk(adressId);
            anfrage.setmKategorieIdFk("1");
            anfrage.setmBild(bild);
            anfrage.setmUserId(userId);

            writeDb(anfrage);
        }
    }

    public String writeDbAdresse(String land, String straße, String stadt, String plz) {
        sqLite = new SQLite(this);
        // Gets the data repository in write mode
        SQLiteDatabase db = sqLite.getReadableDatabase();
        String newRowId = null;
        try{
            Log.e("cursor","start");
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_ADRESSE, // a. table
                            new String[]{SQLiteInit.COLUMN_ADRESSE_ID_PK}, // b. column names
                            " strasse_hausnummer = ? and ort = ? and plz = ? and land = ?", // c. selections
                            new String[] {straße, stadt, plz, land}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit

            if (cursor != null) {
                cursor.moveToFirst();
                newRowId = cursor.getString(0);
            }
        }catch(Exception ex){
                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(SQLiteInit.COLUMN_STRASSE_HAUSNUMMER, straße);
                values.put(SQLiteInit.COLUMN_PLZ, plz);
                values.put(SQLiteInit.COLUMN_ORT, stadt);
                values.put(SQLiteInit.COLUMN_LAND, land);
                newRowId = String.valueOf(db.insert(SQLiteInit.TABLE_ADRESSE, null, values));
        }
        return newRowId;
    }

    public void writeDb(Anfrage anfrage) {
        sqLite = new SQLite(this);
        // Gets the data repository in write mode
        SQLiteDatabase db = sqLite.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQLiteInit.COLUMN_BESCHREIBUNG, anfrage.getmBeschreibung());
        values.put(SQLiteInit.COLUMN_STARTTERMIN, anfrage.getmStarttermin());
        values.put(SQLiteInit.COLUMN_ENDTERMIN, anfrage.getmEndtermin());
        values.put(SQLiteInit.COLUMN_ABLAUFDATUM, anfrage.getmAblaufdatum());
        values.put(SQLiteInit.COLUMN_PREISVORSTELLUNG, anfrage.getmPreisvorstellung());
        values.put(SQLiteInit.COLUMN_FIRMA, anfrage.getmFirma());
        values.put(SQLiteInit.COLUMN_PRIVAT, anfrage.getmPrivat());
        values.put(SQLiteInit.COLUMN_KATEGORIE_ID_FK, anfrage.getmKategorieIdFk());
        if(anfrage.getmBild() != null){
            values.put(SQLiteInit.COLUMN_BILD, anfrage.getmBild().toString());
        }
        values.put(SQLiteInit.COLUMN_BENUTZER_ID_FK, anfrage.getmUserId());
        values.put(SQLiteInit.COLUMN_ADRESSE_ID_FK, anfrage.getmAdresseIdFk());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SQLiteInit.TABLE_ANFRAGE, null, values);

        Toast.makeText(this, String.valueOf(newRowId), Toast.LENGTH_LONG).show();
    }

    public void bild(){
        if(checkBild == 1){
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 2);
        }else if(checkBild == 2){
            Intent takePictureIntent = new Intent(MediaStore.Images.Media.DATA);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 3);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            // Blob thumb = getBytesFromBitmap(imageBitmap);
            // bild = thumb;
        }else if (requestCode == 3 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            // Blob thumb = (Blob) data.getExtras().get("data");
            // bild = thumb;
        }
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }
}
