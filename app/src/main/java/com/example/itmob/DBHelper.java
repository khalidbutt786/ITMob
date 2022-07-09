package com.example.itmob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";
    private Context dbContext;

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
        dbContext = context;
    }


    public static final String UEBUNG_TABLE = "UEBUNG_TABLE";
    public static final String COLUMN_ID = "ID";

    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_MUSKELGRUPPE = "MUSKELGRUPPE";
    public static final String COLUMN_SAETZE = "SAETZE";
    public static final String COLUMN_WIEDERHOLUNGEN = "WIEDERHOLUNG";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement="CREATE TABLE " + UEBUNG_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ "USERID INT,  " + COLUMN_NAME + " TEXT, " + COLUMN_MUSKELGRUPPE + " TEXT, " + COLUMN_SAETZE + " TEXT, " + COLUMN_WIEDERHOLUNGEN + " TEXT" + ")";
        db.execSQL("CREATE TABLE VERTRAG(vertragID INT PRIMARY KEY, StartlaufZeit TEXT, EndLaufZeit Text, Preis TEXT, Vorname TEXT, Nachname TEXT, Geburtsdatum TEXT, Email TEXT, KuendigungVorgemerkt INT)");
        db.execSQL(createTableStatement);
        db.execSQL("CREATE TABLE USER(userID INT PRIMARY KEY, Email TEXT, Passwort TEXT, VertragID INT,ImagePath TEXT, FOREIGN KEY(VertragID) REFERENCES VERTRAG(vertragid), FOREIGN KEY(Email) REFERENCES VERTRAG(Email))");
        db.execSQL("CREATE TABLE USER_UEBUNG(user_uebungID INT PRIMARY KEY, USERID INT, UEBUNGID INT, FOREIGN KEY(USERID) REFERENCES USER(userID), FOREIGN KEY(UEBUNGID) REFERENCES UEBUNG(uebungID))");
        db.execSQL("CREATE TABLE ACTIVEUSER(userID INT PRIMARY KEY)");



        // table for courses
        db.execSQL("CREATE TABLE courses_table(courseid INTEGER PRIMARY KEY AUTOINCREMENT , courseName TEXT, courseTrainer TEXT, courseDate TEXT, courseStartTime TEXT , courseTimeDuration TEXT)");
        // creating table for participants of a particular course
        db.execSQL("CREATE TABLE PARTICIPANTS(courseId INTEGER  , vertragEmail TEXT , pID INTEGER PRIMARY KEY)");

        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email ) VALUES (1, '23.06.2022', '23.06.2024', '39.99','Khalid','Butt','06.05.1997', 'kb' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email ) VALUES (2, '2021-03-06', '2024-03-06', '24.00','Markus','Ruehl','23-08-1978', 'mr' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email ) VALUES (3, '2020-02-05', '2024-02-05', '19.99','Ronnie','Coleman','02-10-1988', 'rc' );");

        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (1);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (2);");


        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration ) VALUES (999, 'SLIM FIT', 'ISRAR ALI', '30-06-2022','8:15','45 minutes' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration ) VALUES (510, 'BODY Fit', 'AHMAD', '30-06-2022','9:15','50 minutes' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration ) VALUES (515, 'Slim Fit', 'Adnan', '28-06-2022','9:15','50 minutes' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration ) VALUES (495, 'Slim Fit', 'Zeeshan', '01-07-2022','10:15','50 minutes' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration ) VALUES (395, 'Body Fit', 'Ajmal', '02-07-2022','8:15','40 minutes' );");

    }




    public ArrayList<Uebung> getAll(String userId) {
        // Initialize an arraylist to hold our database entries.
        ArrayList<Uebung> list = new ArrayList<>();
        // Choose table to select from.
        //String queryString = "SELECT * FROM " + UEBUNG_TABLE + " where USERID = ?", new String[]{userId}";
        // Get a readable database.
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery(queryString, null);
        Cursor cursor = db.rawQuery("Select * from UEBUNG_TABLE where USERID = ?", new String[]{userId});

        if(cursor.moveToFirst()) {
            // Loop through the cursor and create Uebung objects, put them into the list.
            do {
                int uebungID = cursor.getInt(0);
                int userID = cursor.getInt(1);
                String uebungName = cursor.getString(2);
                String uebungMuskelgruppe = cursor.getString(3);
                int uebungSaetze = cursor.getInt(4);
                int uebungWiederholung = cursor.getInt(5);
                // Create uebung object from table data.
                Uebung newUebung = new Uebung(uebungID, userID, uebungName, uebungMuskelgruppe, uebungSaetze, uebungWiederholung);
                list.add(newUebung);
                // Iterate until end of table.
            } while (cursor.moveToNext());
        }
        // Close cursor and database.
        cursor.close();
        db.close();

        return list;
    }
    public boolean update(int id, String name, String muskelgruppe, String saetze, String wiederholungen) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_MUSKELGRUPPE, muskelgruppe);
        cv.put(COLUMN_SAETZE, saetze);
        cv.put(COLUMN_WIEDERHOLUNGEN, wiederholungen);
        cv.put(COLUMN_ID, id);

        long update = db.update(UEBUNG_TABLE, cv,   COLUMN_ID+ " = " + id, new String[]{});
        return update!=-1;
    }

    public void deleteUebung(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String queryString = "DELETE FROM " + UEBUNG_TABLE + " WHERE " + COLUMN_NAME + " = " + name;
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        db.close();
    }
    public boolean addOne(Uebung uebung) {
        // Initialize writable database & content values.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Put content values.
        cv.put(COLUMN_NAME, uebung.getName());
        cv.put("USERID", uebung.getUserid());
        cv.put(COLUMN_MUSKELGRUPPE, uebung.getMuskelgruppe());
        cv.put(COLUMN_SAETZE, uebung.getSaetze());
        cv.put(COLUMN_WIEDERHOLUNGEN, uebung.getWiederholungen());

        // Insert into database.
        long insert = db.insert(UEBUNG_TABLE, null, cv);

        return insert != -1;
    }


    public void deleteOne(Uebung uebung) {
        // Find uebung in the database. If found, delete it and return true.
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + UEBUNG_TABLE + " WHERE " + COLUMN_ID + " = " + uebung.getId() + " ;";
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists USER");
        db.execSQL("drop Table if exists UEBUNG");
        db.execSQL("drop Table if exists USER_UEBUNG");
        db.execSQL("drop Table if exists VERTRAG");

    }

    public int getUserID(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from VERTRAG where Email = ?", new String[]{email});
        int id =0;
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }


    public String imagePath(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER where Email = ?", new String[]{email});
        String path = "";
        if (cursor.moveToFirst()){
            path = cursor.getString(4);
        }
        cursor.close();
        return path;
    }

    public void updateImagePath(String email, String path){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        myDB.execSQL("UPDATE USER SET ImagePath="+"'"+path+"'"+ " WHERE Email='"+email+"'");

    }


    public void deleteUser(String email){


        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        myDB.execSQL("DELETE FROM USER WHERE Email LIKE '"+email+"'");


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


    public List<ModelCourses> getAllCourses(){

        List<ModelCourses> coursesList = new ArrayList<>();

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from courses_table",null);


        if (cursor.moveToFirst()){

            do {

                String courseId = cursor.getString(0);
                String courseName = cursor.getString(1);
                String courseTrainer = cursor.getString(2);
                String courseDate = cursor.getString(3);
                String courseStartTime = cursor.getString(4);
                String courseTimeDuration = cursor.getString(5);


                coursesList.add(new ModelCourses(courseId,courseName , courseTrainer , courseDate , courseStartTime ,courseTimeDuration));




            }while (cursor.moveToNext());


        }
        cursor.close();

        return coursesList ;

    }


    public List<ModelCourses> getCoursesByDate(String date){

        List<ModelCourses> coursesList = new ArrayList<>();

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from courses_table WHERE courseDate=?",new String[]{date});

        if (cursor.moveToFirst()){

            do {

                String courseId = cursor.getString(0);
                String courseName = cursor.getString(1);
                String courseTrainer = cursor.getString(2);
                String courseDate = cursor.getString(3);
                String courseStartTime = cursor.getString(4);
                String courseTimeDuration = cursor.getString(5);


                coursesList.add(new ModelCourses(courseId,courseName , courseTrainer , courseDate , courseStartTime ,courseTimeDuration));




            }while (cursor.moveToNext());


        }
        cursor.close();

        return coursesList ;

    }

    public boolean insertParticipant(int courseId , String email){

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("courseId", courseId);
        contentValues.put("vertragEmail", email);


        long result = MyDB.insert("PARTICIPANTS", null, contentValues);
        if (result == -1)
        {
            return false;
        }
        else{
            Toast.makeText(dbContext, "Congrats You've participated", Toast.LENGTH_SHORT).show();;
            return  true ;
        }



    }

    // method to check If user has already participated


    public boolean checkIfParticipated(String courseId , String email){

        SQLiteDatabase MyDB = this.getWritableDatabase();
//        String query = "SELECT * FROM PARTICIPANTS WHERE courseId =?";
//        MyDB.execSQL(query,new Integer[]{courseId});

        Cursor cursor = MyDB.rawQuery("Select * from PARTICIPANTS where courseId = ? AND vertragEmail = ?", new String[]{courseId,email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }


    // Unrolling user from the course


    public boolean unRollUserFromCourse(String courseId){

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("DELETE FROM PARTICIPANTS WHERE courseId = ?",new String[]{courseId});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }



    // Get All Participants of a Course

    public List<String> gettingAllParticipants(String courseId){

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT vertragEmail FROM PARTICIPANTS WHERE courseId =?",new String[]{courseId});


        List<String> list = new ArrayList<>();


        if (cursor.moveToFirst()){

            do {

                String email = cursor.getString(0);
                list.add(email);


            }while (cursor.moveToNext());


        }
        cursor.close();

        return list;
    }


    // To Check Participants' names


    public String getParticipantsName(String searchEmail){

        ArrayList<String> namesList = new ArrayList<>();

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from VERTRAG where Email = ?", new String[]{searchEmail});

        if (cursor.moveToFirst()){


            String vorname = cursor.getString(4);
            String nachname = cursor.getString(5);


            namesList.add(vorname+" "+nachname);



        }
        cursor.close();
        return namesList.get(0);

    }


    public int getUserCount() {

            int userCount = 0;
            SQLiteDatabase myDB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            Cursor cursor = myDB.rawQuery("Select Count(*) from VERTRAG", new String[]{});

            if (cursor.moveToFirst()) {

                userCount = cursor.getInt(0);
                return userCount;
            }

            return userCount;


    }
}
