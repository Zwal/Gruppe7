package de.repair.repairondemand.SQLlite;

public class SQLiteInit {

    public static final String TABLE_PRIVATPERSON = "privatperson";
    public static final String TABLE_FIRMA = "firma";
    public static final String TABLE_ADRESSE = "adresse";
    public static final String TABLE_BENUTZERKONTO = "benutzerkonto";
    public static final String TABLE_ANFRAGE = "anfrage";
    public static final String TABLE_ANGEBOT = "angebot";
    public static final String TABLE_KATEGORIE = "kategorie";
    public static final String TABLE_AUFTRAGKATEGORIE = "auftragKategorie";

    // table privatperson
    public static final String COLUMN_PERS_ID_PK = "pers_id_pk";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VORNAME = "vorname";
    public static final String COLUMN_GEBURTSDATUM = "geburtsdatum";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_TELEFON = "telefon";
    public static final String COLUMN_QUALIFIKATION = "qualifikation";
    public static final String COLUMN_BENUTZER_ID_FK = "benutzer_id_fk";
    public static final String COLUMN_ADRESSE_ID_FK = "adresse_id_fk";

    // table Firma
    public static final String COLUMN_FIRMA_ID_PK = "firma_id_pk";
    // name
    public static final String COLUMN_RECHTSFORM = "rechtsform";
    public static final String COLUMN_Amtsgericht = "amtsgericht";
    // bentuzer_id-fk, telefon
    public static final String COLUMN_TÄTIGKEITSFELDER = "tätigkeitsfelder";
    // email
    public static final String COLUMN_HR_AUSZUG_GEWERBESCHEIN = "hr_auszug_gewerbe";
    // adresse_id_fk

    // table adresse
    public static final String COLUMN_ADRESSE_ID_PK = "adresse_id_pk";
    public static final String COLUMN_STRASSE = "strasse";
    public static final String COLUMN_HAUSNUMMER = "hausnummer";
    public static final String COLUMN_PLZ = "plz";
    public static final String COLUMN_ORT = "ort";
    public static final String COLUMN_LAND = "land";

    // table benutzerkonto
    public static final String COLUMN_BENUTZER_ID_PK = "benutzer_id_pk";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORT = "passwort";
    //public static final String COLUMN_SICHERHEITSFRAGE = "sicherheitsfrage";
    //public static final String COLUMN_ANTWORT = "antwort";

    // table anfrage
    public static final String COLUMN_ANFRAGE_ID_PK = "anfrage_id_pk";
    public static final String COLUMN_BESCHREIBUNG = "beschreibung";
    public static final String COLUMN_STARTTERMIN= "starttermin";
    public static final String COLUMN_ENDTERMIN = "endtermin";
    public static final String COLUMN_ABLAUFDATUM = "ablaufdatum";
    public static final String COLUMN_PREISVORSTELLUNG = "preisvorstellung";
    public static final String COLUMN_KATEGORIE_ID_FK = "kategorie_id_fk";
    public static final String COLUMN_BILD = "bild";
    public static final String COLUMN_FIRMA = "firma";
    public static final String COLUMN_PRIVAT = "privat";
    // benutzer_id_fk, adresse_id_fk

    // table Kategorie
    public static final String COLUMN_KATEGORIE_ID_PK = "vorname";
    // beschreibung

    // table auftragKategorie
    public static final String COLUMN_AUFTRAGS_ID_FK = "auftrags_id_fk";
    //kategorie_id_fk

    // table angebot
    public static final String COLUMN_ANGEBOTS_ID_PK = "angebots_id_pk";
    // starttermin, endtermin, beschreibung
    public static final String COLUMN_ANFRAGE_ID_FK = "anfrage_id_fk";
    // benutzer_id_fk

    public static final String SQL_CREATE_PRIVATPERSON =
            "CREATE TABLE " + TABLE_PRIVATPERSON +
                    "(" + COLUMN_PERS_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_VORNAME + " INTEGER NOT NULL, " +
                    COLUMN_GEBURTSDATUM + " TEXT NOT NULL, " +
                    COLUMN_EMAIL + " TEXT NOT NULL," +
                    COLUMN_TELEFON + " TEXT NOT NULL," +
                    COLUMN_QUALIFIKATION + " TEXT NOT NULL," +
                    COLUMN_BENUTZER_ID_FK + " INTEGER NOT NULL," +
                    COLUMN_ADRESSE_ID_FK + " INTEGER NOT NULL);";

