package de.repair.repairondemand;



import android.content.Intent;

import android.media.Image;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import de.repair.repairondemand.SQLlite.AktuellerBenutzer;


public class Support1 extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnSupport2;
    private ImageButton mBtnZurück;
    private Spinner mSpinProfile;
    private String[] mSpinnerCont;
    private TextView mUsername;

    private Intent startActivityIntent;
    private String username;
    ArrayAdapter<CharSequence> adapterSpinnerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_a);
        username = getIntent().getExtras().getString("username");
        bindViews();
        init();
    }

    private void bindViews() {
        mUsername = this.findViewById(R.id.Benutzername);
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mBtnSupport2 = this.findViewById(R.id.btnSupport2);
        mSpinProfile = this.findViewById(R.id.spinnerProfile);
        adapterSpinnerProfile = ArrayAdapter.createFromResource(this, R.array.spinnerProfile,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinProfile.setAdapter(adapterSpinnerProfile);
    }

    private void init() {
        mBtnSupport2.setOnClickListener(this);
        mBtnZurück.setOnClickListener(this);
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
            case R.id.btnSupport2:
                startActivityIntent =  new Intent(this, Support2.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
        }
    }
}