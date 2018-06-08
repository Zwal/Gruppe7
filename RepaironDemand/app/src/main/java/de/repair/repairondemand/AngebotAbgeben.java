package de.repair.repairondemand;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.repair.repairondemand.SQLlite.AktuellerBenutzer;
import de.repair.repairondemand.SQLlite.Modells.Angebot;

public class AngebotAbgeben extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnZeitStart, mBtnZeitEnde, mBtnSenden;
    private ImageButton mBtnZurück;

    private EditText mTxtPreis, mTxtBeschreibung;

    private TextView mTvHinweisPreis, mTvHinweisZeitraum, mUsername;

    private Intent startActivityIntent;

    private int checkBtn;

    private String anfrageId;

    private String kategorie, anfang, ende, radius, username;
    ArrayAdapter<CharSequence> adapterSpinnerProfile;
    private Spinner mSpinProfile;
    private String[] mSpinnerCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.angebot_abgeben);
        anfrageId = getIntent().getExtras().getString("anfrageId");
        username = getIntent().getExtras().getString("username");
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
        mUsername = this.findViewById(R.id.Benutzername);
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mBtnZeitStart = this.findViewById(R.id.zeitraum_auswahl_start);
        mBtnZeitEnde = this.findViewById(R.id.zeitraum_auswahl_Ende);
        mBtnSenden = this.findViewById(R.id.btnSenden);

        mTxtPreis = this.findViewById(R.id.preis);
        mTxtBeschreibung = this.findViewById(R.id.beschreibung);

        mTvHinweisPreis = this.findViewById(R.id.hinweisPreis);
        mTvHinweisZeitraum = this.findViewById(R.id.hinweis_zeitraum);
        mSpinProfile = this.findViewById(R.id.spinnerProfile);
        adapterSpinnerProfile = ArrayAdapter.createFromResource(this, R.array.spinnerProfile,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinProfile.setAdapter(adapterSpinnerProfile);

        btnColor("white");
    }

    private void init() {
        mBtnZurück.setOnClickListener(this);
        mBtnZeitStart.setOnClickListener(this);
        mBtnZeitEnde.setOnClickListener(this);
        mBtnSenden.setOnClickListener(this);
        mUsername.setText(username);
        mSpinnerCont = getResources().getStringArray(R.array.spinnerProfile);
        mSpinProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(mSpinnerCont[position].equals("Ausloggen")){
                    ausloggen();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

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
                startActivityIntent =  new Intent(this, AuftragAnsicht.class);
                startActivityIntent.putExtra("anfrageId", anfrageId);
                startActivityIntent.putExtra("kategorie", kategorie);
                startActivityIntent.putExtra("anfang", anfang);
                startActivityIntent.putExtra("ende", ende);
                startActivityIntent.putExtra("radius", radius);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.zeitraum_auswahl_start:
                checkBtn = 1;
                showDatePickerDialog();
                break;
            case R.id.zeitraum_auswahl_Ende:
                checkBtn = 2;
                showDatePickerDialog();
                break;
            case R.id.btnSenden:
                check();
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
            AngebotAbgeben angebotAbgeben = (AngebotAbgeben) getActivity();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = format.format(calendar.getTime());

            angebotAbgeben.date(strDate);
        }
    }

    public void date(String date){
        if(checkBtn==1) {
            mBtnZeitStart.setText(date);
        }else if(checkBtn==2) {
            mBtnZeitEnde.setText(date);
        }
    }

    public void check() {
        String preis = mTxtPreis.getText().toString();
        String dateStart = mBtnZeitStart.getText().toString();
        String dateEnde = mBtnZeitEnde.getText().toString();
        if(preis == null || dateStart.equals("Start") || dateEnde.equals("Ende") ){
            btnColor("red");
        }else{
            btnColor("white");
            FragmentManager fm = getSupportFragmentManager();
            AngebotAbgebenDialog alertDialog = AngebotAbgebenDialog.newInstance("Angebot abgeben",
                    writeAngebotDb(), username);
            alertDialog.show(fm, "fragment_alert");
        }
    }

    public void btnColor(String color){
        if(color.equals("white")){
            mTvHinweisPreis.setVisibility(ListView.INVISIBLE);
            mTvHinweisZeitraum.setVisibility(ListView.INVISIBLE);
        }else if(color.equals("red")){
            mTvHinweisPreis.setVisibility(ListView.VISIBLE);
            mTvHinweisZeitraum.setVisibility(ListView.VISIBLE);
        }
    }

    public Angebot writeAngebotDb(){
        Angebot angebot = new Angebot();
        angebot.setmPreis(mTxtPreis.getText().toString());
        Log.e("Preis",mTxtPreis.getText().toString() );
        angebot.setmZeitStart(mBtnZeitStart.getText().toString());
        angebot.setmZeitEnde(mBtnZeitEnde.getText().toString());
        angebot.setmBeschreibung(mTxtBeschreibung.getText().toString());
        angebot.setmAnfrageId(anfrageId);
        angebot.setmBenutzerId(new AktuellerBenutzer().getId(this));

        return angebot;
    }
}
