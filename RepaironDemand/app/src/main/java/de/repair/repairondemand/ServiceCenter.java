package de.repair.repairondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class ServiceCenter extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnFAQ, mBtnSupport, mBtnBeschwerde, mBtnNutzungsbedingungen, mBtnZurück;

    private Intent startActivityIntent;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicecenter);
        username = getIntent().getExtras().getString("username");
        bindViews();
        init();
    }

    private void bindViews() {
        mBtnZurück = this.findViewById(R.id.btnZurück) ;
        mBtnFAQ = this.findViewById(R.id.btnFAQ);
        mBtnSupport = this.findViewById(R.id.btnSupport);
        mBtnBeschwerde = this.findViewById(R.id.btnBeschwerde);
        mBtnNutzungsbedingungen = this.findViewById(R.id.btnNutzungsbedingungen);
    }

    private void init() {
        mBtnFAQ.setOnClickListener(this);
        mBtnSupport.setOnClickListener(this);
        mBtnBeschwerde.setOnClickListener(this);
        mBtnNutzungsbedingungen.setOnClickListener(this);
        mBtnZurück.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btnFAQ:
                startActivityIntent =  new Intent(this, FAQ.class);
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
        }
    }

}