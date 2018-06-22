package de.repair.repairondemand;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.repair.repairondemand.SQLlite.AktuellerBenutzer;


public class Beschwerde extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mBtnZurück;
    private Button mBtnSenden, mBtnDatum;
    private Spinner mSpinProfile;
    private String[] mSpinnerCont;
    private TextView mUsername, mTvMeldung, mTvMeldungDate, mTvMeldungAuftrNr;
    private EditText mText,mTextAuftrNr;
    private ImageView mLogohome;

    private Intent startActivityIntent;
    private String username;
    ArrayAdapter<CharSequence> adapterSpinnerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beschwerde);
        username = getIntent().getExtras().getString("username");
        bindViews();
        init();
    }

    private void bindViews() {
        mLogohome = this.findViewById(R.id.logohome);
        mText = this.findViewById(R.id.text);
        mTextAuftrNr = this.findViewById(R.id.textAuftrNr);
        mUsername = this.findViewById(R.id.Benutzername);
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mBtnSenden = this.findViewById(R.id.btnSenden);
        mBtnDatum = this.findViewById(R.id.btnDate);
        mTvMeldung = this.findViewById(R.id.meldung);
        mTvMeldungDate = this.findViewById(R.id.meldungDate);
        mTvMeldungAuftrNr = this.findViewById(R.id.meldungAuftrNr);
        mSpinProfile = this.findViewById(R.id.spinnerProfile);
        adapterSpinnerProfile = ArrayAdapter.createFromResource(this, R.array.spinnerProfile,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinProfile.setAdapter(adapterSpinnerProfile);
    }

    private void init() {
        mLogohome.setOnClickListener(this);
        mBtnDatum.setOnClickListener(this);
        mBtnSenden.setOnClickListener(this);
        mBtnZurück.setOnClickListener(this);
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
                startActivityIntent =  new Intent(this, ServiceCenter.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnSenden:
                if(check()) {
                    Toast.makeText(this, "Abgeschickt.", Toast.LENGTH_LONG).show();
                    startActivityIntent = new Intent(this, Home.class);
                    startActivityIntent.putExtra("username", username);
                    startActivity(startActivityIntent);
                }
                break;
            case R.id.btnDate:
                showDatePickerDialog();
                break;
        }
    }
    // Hier wird ein Kalender geöffnet in dem man das Datum auswählen kann
    public void showDatePickerDialog() {
        DialogFragment newFragment = new Beschwerde.DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        // Kalender wird angelegt
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Beschwerde beschwerde = (Beschwerde) getActivity();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = format.format(calendar.getTime());

            beschwerde.date(strDate);
        }
    }

    // hier wird das Datum gesetzt
    public void date(String date){
        mBtnDatum.setText(date);
    }

    // hier wird überprüft ob alle erforderlichen Felder ausgfüllt wurden, wenn nein werden Hinweise angezeigt
    public boolean check(){
        boolean b = false;
        if(!mText.getText().toString().equals("") && !mTextAuftrNr.getText().toString().equals("")
        && !mBtnDatum.getText().toString().equals("Datum")){
            mTvMeldung.setVisibility(TextView.INVISIBLE);
            mTvMeldungAuftrNr.setVisibility(TextView.INVISIBLE);
            mTvMeldungDate.setVisibility(TextView.INVISIBLE);
            b = true;
        }else{
            mTvMeldung.setVisibility(TextView.VISIBLE);
            mTvMeldungAuftrNr.setVisibility(TextView.VISIBLE);
            mTvMeldungDate.setVisibility(TextView.VISIBLE);
        }
        return b;
    }
}