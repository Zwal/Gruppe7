package de.repair.repairondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AuftragAnsicht extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnZurück, mBtnProfil, mBtnAngebotAbgeben;

    private ImageView mImViAuftragBild;

    private TextView mTvBeschreibung, mTvStandort;

    private Intent startActivityIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auftrag_ansicht);
        bindViews();
        init();
    }


    private void bindViews() {
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mBtnProfil = this.findViewById(R.id.btnProfilansehen);
        mBtnAngebotAbgeben = this.findViewById(R.id.btnAngebotabgeben);
        mImViAuftragBild = this.findViewById(R.id.auftrag_bild);
        mTvBeschreibung = this.findViewById(R.id.beschreibung);
        mTvStandort = this.findViewById(R.id.standort);
    }

    private void init() {
        mBtnZurück.setOnClickListener(this);
        mBtnProfil.setOnClickListener(this);
        mBtnAngebotAbgeben.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btnZurück:
                finish();
                break;
            case R.id.btnAngebotabgeben:
                startActivityIntent = new Intent(this, AngebotAbgeben.class);
                startActivity(startActivityIntent);
                break;
            case R.id.btnProfilansehen:
                startActivityIntent = new Intent(this, Profile.class);
                startActivity(startActivityIntent);
                break;
        }
    }
}
