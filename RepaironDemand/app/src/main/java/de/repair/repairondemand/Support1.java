package de.repair.repairondemand;



import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;





public class Support1 extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnZurück, mBtnSupport2;
    private Intent startActivityIntent;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support1);
        username = getIntent().getExtras().getString("username");
        bindViews();
        init();
    }

    private void bindViews() {
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mBtnSupport2 = this.findViewById(R.id.btnSupport2);
    }

    private void init() {
        mBtnSupport2.setOnClickListener(this);
        mBtnZurück.setOnClickListener(this);
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
            case R.id.btnSupport:
                startActivityIntent =  new Intent(this, Support2.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
        }
    }
}