package de.repair.repairondemand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class NotAvailable extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mBtnZurück;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notavailable);
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
                finish();
                break;
        }
    }
}
