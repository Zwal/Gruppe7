package de.repair.repairondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Impressum extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mBtnZurück;
    private String username;
    private Intent startActivityIntent;
    private int mMain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.impressum);
        if(getIntent().hasExtra("username")) {
            username = getIntent().getExtras().getString("username");
        }
        if(getIntent().hasExtra("main")) {
            username = getIntent().getExtras().getString("main");
            mMain = 1;
        }
        bindViews();
        init();
    }

    private void bindViews() {
        mBtnZurück = this.findViewById(R.id.btnZurück);
    }

    private void init() {
        mBtnZurück.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btnZurück:
                if(mMain == 1){
                    finish();
                }else {
                    startActivityIntent = new Intent(this, Home.class);
                    startActivityIntent.putExtra("username", username);
                    startActivity(startActivityIntent);
                }
                break;
        }
    }
}
