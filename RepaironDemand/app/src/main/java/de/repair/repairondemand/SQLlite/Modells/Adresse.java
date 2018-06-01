package de.repair.repairondemand.SQLlite.Modells;

public class Adresse {
    private String mId;
    private String mStrasseHausnummer;
    private String mPlz;
    private String mOrt;
    private String mLand;

    public Adresse(){

    }


    public String getmStrasseHausnummer() {
        return mStrasseHausnummer;
    }

    public void setmStrasseHausnummer(String mStrasseHausnummer) {
        this.mStrasseHausnummer = mStrasseHausnummer;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmOrt() {
        return mOrt;
    }

    public void setmOrt(String mOrt) {
        this.mOrt = mOrt;
    }

    public String getmPlz() {
        return mPlz;
    }

    public void setmPlz(String mPlz) {
        this.mPlz = mPlz;
    }

    public String getmLand() {
        return mLand;
    }

    public void setmLand(String mLand) {
        this.mLand = mLand;
    }

    @Override
    public String toString() {
        return mPlz + " " + mOrt + ", " + mLand;
    }
}
