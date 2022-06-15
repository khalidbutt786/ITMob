package com.example.itmob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE VERTRAG(vertragID INT PRIMARY KEY, StartlaufZeit TEXT, EndLaufZeit Text, Preis TEXT, Vorname TEXT, Nachname TEXT, Geburtsdatum TEXT, Email TEXT)");
        db.execSQL("CREATE TABLE UEBUNG(uebungID INT PRIMARY KEY, Name TEXT, Muskelgruppe Text, Wiederholungen TEXT, Saetze TEXT)");
        db.execSQL("CREATE TABLE USER(userID INT PRIMARY KEY, Email TEXT, Passwort TEXT, VertragID INT, FOREIGN KEY(VertragID) REFERENCES VERTRAG(vertragid), FOREIGN KEY(Email) REFERENCES VERTRAG(Email))");
        db.execSQL("CREATE TABLE USER_UEBUNG(user_uebungID INT PRIMARY KEY, USERID INT, UEBUNGID INT, FOREIGN KEY(USERID) REFERENCES USER(userID), FOREIGN KEY(UEBUNGID) REFERENCES UEBUNG(uebungID))");

        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email ) VALUES (2501, '2022-06-23', '2024-06-23', '39.99','Khalid','Butt','06-05-1997', 'khalidbutt@live.de' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email ) VALUES (2234, '2021-03-06', '2024-03-06', '24.00','Markus','Ruehl','23-08-1978', 'ruehl@gmail.com' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email ) VALUES (7866, '2020-02-05', '2024-02-05', '19.99','Ronnie','Coleman','02-10-1988', 'coleman@ronnie.de' );");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists USER");
        db.execSQL("drop Table if exists UEBUNG");
        db.execSQL("drop Table if exists USER_UEBUNG");
        db.execSQL("drop Table if exists VERTRAG");

    }

    public Boolean insertDataUser(String email, String password, int vertragsID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Email", email);
        contentValues.put("Passwort", password);
        contentValues.put("VertragID", vertragsID);

        long result = MyDB.insert("USER", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER where Email = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkvertrag(String email, String vertragsID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from VERTRAG where VertragID = ? and Email = ?", new String[]{vertragsID, email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean vertragsIDFromUser(String vertragsID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from VERTRAG where vertragID = ?", new String[]{vertragsID});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER where Email = ? and Passwort = ?", new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public void dropTable(String table) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query = "DROP TABLE " + table;
        MyDB.execSQL(query);
    }
}