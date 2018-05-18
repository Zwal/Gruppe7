package de.repair.repairondemand;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AngebotAbgeben extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnZurück, mBtnZeitStart, mBtnZeitEnde, mBtnSenden;

    private EditText mTxtPreis, mTxtBeschreibung;

    private TextView mTvHinweisPreis, mTvHinweisZeitraum;

    private Intent startActivityIntent;

    private int checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        init();
    }

    private void bindViews() {
        mBtnZurück = this.findViewById(R.id.zurück);
        mBtnZeitStart = this.findViewById(R.id.zeitraum_auswahl_start);
        mBtnZeitEnde = this.findViewById(R.id.zeitraum_auswahl_Ende);
        mBtnSenden = this.findViewById(R.id.btnSenden);

        mTxtPreis = this.findViewById(R.id.preis);
        mTxtBeschreibung = this.findViewById(R.id.beschreibung);

        mTvHinweisPreis = this.findViewById(R.id.hinweisPreis);
        mTvHinweisZeitraum = this.findViewById(R.id.hinweis_zeitraum);

        btnColor("white");
    }

    private void init() {
        mBtnZurück.setOnClickListener(this);
        mBtnZeitStart.setOnClickListener(this);
        mBtnZeitEnde.setOnClickListener(this);
        mBtnSenden.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btnZurück:
                finish();
                break;
            case R.id.zeitraum_auswahl_start:
                checkBtn = 1;
                showDatePickerDialog();
                break;
            case R.id.zeitraum_auswahl_Ende:
                checkBtn = 2;
                showDatePickerDialog();
                break;
            case R.id.btnSenden:
                check();
                break;
        }
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new AnfrageErstellen.DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            AngebotAbgeben angebotAbgeben = (AngebotAbgeben) getActivity();
            angebotAbgeben.date(year, month, day);
        }
    }

    public void date(int year, int month, int day){
        if(checkBtn==1) {
            mBtnZeitStart.setText(String.valueOf(day) + "." + String.valueOf(month) + "." +
                    String.valueOf(year));
        }else if(checkBtn==2) {
            mBtnZeitEnde.setText(String.valueOf(day) + "." + String.valueOf(month) + "." +
                    String.valueOf(year));
        }
    }

    public void check() {
        String preis = mTxtPreis.getText().toString();
        String dateStart = mBtnZeitStart.getText().toString();
        String dateEnde = mBtnZeitEnde.getText().toString();
        if(preis == null || dateStart.equals("Start") || dateEnde.equals("Ende") ){
            btnColor("red");
        }
    }

    public void btnColor(String color){
        if(color.equals("white")){
            mTvHinweisPreis.setTextColor(Color.WHITE);
            mTvHinweisZeitraum.setTextColor(Color.WHITE);
        }else if(color.equals("red")){
            mTvHinweisPreis.setTextColor(Color.RED);
            mTvHinweisZeitraum.setTextColor(Color.RED);
        }
    }
}
