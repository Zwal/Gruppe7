package de.repair.repairondemand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Profile extends AppCompatActivity implements View.OnClickListener {

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
