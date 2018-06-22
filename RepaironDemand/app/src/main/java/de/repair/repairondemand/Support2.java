package de.repair.repairondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import de.repair.repairondemand.SQLlite.AktuellerBenutzer;


public class Support2 extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnSenden;
    private Spinner mSpinProfile;
    private String[] mSpinnerCont;
    private TextView mUsername, mTvMeldung;
    private EditText mText;
    private ImageView mLogohome;
    private Intent startActivityIntent;
    private String username;
    ArrayAdapter<CharSequence> adapterSpinnerProfile;
    private ImageButton mBtnZurück;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_b);
        username = getIntent().getExtras().getString("username");
        bindViews();
        init();
    }

    private void bindViews() {
        mLogohome = this.findViewById(R.id.logohome);
        mText = this.findViewById(R.id.text);
        mUsername = this.findViewById(R.id.Benutzername);
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mBtnSenden = this.findViewById(R.id.btnSenden);
        mTvMeldung = this.findViewById(R.id.meldung);
        mSpinProfile = this.findViewById(R.id.spinnerProfile);
        adapterSpinnerProfile = ArrayAdapter.createFromResource(this, R.array.spinnerProfile,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinProfile.setAdapter(adapterSpinnerProfile);
    }

    private void init() {
        mLogohome.setOnClickListener(this);
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
                startActivityIntent =  new Intent(this, Support1.class);
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
        }
    }

    // hier wird überprüft ob die erforderlichen Felder ausgefüllt wurden, wenn
    // nein, werden Hinweise angezeigt
    public boolean check(){
        boolean b = false;
        if(!mText.getText().toString().equals("")){
            mTvMeldung.setVisibility(TextView.INVISIBLE);
            b = true;
        }else{
            mTvMeldung.setVisibility(TextView.VISIBLE);
        }
        return b;
    }
}