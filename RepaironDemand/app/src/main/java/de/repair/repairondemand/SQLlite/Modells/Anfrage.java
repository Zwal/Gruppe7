package de.repair.repairondemand.SQLlite.Modells;

import java.sql.Blob;

public class Anfrage {

    private String mId;
    private String mBeschreibung;
    private String mStarttermin;
    private String mEndtermin;
    private String mAblaufdatum;
    private String mPreisvorstellung;
    private String mFirma;
    private String mPrivat;
    private String mKategorieIdFk;
    private String mAdresseIdFk;
    private Integer mUserId;
    // private String mBenutzerIdFk;

    public Anfrage(){}

    public Anfrage(String mId, String mBeschreibung, String mStarttermin, String mEndtermin,
                   String mAblaufdatum, String mPreisvorstellung, String mFirma, String mPrivat,
                   String mKategorieIdFk){
        this.mBeschreibung = mBeschreibung;
        this.mStarttermin = mStarttermin;
        this.mEndtermin = mEndtermin;
        this.mAblaufdatum = mAblaufdatum;
        this.mPreisvorstellung = mPreisvorstellung;
        this.mFirma = mFirma;
        this.mPrivat = mPrivat;
        this.mKategorieIdFk = mKategorieIdFk;
        this.mId = mId;
    }

    public String getmKategorieIdFk() {
        return mKategorieIdFk;
    }

    public void setmKategorieIdFk(String mKategorieIdFk) {
        this.mKategorieIdFk = mKategorieIdFk;
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

    public String getmAdresseIdFk() {
        return mAdresseIdFk;
    }

    public void setmAdresseIdFk(String mAdresseIdFk) {
        this.mAdresseIdFk = mAdresseIdFk;
    }

    public Integer getmUserId() {
        return mUserId;
    }

    public void setmUserId(Integer mUserId) {
        this.mUserId = mUserId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @Override
    public String toString() {
        return getmBeschreibung();
    }
}
