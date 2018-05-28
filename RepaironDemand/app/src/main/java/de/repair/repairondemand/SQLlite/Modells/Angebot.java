package de.repair.repairondemand.SQLlite.Modells;

public class Angebot {

    private String mPreis;
    private String mZeitStart;
    private String mZeitEnde;
    private String mBeschreibung;
    private String mAnfrageId;
    private String mBenutzerId;

    public Angebot(){
    }

    public String getmZeitStart() {
        return mZeitStart;
    }

    public void setmZeitStart(String mZeitStart) {
        this.mZeitStart = mZeitStart;
    }

    public String getmPreis() {
        return mPreis;
    }

    public void setmPreis(String mPreis) {
        this.mPreis = mPreis;
    }

    public String getmZeitEnde() {
        return mZeitEnde;
    }

    public void setmZeitEnde(String mZeitEnde) {
        this.mZeitEnde = mZeitEnde;
    }

    public String getmBeschreibung() {
        return mBeschreibung;
    }

    public void setmBeschreibung(String mBeschreibung) {
        this.mBeschreibung = mBeschreibung;
    }

    public String getmAnfrageId() {
        return mAnfrageId;
    }

    public void setmAnfrageId(String mAnfrageId) {
        this.mAnfrageId = mAnfrageId;
    }

    public String getmBenutzerId() {
        return mBenutzerId;
    }

    public void setmBenutzerId(String mBenutzerId) {
        this.mBenutzerId = mBenutzerId;
    }
}
