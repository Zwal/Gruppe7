package de.repair.repairondemand.SQLlite.Modells;

import java.sql.Blob;

public class Anfrage {

    private String mBeschreibung;
    private String mStarttermin;
    private String mEndtermin;
    private String mAblaufdatum;
    private String mPreisvorstellung;
    private String mFirma;
    private String mPrivat;
    private String mKategorie;
    private Blob mBild;

    public Anfrage(){}

    public Anfrage(String mBeschreibung, String mStarttermin, String mEndtermin,
                   String mAblaufdatum, String mPreisvorstellung, String mFirma, String mPrivat,
                   String mKategorie, Blob mBild){
        this.mBeschreibung = mBeschreibung;
        this.mStarttermin = mStarttermin;
        this.mEndtermin = mEndtermin;
        this.mAblaufdatum = mAblaufdatum;
        this.mPreisvorstellung = mPreisvorstellung;
        this.mFirma = mFirma;
        this.mPrivat = mPrivat;
        this.mKategorie = mKategorie;
        this.mBild = mBild;
    }


    public Blob getmBild() {
        return mBild;
    }

    public void setmBild(Blob mBild) {
        this.mBild = mBild;
    }

    public String getmKategorie() {
        return mKategorie;
    }

    public void setmKategorie(String mKategorie) {
        this.mKategorie = mKategorie;
    }

    public String getmPreisvorstellung() {
        return mPreisvorstellung;
    }

    public void setmPreisvorstellung(String mPreisvorstellung) {
        this.mPreisvorstellung = mPreisvorstellung;
    }

    public String getmAblaufdatum() {
        return mAblaufdatum;
    }

    public void setmAblaufdatum(String mAblaufdatum) {
        this.mAblaufdatum = mAblaufdatum;
    }

    public String getmEndtermin() {
        return mEndtermin;
    }

    public void setmEndtermin(String mEndtermin) {
        this.mEndtermin = mEndtermin;
    }

    public String getmStarttermin() {
        return mStarttermin;
    }

    public void setmStarttermin(String mStarttermin) {
        this.mStarttermin = mStarttermin;
    }

    public String getmBeschreibung() {
        return mBeschreibung;
    }

    public void setmBeschreibung(String mBeschreibung) {
        this.mBeschreibung = mBeschreibung;
    }

    public String getmFirma() {
        return mFirma;
    }

    public void setmFirma(String mFirma) {
        this.mFirma = mFirma;
    }

    public String getmPrivat() {
        return mPrivat;
    }

    public void setmPrivat(String mPrivat) {
        this.mPrivat = mPrivat;
    }
}
