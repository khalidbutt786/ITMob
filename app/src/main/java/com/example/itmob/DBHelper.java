package com.example.itmob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE VERTRAG(vertragID INT PRIMARY KEY, StartlaufZeit TEXT, EndLaufZeit Text, Preis TEXT, Vorname TEXT, Nachname TEXT, Geburtsdatum TEXT, Email TEXT, KuendigungVorgemerkt INT)");
        db.execSQL("CREATE TABLE UEBUNG(uebungID INT PRIMARY KEY, Name TEXT, Muskelgruppe Text, Wiederholungen TEXT, Saetze TEXT)");
        db.execSQL("CREATE TABLE USER(userID INT PRIMARY KEY, Email TEXT, Passwort TEXT, VertragID INT, FOREIGN KEY(VertragID) REFERENCES VERTRAG(vertragid), FOREIGN KEY(Email) REFERENCES VERTRAG(Email))");
        db.execSQL("CREATE TABLE USER_UEBUNG(user_uebungID INT PRIMARY KEY, USERID INT, UEBUNGID INT, FOREIGN KEY(USERID) REFERENCES USER(userID), FOREIGN KEY(UEBUNGID) REFERENCES UEBUNG(uebungID))");
        db.execSQL("CREATE TABLE ACTIVEUSER(userID INT PRIMARY KEY)");




        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email ) VALUES (1, '2022-06-23', '2024-06-23', '39.99','Khalid','Butt','06-05-1997', 'kb' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email ) VALUES (2, '2021-03-06', '2024-03-06', '24.00','Markus','Ruehl','23-08-1978', 'mr' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email ) VALUES (3, '2020-02-05', '2024-02-05', '19.99','Ronnie','Coleman','02-10-1988', 'rc' );");

        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (1);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (2);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (3);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (4);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (5);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (6);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (7);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (8);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (9);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists USER");
        db.execSQL("drop Table if exists UEBUNG");
        db.execSQL("drop Table if exists USER_UEBUNG");
        db.execSQL("drop Table if exists VERTRAG");

    }


    public int getActiveUser(){
        int count = 0;
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = myDB.rawQuery("Select Count(*) from ACTIVEUSER", new String[]{});

        if (cursor.moveToFirst()) {

            count = cursor.getInt(0);
            return count;
        }

        return count;

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

    public boolean updateKuendigungsstatus(String vertragsID){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        myDB.execSQL("UPDATE VERTRAG SET KuendigungVorgemerkt=1 WHERE vertragID='"+vertragsID+"'");

        return false;
    }

    public boolean getKuendigungsStatus(String vertragsID){

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = myDB.rawQuery("Select * from VERTRAG where vertragID= ?", new String[]{vertragsID});

        if (cursor.moveToFirst()){

            int status = cursor.getInt(8);

            if(status==0){
                return false;
            }
            else {
                return true;
            }

        }
        return false;

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

    public ArrayList<String> getUserData(String searchEmail){

        ArrayList<String> userData = new ArrayList<>();

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from VERTRAG where Email = ?", new String[]{searchEmail});

        if (cursor.moveToFirst()){

            String startLaufzeit = cursor.getString(1);
            String endLaufzeit = cursor.getString(2);
            String preis = cursor.getString(3);
            String vorname = cursor.getString(4);
            String nachname = cursor.getString(5);
            String geburtsdatum = cursor.getString(6);
            String email = cursor.getString(7);
            String vertragsnummer = cursor.getString(0);

            userData.add(startLaufzeit);
            userData.add(endLaufzeit);
            userData.add(preis);
            userData.add(vorname);
            userData.add(nachname);
            userData.add(geburtsdatum);
            userData.add(email);
            userData.add(vertragsnummer);

        }
        cursor.close();
        return userData;
    }

}