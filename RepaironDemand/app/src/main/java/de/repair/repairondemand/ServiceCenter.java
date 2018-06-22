package de.repair.repairondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import de.repair.repairondemand.SQLlite.AktuellerBenutzer;


public class ServiceCenter extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnFAQ, mBtnSupport, mBtnBeschwerde, mBtnNutzungsbedingungen;
    private ImageButton mBtnZurück;
    private Spinner mSpinProfile;
    private String[] mSpinnerCont;
    private TextView mUsername;
    private ImageView mLogohome;
    private Intent startActivityIntent;
    private String username;
    ArrayAdapter<CharSequence> adapterSpinnerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicecenter);
        username = getIntent().getExtras().getString("username");
        bindViews();
        init();
    }

    private void bindViews() {
        mLogohome = this.findViewById(R.id.logohome);
        mUsername = this.findViewById(R.id.Benutzername);
        mBtnZurück = this.findViewById(R.id.btnZurück) ;
        mBtnFAQ = this.findViewById(R.id.btnFAQ);
        mBtnSupport = this.findViewById(R.id.btnSupport);
        mBtnBeschwerde = this.findViewById(R.id.btnBeschwerde);
        mBtnNutzungsbedingungen = this.findViewById(R.id.btnNutzungsbedingungen);
        mSpinProfile = this.findViewById(R.id.spinnerProfile);
        adapterSpinnerProfile = ArrayAdapter.createFromResource(this, R.array.spinnerProfile,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinProfile.setAdapter(adapterSpinnerProfile);
    }

    private void init() {
        mLogohome.setOnClickListener(this);
        mBtnFAQ.setOnClickListener(this);
        mBtnSupport.setOnClickListener(this);
        mBtnBeschwerde.setOnClickListener(this);
        mBtnNutzungsbedingungen.setOnClickListener(this);
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
                startActivityIntent =  new Intent(this, Home.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnSupport:
                startActivityIntent =  new Intent(this, Support1.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnBeschwerde:
                startActivityIntent =  new Intent(this, Beschwerde.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnNutzungsbedingungen:
                startActivityIntent =  new Intent(this, Nutzungsbedingungen.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnFAQ:
                startActivityIntent =  new Intent(this, FAQ.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
        }
    }

}