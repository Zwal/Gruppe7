package de.repair.repairondemand;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
//import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AufträgeSuchen extends AppCompatActivity implements View.OnClickListener {

    private int checkBtn;
    public Button mBtnZurück, mBtnRepAnfang, mBtnRepEnde, mBtnSuchen;
    private Spinner mSpinKategorie;
    private SeekBar mSeekbar;
    private TextView mTvRadius;
    // private ListView mListResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anfrage_suche);
        bindViews();
        init();
    }

    private void bindViews() {
        mBtnZurück = this.findViewById(R.id.zurück);
        mBtnRepAnfang = this.findViewById(R.id.btnDateRepAnfang);
        mBtnRepEnde = this.findViewById(R.id.btnDateRepEnde);
        mSpinKategorie = this.findViewById(R.id.kategorie);
        mBtnSuchen = this.findViewById(R.id.btnSuchen);
        mTvRadius = this.findViewById(R.id.txtRadius);
        ArrayAdapter<CharSequence> adapterKategorie = ArrayAdapter.createFromResource(this, R.array.category,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinKategorie.setAdapter(adapterKategorie);
        mSeekbar = this.findViewById(R.id.seekBar);
        // mListResult = this.findViewById(R.id.listView);
    }

    private void init() {
        mBtnZurück.setOnClickListener(this);
        mBtnRepAnfang.setOnClickListener(this);
        mBtnRepEnde.setOnClickListener(this);
        mBtnSuchen.setOnClickListener(this);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Write code to perform some action when progress is changed.
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is started.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is stopped.
                mTvRadius.setText(String.valueOf(seekBar.getProgress()) + " km");
            }
        });
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.zurück:
                finish();
                break;
            case R.id.btnDateRepAnfang:
                checkBtn = 1;
                showDatePickerDialog();
                break;
            case R.id.btnDateRepEnde:
                checkBtn = 2;
                showDatePickerDialog();
                break;
            case R.id.btnSuchen:
                // getAufträgeDb();
                break;
        }
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
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
            AufträgeSuchen aufträgeSuchen = (AufträgeSuchen) getActivity();
            aufträgeSuchen.date(year, month, day);
        }
    }

    public void date(int year, int month, int day){
        if(checkBtn==1) {
            mBtnRepAnfang.setText(String.valueOf(day) + "." + String.valueOf(month) + "." +
                    String.valueOf(year));
        }else if(checkBtn==2) {
            mBtnRepEnde.setText(String.valueOf(day) + "." + String.valueOf(month) + "." +
                    String.valueOf(year));
        }
    }
}
