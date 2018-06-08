package de.repair.repairondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageButton;


public class FAQ extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnPasswort, mBtnAufträgeEinsehen, mBtnAuftragAbbrechen, mBtnSupportErreichen,
            mBtnAuftragnehmer, mBtnZahlung, mBtnKlappe;
    private ImageButton mBtnZurück;
    private TextView mTvAntwort;

    private Intent startActivityIntent;
    private String username;
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
        mBtnKlappe = this.findViewById(R.id.btnKlappe);
        mTvAntwort = this.findViewById(R.id.antwort);
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mBtnPasswort = this.findViewById(R.id.btnPasswort);
        mBtnAufträgeEinsehen = this.findViewById(R.id.btnAuftragEinsehen);
        mBtnAuftragAbbrechen = this.findViewById(R.id.btnAuftragAbbrechen);
        mBtnSupportErreichen = this.findViewById(R.id.btnSupportErreichen);
        mBtnAuftragnehmer = this.findViewById(R.id.btnAuftragnehmer);
        mBtnZahlung = this.findViewById(R.id.btnZahlung);
    }

    private void init() {
        mBtnZurück.setOnClickListener(this);
        mBtnPasswort.setOnClickListener(this);
        mBtnAufträgeEinsehen.setOnClickListener(this);
        mBtnAuftragAbbrechen.setOnClickListener(this);
        mBtnSupportErreichen.setOnClickListener(this);
        mBtnAuftragnehmer.setOnClickListener(this);
        mBtnZahlung.setOnClickListener(this);
        mBtnKlappe.setOnClickListener(this);
        mBtnKlappe.setVisibility(Button.INVISIBLE);
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
            case R.id.btnPasswort:
                klappen(getResources().getString(R.string.passwort_antwort));
                break;
            case R.id.btnAuftragEinsehen:
                klappen(getResources().getString(R.string.aufträgeEinsehen_antwort));
                break;
            case R.id.btnAuftragAbbrechen:
                klappen(getResources().getString(R.string.aufträgeAbbrechen_antwort));
                break;
            case R.id.btnSupportErreichen:
                klappen(getResources().getString(R.string.supportErreichen_antwort));
                break;
            case R.id.btnAuftragnehmer:
                klappen(getResources().getString(R.string.auftragnehmer_antwort));
                break;
            case R.id.btnZahlung:
                klappen(getResources().getString(R.string.zahlung_antwort));
                break;
        }
    }

    public void klappen(String text){
        if(check.equals("offen")){
            mTvAntwort.setVisibility(TextView.INVISIBLE);
            mBtnKlappe.setVisibility(Button.INVISIBLE);
            check = "zu";
        }else{
            mTvAntwort.setText(text);
            mTvAntwort.setVisibility(ListView.VISIBLE);
            mBtnKlappe.setVisibility(Button.VISIBLE);
            check = "offen";
        }
    }
}