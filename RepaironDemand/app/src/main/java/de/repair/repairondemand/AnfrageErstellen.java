package de.repair.repairondemand;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.repair.repairondemand.SQLlite.AktuellerBenutzer;
import de.repair.repairondemand.SQLlite.Modells.Anfrage;
import de.repair.repairondemand.SQLlite.SQLite;
import de.repair.repairondemand.SQLlite.SQLiteInit;
import pub.devrel.easypermissions.EasyPermissions;

public class AnfrageErstellen extends AppCompatActivity implements View.OnClickListener {

    private SQLite sqLite;
    private Bitmap imageBitmap;
    private int checkBtn;
    private int checkBild;
    private Intent startActivityIntent;
    private String username;
    private String[] mSpinnerCont;

    private Blob bild = null;
    public Button  mBtnErstellen, mBtnRepAnfang, mBtnRepEnde
            , mBtnRepAblauf;
    public ImageButton mBtnZurück, mBtnKamera, mBtnUpload;
    private EditText mTxtBeschreibung, mTxtStraße, mTxtStadt, mTxtPlz, mTxtPreisvorstellung;
    private Spinner mSpinLand, mSpinKategorie;
    private CheckBox mCboFirma, mCboPrivat;
    private TextView mTvHinweisLandText, mTvHinweisStrasseText, mTvHinweisStadtText, mTvHinweisPlzText,
            mTvHinweisRepzAnfangText, mTvHinweisRepzEndeText, mTvHinweisAblaufText,
            mTvHinweisKategorieText, mTvHinweisChecboxText, mUsername;
    ArrayAdapter<CharSequence> adapterSpinnerProfile;
    private Spinner mSpinProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anfrage_erstellen);
        username = getIntent().getExtras().getString("username");
        bindViews();
        init();
    }

    private void bindViews() {
        mUsername = this.findViewById(R.id.Benutzername);
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
        mTvHinweisStrasseText = this.findViewById(R.id.hinweisStrasseText);
        mTvHinweisStadtText = this.findViewById(R.id.hinweisStadtText);
        mTvHinweisPlzText = this.findViewById(R.id.hinweisPlzText);
        mSpinProfile = this.findViewById(R.id.spinnerProfile);
        adapterSpinnerProfile = ArrayAdapter.createFromResource(this, R.array.spinnerProfile,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinProfile.setAdapter(adapterSpinnerProfile);

        btnColor("white");
    }

    private void init() {
        mBtnZurück.setOnClickListener(this);
        mBtnErstellen.setOnClickListener(this);
        mBtnRepAnfang.setOnClickListener(this);
        mBtnRepEnde.setOnClickListener(this);
        mBtnRepAblauf.setOnClickListener(this);
        mBtnZurück.setOnClickListener(this);
        mBtnUpload.setOnClickListener(this);
        mBtnKamera.setOnClickListener(this);
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
    }
    // aktueller Benutzer wird gelöscht und Main Activity wird gestartet
    public void ausloggen(){
        new AktuellerBenutzer().deleteAktuellerUser(this);
        startActivityIntent =  new Intent(this, MainActivity.class);
        startActivity(startActivityIntent);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btnZurück:
                startActivityIntent = new Intent(this, Home.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnErstellen:
                if(check(new AktuellerBenutzer().getId(this))) {
                    startActivityIntent = new Intent(this, Home.class);
                    startActivityIntent.putExtra("username", username);
                    startActivity(startActivityIntent);
                }
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
                checkBild = 2;
                bild();
                break;
        }
    }


    // Hier wird ein Kalender geöffnet in dem man das Datum auswählen kann
    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // aktuelles Datum als Voreinstellung
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //Rückgabe einer neuen Instanz
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            AnfrageErstellen anfrageErstellen = (AnfrageErstellen) getActivity();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = format.format(calendar.getTime());

            anfrageErstellen.date(strDate);
        }
    }

    // hier wird das Datum gesetzt
    public void date(String date){
        if(checkBtn==1) {
            mBtnRepAnfang.setText(date);
        }else if(checkBtn==2) {
            mBtnRepEnde.setText(date);
        }else if(checkBtn==3){
            mBtnRepAblauf.setText(date);
        }
    }

    // hier werden die Hinweise sichtbar/unsichtbar gesetzt
    public void btnColor(String color){
        if(color.equals("white")){
            mTvHinweisLandText.setVisibility(ListView.INVISIBLE);
            mTvHinweisRepzAnfangText.setVisibility(ListView.INVISIBLE);
            mTvHinweisRepzEndeText.setVisibility(ListView.INVISIBLE);
            mTvHinweisAblaufText.setVisibility(ListView.INVISIBLE);
            mTvHinweisKategorieText.setVisibility(ListView.INVISIBLE);
            mTvHinweisChecboxText.setVisibility(ListView.INVISIBLE);
            mTvHinweisStrasseText.setVisibility(ListView.INVISIBLE);
            mTvHinweisStadtText.setVisibility(ListView.INVISIBLE);
            mTvHinweisPlzText.setVisibility(ListView.INVISIBLE);
        }else if(color.equals("red")){
            mTvHinweisLandText.setVisibility(ListView.VISIBLE);
            mTvHinweisRepzAnfangText.setVisibility(ListView.VISIBLE);
            mTvHinweisRepzEndeText.setVisibility(ListView.VISIBLE);
            mTvHinweisAblaufText.setVisibility(ListView.VISIBLE);
            mTvHinweisKategorieText.setVisibility(ListView.VISIBLE);
            mTvHinweisChecboxText.setVisibility(ListView.VISIBLE);
            mTvHinweisStrasseText.setVisibility(ListView.VISIBLE);
            mTvHinweisStadtText.setVisibility(ListView.VISIBLE);
            mTvHinweisPlzText.setVisibility(ListView.VISIBLE);
        }
    }

    // es wird überprüft ob alle Felder ausgefüllt sind, wenn ja wird die Anfrage in die Datenbank geschrieben
    public boolean check(String userId){
        String adressId = null;
        String dateAnfang = mBtnRepAnfang.getText().toString();
        String dateEnde = mBtnRepEnde.getText().toString();
        String dateAblauf = mBtnRepAblauf.getText().toString();
        String land = mSpinLand.getSelectedItem().toString();
        String kategorie = mSpinKategorie.getSelectedItem().toString();
        String strasse = mTxtStraße.getText().toString();
        String plz = mTxtPlz.getText().toString();
        String stadt = mTxtStadt.getText().toString();
        Log.e("privat", String.valueOf(mCboPrivat.isChecked()));
        if(strasse.equals("")|| plz.equals("") || stadt.equals("")||dateAnfang.equals("Anfang")
                ||dateEnde.equals("Ende")||dateAblauf.equals("Ablauf")||
                land.equals("Land")||kategorie.equals("Kategorie")||!(mCboFirma.isChecked()||mCboPrivat.isChecked())){
            btnColor("red");
            return false;
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
            anfrage.setmKategorieIdFk(getKategorie(kategorie));
            adressId = writeDbAdresse(land, strasse, stadt, plz);
            anfrage.setmAdresseIdFk(adressId);
            anfrage.setmUserId(Integer.parseInt(userId));

            writeDb(anfrage);

            return true;

        }
    }

    // hier wird die Adresse in die Datenbank geschrieben
    public String writeDbAdresse(String land, String straße, String stadt, String plz) {
        sqLite = new SQLite(this);
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
                // Map wird mit Variablen erzeugt, wo Spaltennamen die PKs sind
                ContentValues values = new ContentValues();
                values.put(SQLiteInit.COLUMN_STRASSE_HAUSNUMMER, straße);
                values.put(SQLiteInit.COLUMN_PLZ, plz);
                values.put(SQLiteInit.COLUMN_ORT, stadt);
                values.put(SQLiteInit.COLUMN_LAND, land);
                newRowId = String.valueOf(db.insert(SQLiteInit.TABLE_ADRESSE, null, values));
        }
        return newRowId;
    }

    // hier wird die Anfrage in die Datenbank geschrieben
    public void writeDb(Anfrage anfrage) {
        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getWritableDatabase();

        // Map wird mit Variablen erzeugt, wo Spaltennamen die PKs sind
        ContentValues values = new ContentValues();
        values.put(SQLiteInit.COLUMN_BESCHREIBUNG, anfrage.getmBeschreibung());
        values.put(SQLiteInit.COLUMN_STARTTERMIN, anfrage.getmStarttermin());
        values.put(SQLiteInit.COLUMN_ENDTERMIN, anfrage.getmEndtermin());
        values.put(SQLiteInit.COLUMN_ABLAUFDATUM, anfrage.getmAblaufdatum());
        values.put(SQLiteInit.COLUMN_PREISVORSTELLUNG, anfrage.getmPreisvorstellung());
        values.put(SQLiteInit.COLUMN_FIRMA, anfrage.getmFirma());
        values.put(SQLiteInit.COLUMN_PRIVAT, anfrage.getmPrivat());
        values.put(SQLiteInit.COLUMN_KATEGORIE_ID_FK, anfrage.getmKategorieIdFk());
        values.put(SQLiteInit.COLUMN_BILD, getBytesFromBitmap(imageBitmap));
        values.put(SQLiteInit.COLUMN_BENUTZER_ID_FK, anfrage.getmUserId());
        values.put(SQLiteInit.COLUMN_ADRESSE_ID_FK, anfrage.getmAdresseIdFk());

        // neue Zeile wird eingefügt, PK der neuen Zeile wird zurückgegeben
        long newRowId = db.insert(SQLiteInit.TABLE_ANFRAGE, null, values);

        Toast.makeText(this, "Auftrag erstellt.", Toast.LENGTH_LONG).show();
    }


    // hier erfolgt der Kamera Zugriff um ein Bild der Anfrage hinzuzufügen
    public void bild(){
        if(checkBild == 1){
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 2);
        }else if(checkBild == 2){
            if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
            } else {
                EasyPermissions.requestPermissions(this, "Access for storage",
                        101, galleryPermissions);
            }
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        }
    }
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
        }else if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
            pickImage(data);
        }
    }

    // hier erfolgt der Zugriff auf die Medien, aus denen man sich ein Bild für die Anfrage holen kann
    public void pickImage(Intent data){
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        imageBitmap = BitmapFactory.decodeFile(picturePath);
    }

    // hier wird eine bitmap Datei in ein byte[] umgewandelt
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }

    // hier wird die Kategorie zu der Auswahl abgefragt
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
        Log.e("id", id);
        return id;
    }
}
