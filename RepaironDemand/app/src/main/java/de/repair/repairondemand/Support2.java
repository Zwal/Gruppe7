package de.repair.repairondemand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class Support2 extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnZurück, mBtnSenden;
    private Intent startActivityIntent;
    private String username;
    private ImageButton mUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support1);
        username = getIntent().getExtras().getString("username");
        bindViews();
        init();
    }

    private void bindViews() {
        mUpload = this.findViewById(R.id.btnUpload);
        mBtnZurück = this.findViewById(R.id.btnZurück);
        mBtnSenden = this.findViewById(R.id.btnSenden);
    }

    private void init() {
        mUpload.setOnClickListener(this);
        mBtnSenden.setOnClickListener(this);
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
            case R.id.btnSenden:
                startActivityIntent =  new Intent(this, Support2.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
            case R.id.btnUpload:
                startActivityIntent =  new Intent(this, Support2.class);
                startActivityIntent.putExtra("username", username);
                startActivity(startActivityIntent);
                break;
        }
    }
}