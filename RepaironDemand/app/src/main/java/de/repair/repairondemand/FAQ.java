package de.repair.repairondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageButton;

import de.repair.repairondemand.SQLlite.AktuellerBenutzer;


public class FAQ extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnPasswort, mBtnAufträgeEinsehen, mBtnAuftragAbbrechen, mBtnSupportErreichen,
            mBtnAuftragnehmer, mBtnZahlung, mBtnKlappe;
    private ImageButton mBtnZurück;
    private ImageView mLogohome;
    private Spinner mSpinProfile;
    private String[] mSpinnerCont;
    private TextView mTvAntwort, mUsername;
    private View mklapView;

    private Intent startActivityIntent;
    private String username;
    ArrayAdapter<CharSequence> adapterSpinnerProfile;
    private String check = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);
        username = getIntent().getExtras().getString("username");
        bindViews();
        init();
    }

    private void bindViews() {
        mklapView = this.findViewById(R.id.constraintLayout2);
        mUsername = this.findViewById(R.id.Benutzername);
        mBtnKlappe = this.findViewById(R.id.btnKlappe);
        mTvAntwort = this.findViewById(R.id.antwort);
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mLogohome = this.findViewById(R.id.logohome);
        mBtnPasswort = this.findViewById(R.id.btnPasswort);
        mBtnAufträgeEinsehen = this.findViewById(R.id.btnAuftragEinsehen);
        mBtnAuftragAbbrechen = this.findViewById(R.id.btnAuftragAbbrechen);
        mBtnSupportErreichen = this.findViewById(R.id.btnSupportErreichen);
        mBtnAuftragnehmer = this.findViewById(R.id.btnAuftragnehmer);
        mBtnZahlung = this.findViewById(R.id.btnZahlung);
        check = "zu";
        mSpinProfile = this.findViewById(R.id.spinnerProfile);
        adapterSpinnerProfile = ArrayAdapter.createFromResource(this, R.array.spinnerProfile,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinProfile.setAdapter(adapterSpinnerProfile);
    }

    private void init() {
        mBtnZurück.setOnClickListener(this);
        mLogohome.setOnClickListener(this);
        mBtnPasswort.setOnClickListener(this);
        mBtnAufträgeEinsehen.setOnClickListener(this);
        mBtnAuftragAbbrechen.setOnClickListener(this);
        mBtnSupportErreichen.setOnClickListener(this);
        mBtnAuftragnehmer.setOnClickListener(this);
        mBtnZahlung.setOnClickListener(this);
        mBtnKlappe.setOnClickListener(this);
        mBtnKlappe.setVisibility(Button.INVISIBLE);
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
                startActivityIntent =  new Intent(this, ServiceCenter.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.logohome:
                startActivityIntent =  new Intent(this, Home.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnAuftragEinsehen:
                klappen(getResources().getString(R.string.aufträgeEinsehen_frage) + "\n\n" +
                        getResources().getString(R.string.aufträgeEinsehen_antwort));
                break;
            case R.id.btnAuftragAbbrechen:
                klappen(getResources().getString(R.string.aufträgeAbbrechen_frage) + "\n\n"+
                        getResources().getString(R.string.aufträgeAbbrechen_antwort));
                break;
            case R.id.btnSupportErreichen:
                klappen(getResources().getString(R.string.supportErreichen_frage)+ "\n\n"+
                        getResources().getString(R.string.supportErreichen_antwort));
                break;
            case R.id.btnAuftragnehmer:
                klappen(getResources().getString(R.string.auftragnehmer_frage) + "\n\n"+
                        getResources().getString(R.string.auftragnehmer_antwort));
                break;
            case R.id.btnZahlung:
                klappen(getResources().getString(R.string.zahlung_frage) + "\n\n"+
                        getResources().getString(R.string.zahlung_antwort));
                break;
            case R.id.btnPasswort:
                klappen(getResources().getString(R.string.passwort_frage) + "\n\n"+
                        getString(R.string.passwort_antwort));
                break;
            case R.id.btnKlappe:
                klappen(null);
                break;
        }
    }

    public void klappen(String text){
        if(check.equals("offen")){
            slideUp(mklapView);
            mTvAntwort.setVisibility(TextView.INVISIBLE);
            mBtnKlappe.setVisibility(Button.INVISIBLE);
            check = "zu";
        }else{
            slideDown(mklapView);
            mTvAntwort.setText(text);
            mTvAntwort.setVisibility(ListView.VISIBLE);
            mBtnKlappe.setVisibility(Button.VISIBLE);
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
}