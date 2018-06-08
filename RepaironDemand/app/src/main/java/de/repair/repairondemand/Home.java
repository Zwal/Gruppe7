package de.repair.repairondemand;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Home extends AppCompatActivity implements View.OnClickListener {


    private Button mBtnAnfrageerstellen, mBtnEingestellteAufträge,
    mBtnAufträgesuche, mBtnAbgegebeneAngebote, mBtnFeedback, mBtnKalender,
    mBtnBenachrichtigungen, mBtnServiceCenter;
    private Spinner mSpinProfile;
    private TextView mTxtImpressum, mUsername;

    private Intent startActivityIntent;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        username = getIntent().getExtras().getString("username");
        bindViews();
        init();
    }


    private void bindViews() {
        mUsername = this.findViewById(R.id.Benutzername);
        mBtnAnfrageerstellen = this.findViewById(R.id.btnAnfrageerstellen);
        mBtnEingestellteAufträge = this.findViewById(R.id.btnEingestellteAufträge);
        mBtnAufträgesuche = this.findViewById(R.id.btnAufträgesuchen);
        mBtnAbgegebeneAngebote = this.findViewById(R.id.btnAbgegebeneAngebote);
        mBtnFeedback= this.findViewById(R.id.btnFeedback);
        mBtnKalender = this.findViewById(R.id.btnKalender);
        mBtnBenachrichtigungen = this.findViewById(R.id.btnBenachrichtigungen);
        mBtnServiceCenter = this.findViewById(R.id.btnServiceCenter);
        mTxtImpressum = this.findViewById(R.id.linkImpressum);
        mSpinProfile = this.findViewById(R.id.spinnerProfile);
        ArrayAdapter<CharSequence> adapterSpinnerProfile = ArrayAdapter.createFromResource(this, R.array.spinnerProfile,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinProfile.setAdapter(adapterSpinnerProfile);
    }

    private void init() {
        mBtnAnfrageerstellen.setOnClickListener(this);
        mBtnEingestellteAufträge.setOnClickListener(this);
        mBtnAufträgesuche.setOnClickListener(this);
        mBtnAbgegebeneAngebote.setOnClickListener(this);
        mBtnFeedback.setOnClickListener(this);
        mBtnKalender.setOnClickListener(this);
        mBtnBenachrichtigungen.setOnClickListener(this);
        mBtnServiceCenter.setOnClickListener(this);
        mTxtImpressum.setOnClickListener(this);
        mUsername.setText(username);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btnAnfrageerstellen:
                startActivityIntent =  new Intent(this, AnfrageErstellen.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnEingestellteAufträge:
                startActivityIntent =  new Intent(this, NotAvailable.class);
                startActivity(startActivityIntent);
                break;
            case R.id.btnAufträgesuchen:
                startActivityIntent =  new Intent(this, AufträgeSuchen.class);
                startActivity(startActivityIntent);
                break;
            case R.id.btnAbgegebeneAngebote:
                startActivityIntent =  new Intent(this, NotAvailable.class);
                startActivity(startActivityIntent);
                break;
            case R.id.btnFeedback:
                startActivityIntent =  new Intent(this, NotAvailable.class);
                startActivity(startActivityIntent);
                break;
            case R.id.btnKalender:
                startActivityIntent =  new Intent(this, NotAvailable.class);
                startActivity(startActivityIntent);
                break;
            case R.id.btnBenachrichtigungen:
                startActivityIntent =  new Intent(this, NotAvailable.class);
                startActivity(startActivityIntent);
                break;
            case R.id.btnServiceCenter:
                startActivityIntent =  new Intent(this, NotAvailable.class);
                startActivity(startActivityIntent);
                break;
            case R.id.linkImpressum:
                startActivityIntent =  new Intent(this, Impressum.class);
                startActivity(startActivityIntent);
                break;
        }
    }

}
