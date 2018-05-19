package de.repair.repairondemand;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import de.repair.repairondemand.SQLlite.SQLite;
import de.repair.repairondemand.SQLlite.SQLiteInit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mBtnLogin;
    private TextView mTxtImpressum, mTxtPassVerg, mTxtRegister;
    private EditText mEdTxtEmail, mEdTxtPasswort;
    private CheckBox mCboRemember;

    private SQLite sqLite;

    private Intent startActivityIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        init();
    }

    private void bindViews() {
        mBtnLogin = this.findViewById(R.id.btnlogin);

        mTxtImpressum = this.findViewById(R.id.linkImpressum);
        mTxtPassVerg = this.findViewById(R.id.linkPasswortvergessen);
        mTxtRegister = this.findViewById(R.id.linkRegister);

        mEdTxtEmail = this.findViewById(R.id.emailInput);
        mEdTxtPasswort = this.findViewById(R.id.passwortInput);

        mCboRemember = this.findViewById(R.id.checkBoxRemember);
    }

    private void init() {
        mBtnLogin.setOnClickListener(this);
        mTxtImpressum.setOnClickListener(this);
        mTxtPassVerg.setOnClickListener(this);
        mTxtRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.btnlogin:
                initData();
                if(checkUser(mEdTxtEmail.getText().toString(), mEdTxtPasswort.getText().toString()) == 1) {
                    startActivityIntent = new Intent(this, Home.class);
                    startActivity(startActivityIntent);
                }
                break;
            case R.id.linkImpressum:
                startActivityIntent = new Intent(this, Impressum.class);
                startActivity(startActivityIntent);
                break;
            case R.id.linkPasswortvergessen:
                startActivityIntent = new Intent(this, NotAvailable.class);
                startActivity(startActivityIntent);
                break;
            case R.id.linkRegister:
                startActivityIntent = new Intent(this, NotAvailable.class);
                startActivity(startActivityIntent);
                break;
        }
    }

    public int checkUser(String email, String passwort){
        sqLite = new SQLite(this);
        // Gets the data repository in write mode
        SQLiteDatabase db = sqLite.getReadableDatabase();

        int register = 0;

        try{
            Cursor cursor =
                    db.query(SQLiteInit.TABLE_BENUTZERKONTO, // a. table
                            new String[]{SQLiteInit.COLUMN_BENUTZER_ID_PK}, // b. column names
                            " username = ? and passwort = ? ", // c. selections
                            new String[] {email, passwort}, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit

            if (cursor != null) {
                register = cursor.getColumnCount();
            }
        }catch(Exception ex){

        }

        return register;
    }

    public void initData(){
        writeKategorie();
        writeUser();
        mEdTxtEmail.setText("7@repair.de");
        mEdTxtPasswort.setText("123");
    }

    public void writeKategorie(){
        sqLite = new SQLite(this);
        String[] kategorien = new String[]{"Sanitär", "Dach"};
        SQLiteDatabase db = sqLite.getWritableDatabase();
        for(String kat : kategorien) {
            ContentValues values = new ContentValues();
            values.put(SQLiteInit.COLUMN_BESCHREIBUNG, kat);
            db.insert(SQLiteInit.TABLE_KATEGORIE, null, values);
        }
    }

    public void writeUser(){
        sqLite = new SQLite(this);
        SQLiteDatabase db = sqLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteInit.COLUMN_USERNAME, "7@repair.de");
        values.put(SQLiteInit.COLUMN_PASSWORT, "123");
        db.insert(SQLiteInit.TABLE_BENUTZERKONTO, null, values);

    }
}