    public static final String SQL_CREATE_FIRMA =
            "CREATE TABLE " + TABLE_FIRMA +
                    "(" + COLUMN_FIRMA_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_RECHTSFORM + " TEXT NOT NULL, " +
                    COLUMN_Amtsgericht + " TEXT NOT NULL, " +
                    COLUMN_BENUTZER_ID_FK + " TEXT NOT NULL," +
                    COLUMN_TELEFON + " TEXT NOT NULL," +
                    COLUMN_TÄTIGKEITSFELDER + " TEXT NOT NULL," +
                    COLUMN_EMAIL + " TEXT NOT NULL," +
                    COLUMN_HR_AUSZUG_GEWERBESCHEIN + " BLOB NOT NULL," +
                    COLUMN_ADRESSE_ID_FK + " INTEGER NOT NULL);";

    public static final String SQL_CREATE_ADRESSE =
            "CREATE TABLE " + TABLE_ADRESSE +
                    "(" + COLUMN_ADRESSE_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_STRASSE + " TEXT NOT NULL, " +
                    COLUMN_HAUSNUMMER + " INTEGER NOT NULL, " +
                    COLUMN_PLZ + " INTEGER NOT NULL, " +
                    COLUMN_ORT + " TEXT NOT NULL," +
                    COLUMN_LAND + " TEXT NOT NULL);";

    public static final String SQL_CREATE_BENUTZERKONTO =
            "CREATE TABLE " + TABLE_BENUTZERKONTO +
                    "(" + COLUMN_BENUTZER_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_PASSWORT + " TEXT NOT NULL);";
                    //COLUMN_SICHERHEITSFRAGE + " TEXT NOT NULL, " +
                    //COLUMN_ANTWORT + " TEXT NOT NULL);";

    public static final String SQL_CREATE_ANFRAGE =
            "CREATE TABLE " + TABLE_ANFRAGE +
                    "(" + COLUMN_ANFRAGE_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BESCHREIBUNG + " TEXT NOT NULL, " +
                    COLUMN_STARTTERMIN + " TEXT NOT NULL, " +
                    COLUMN_ENDTERMIN + " TEXT NOT NULL, " +
                    COLUMN_ABLAUFDATUM + " TEXT NOT NULL, " +
                    COLUMN_PREISVORSTELLUNG + " TEXT NOT NULL, " +
                    COLUMN_FIRMA + " TEXT NOT NULL, " +
                    COLUMN_PRIVAT + " TEXT NOT NULL, " +
                    COLUMN_KATEGORIE_ID_FK + " INTEGER NOT NULL, " +
                    COLUMN_BILD + " BLOB, " +
                    COLUMN_BENUTZER_ID_FK + " INTEGER NOT NULL, " +
                    COLUMN_ADRESSE_ID_FK + " INTEGER NOT NULL);";

    public static final String SQL_CREATE_ANGEBOT =
            "CREATE TABLE " + TABLE_ANGEBOT +
                    "(" + COLUMN_ANGEBOTS_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PREISVORSTELLUNG + " TEXT NOT NULL, " +
                    COLUMN_STARTTERMIN + " TEXT NOT NULL, " +
                    COLUMN_ENDTERMIN + " TEXT NOT NULL, " +
                    COLUMN_BESCHREIBUNG + " TEXT NOT NULL, " +
                    COLUMN_ANFRAGE_ID_FK + " INTEGER NOT NULL, " +
                    COLUMN_BENUTZER_ID_FK + " INTEGER NOT NULL);";

    public static final String SQL_CREATE_KATEGORIE =
            "CREATE TABLE " + TABLE_KATEGORIE +
                    "(" + COLUMN_KATEGORIE_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BESCHREIBUNG + " TEXT NOT NULL);";

    public static final String SQL_CREATE_AUFTRAGKATEGORIE =
            "CREATE TABLE " + TABLE_AUFTRAGKATEGORIE +
                    "(" + COLUMN_AUFTRAGS_ID_FK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_KATEGORIE_ID_PK + " INTEGER NOT NULL);";

    String[] tableArray = new String[]{SQL_CREATE_PRIVATPERSON, SQL_CREATE_FIRMA, SQL_CREATE_ADRESSE,
            SQL_CREATE_BENUTZERKONTO, SQL_CREATE_ANFRAGE, SQL_CREATE_ANGEBOT,
            SQL_CREATE_KATEGORIE, SQL_CREATE_AUFTRAGKATEGORIE};

    public String[] tableArray(){
        return tableArray;
    }
}
