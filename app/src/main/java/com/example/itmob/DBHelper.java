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
        db.execSQL("CREATE TABLE courses_table(courseid INTEGER PRIMARY KEY AUTOINCREMENT , courseName TEXT, courseTrainer TEXT, courseDate TEXT, courseStartTime TEXT , courseTimeDuration TEXT, courseDescription TEXT)");
        // creating table for participants of a particular course
        db.execSQL("CREATE TABLE PARTICIPANTS(courseId INTEGER  , vertragEmail TEXT , pID INTEGER PRIMARY KEY)");

        fillVertragsDaten(db);
        fillActiveUserDaten(db);
        fillCourseDaten(db);

    }

    private void fillActiveUserDaten(SQLiteDatabase db) {
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (1);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (2);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (3);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (4);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (5);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (6);");
        db.execSQL("INSERT INTO ACTIVEUSER (userID) VALUES (7);");
    }

    public void fillCourseDaten(SQLiteDatabase db){
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (1, 'Pilates', 'Ulrike', '14-08-2022','08:15','40 Minuten', 'Mit der Konzentration auf jeden Muskel und auf jedes Gelenk werden Körper und Geist in einen nahezu meditativen Zustand versetzt-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (2, 'Pilates', 'Ulrike', '15-08-2022','08:15','40 Minuten', 'Mit der Konzentration auf jeden Muskel und auf jedes Gelenk werden Körper und Geist in einen nahezu meditativen Zustand versetzt-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (3, 'Pilates', 'Ulrike', '16-08-2022','08:15','40 Minuten', 'Mit der Konzentration auf jeden Muskel und auf jedes Gelenk werden Körper und Geist in einen nahezu meditativen Zustand versetzt-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (4, 'Pilates', 'Ulrike', '17-08-2022','08:15','40 Minuten', 'Mit der Konzentration auf jeden Muskel und auf jedes Gelenk werden Körper und Geist in einen nahezu meditativen Zustand versetzt-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (5, 'Pilates', 'Ulrike', '18-08-2022','08:15','40 Minuten', 'Mit der Konzentration auf jeden Muskel und auf jedes Gelenk werden Körper und Geist in einen nahezu meditativen Zustand versetzt-' );");

        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (6, 'BodyFit', 'Bryan', '14-08-2022','09:30','40 Minuten', 'Fit werden, Pfunde verlieren - und das wie im Flug- Es werden Kondition, Kraft und Beweglichkeit trainiert-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (7, 'BodyFit', 'Bryan', '15-08-2022','09:30','40 Minuten', 'Fit werden, Pfunde verlieren - und das wie im Flug- Es werden Kondition, Kraft und Beweglichkeit trainiert-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (8, 'BodyFit', 'Bryan', '16-08-2022','09:30','40 Minuten', 'Fit werden, Pfunde verlieren - und das wie im Flug- Es werden Kondition, Kraft und Beweglichkeit trainiert-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (9, 'BodyFit', 'Bryan', '17-08-2022','09:30','40 Minuten', 'Fit werden, Pfunde verlieren - und das wie im Flug- Es werden Kondition, Kraft und Beweglichkeit trainiert-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (10, 'BodyFit', 'Bryan', '18-08-2022','09:30','40 Minuten', 'Fit werden, Pfunde verlieren - und das wie im Flug- Es werden Kondition, Kraft und Beweglichkeit trainiert-' );");

        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (11, 'RückenFit', 'Danial', '14-08-2022','10:30','40 Minuten', 'In diesem Kurs geht es nicht nur darum, den Rücken und die Halswirbelsäule zu stärken, sondern vielmehr darum ein muskuläres Gleichgewicht im ganzen Körper herzustellen-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (12, 'RückenFit', 'Danial', '15-08-2022','10:30','40 Minuten', 'In diesem Kurs geht es nicht nur darum, den Rücken und die Halswirbelsäule zu stärken, sondern vielmehr darum ein muskuläres Gleichgewicht im ganzen Körper herzustellen-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (13, 'RückenFit', 'Danial', '16-08-2022','10:30','40 Minuten', 'In diesem Kurs geht es nicht nur darum, den Rücken und die Halswirbelsäule zu stärken, sondern vielmehr darum ein muskuläres Gleichgewicht im ganzen Körper herzustellen-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (14, 'RückenFit', 'Danial', '17-08-2022','10:30','40 Minuten', 'In diesem Kurs geht es nicht nur darum, den Rücken und die Halswirbelsäule zu stärken, sondern vielmehr darum ein muskuläres Gleichgewicht im ganzen Körper herzustellen-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (15, 'RückenFit', 'Danial', '18-08-2022','10:30','40 Minuten', 'In diesem Kurs geht es nicht nur darum, den Rücken und die Halswirbelsäule zu stärken, sondern vielmehr darum ein muskuläres Gleichgewicht im ganzen Körper herzustellen-' );");

        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (16, 'Zumba', 'Sandra', '14-08-2022','17:00','40 Minuten', 'Zumba kombiniert Aerobic mit lateinamerikanischen sowie internationalen Tänzen-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (17, 'Zumba', 'Sandra', '15-08-2022','17:00','40 Minuten', 'Zumba kombiniert Aerobic mit lateinamerikanischen sowie internationalen Tänzen-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (18, 'Zumba', 'Sandra', '16-08-2022','17:00','40 Minuten', 'Zumba kombiniert Aerobic mit lateinamerikanischen sowie internationalen Tänzen-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (19, 'Zumba', 'Sandra', '17-08-2022','17:00','40 Minuten', 'Zumba kombiniert Aerobic mit lateinamerikanischen sowie internationalen Tänzen-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (20, 'Zumba', 'Sandra', '18-08-2022','17:00','40 Minuten', 'Zumba kombiniert Aerobic mit lateinamerikanischen sowie internationalen Tänzen-' );");

        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (21, 'Fatburner', 'Philipp', '14-08-2022','10:00','40 Minuten', 'In diesem Kurs wird die Ausdauer mithilfe einfacher Schrittkombinationen und die Kräftigung in Intervallen trainiert- Wir arbeiten mit verschiedenen Kleingeräten (z-B- Tubes, Hanteln, etc-)-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (22, 'Fatburner', 'Philipp', '15-08-2022','10:00','40 Minuten', 'In diesem Kurs wird die Ausdauer mithilfe einfacher Schrittkombinationen und die Kräftigung in Intervallen trainiert- Wir arbeiten mit verschiedenen Kleingeräten (z-B- Tubes, Hanteln, etc-)-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (23, 'Fatburner', 'Philipp', '16-08-2022','10:00','40 Minuten', 'In diesem Kurs wird die Ausdauer mithilfe einfacher Schrittkombinationen und die Kräftigung in Intervallen trainiert- Wir arbeiten mit verschiedenen Kleingeräten (z-B- Tubes, Hanteln, etc-)-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (24, 'Fatburner', 'Philipp', '17-08-2022','10:00','40 Minuten', 'In diesem Kurs wird die Ausdauer mithilfe einfacher Schrittkombinationen und die Kräftigung in Intervallen trainiert- Wir arbeiten mit verschiedenen Kleingeräten (z-B- Tubes, Hanteln, etc-)-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (25, 'Fatburner', 'Philipp', '18-08-2022','10:00','40 Minuten', 'In diesem Kurs wird die Ausdauer mithilfe einfacher Schrittkombinationen und die Kräftigung in Intervallen trainiert- Wir arbeiten mit verschiedenen Kleingeräten (z-B- Tubes, Hanteln, etc-)-' );");

        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (26, 'Bodyworkout', 'Philipp', '14-08-2022','11:00','40 Minuten', 'Bodyworkout ist ein Ganzkörperprogramm in dem es darum geht mit dem eigenen Körpergewicht zu arbeiten um die Körpermitte zu stärken-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (27, 'Bodyworkout', 'Philipp', '15-08-2022','11:00','40 Minuten', 'Bodyworkout ist ein Ganzkörperprogramm in dem es darum geht mit dem eigenen Körpergewicht zu arbeiten um die Körpermitte zu stärken-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (28, 'Bodyworkout', 'Philipp', '16-08-2022','11:00','40 Minuten', 'Bodyworkout ist ein Ganzkörperprogramm in dem es darum geht mit dem eigenen Körpergewicht zu arbeiten um die Körpermitte zu stärken-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (29, 'Bodyworkout', 'Philipp', '17-08-2022','11:00','40 Minuten', 'Bodyworkout ist ein Ganzkörperprogramm in dem es darum geht mit dem eigenen Körpergewicht zu arbeiten um die Körpermitte zu stärken-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (30, 'Bodyworkout', 'Philipp', '18-08-2022','11:00','40 Minuten', 'Bodyworkout ist ein Ganzkörperprogramm in dem es darum geht mit dem eigenen Körpergewicht zu arbeiten um die Körpermitte zu stärken-' );");

        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (31, 'Bodypump', 'Danial', '14-08-2022','16:00','40 Minuten', 'Kräftigung und Straffung des gesamten Körpers, stärkt Knochen und das Immunsystem-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (32, 'Bodypump', 'Danial', '15-08-2022','16:00','40 Minuten', 'Kräftigung und Straffung des gesamten Körpers, stärkt Knochen und das Immunsystem-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (33, 'Bodypump', 'Danial', '16-08-2022','16:00','40 Minuten', 'Kräftigung und Straffung des gesamten Körpers, stärkt Knochen und das Immunsystem-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (34, 'Bodypump', 'Danial', '17-08-2022','16:00','40 Minuten', 'Kräftigung und Straffung des gesamten Körpers, stärkt Knochen und das Immunsystem-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (35, 'Bodypump', 'Danial', '18-08-2022','16:00','40 Minuten', 'Kräftigung und Straffung des gesamten Körpers, stärkt Knochen und das Immunsystem-' );");

        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (36, 'Bauch Special', 'Filiz', '14-08-2022','19:00','40 Minuten', 'Intensive Übungen rund um die Bauchmuskulatur: hier wird trainiert und gekräftigt!' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (37, 'Bauch Special', 'Filiz', '15-08-2022','19:00','40 Minuten', 'Intensive Übungen rund um die Bauchmuskulatur: hier wird trainiert und gekräftigt!' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (38, 'Bauch Special', 'Filiz', '16-08-2022','19:00','40 Minuten', 'Intensive Übungen rund um die Bauchmuskulatur: hier wird trainiert und gekräftigt!' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (39, 'Bauch Special', 'Filiz', '17-08-2022','19:00','40 Minuten', 'Intensive Übungen rund um die Bauchmuskulatur: hier wird trainiert und gekräftigt!' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (40, 'Bauch Special', 'Filiz', '18-08-2022','19:00','40 Minuten', 'Intensive Übungen rund um die Bauchmuskulatur: hier wird trainiert und gekräftigt!' );");

        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (41, 'BBP Plus', 'Filiz', '14-08-2022','12:00','40 Minuten', 'Bei diesem Kurs wird mit intensiven Übungen die Gesäß-, Bein-, und Bauchmuskulatur trainiert und gekräftigt!' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (42, 'BBP Plus', 'Filiz', '15-08-2022','12:00','40 Minuten', 'Bei diesem Kurs wird mit intensiven Übungen die Gesäß-, Bein-, und Bauchmuskulatur trainiert und gekräftigt!' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (43, 'BBP Plus', 'Filiz', '16-08-2022','12:00','40 Minuten', 'Bei diesem Kurs wird mit intensiven Übungen die Gesäß-, Bein-, und Bauchmuskulatur trainiert und gekräftigt!' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (44, 'BBP Plus', 'Filiz', '17-08-2022','12:00','40 Minuten', 'Bei diesem Kurs wird mit intensiven Übungen die Gesäß-, Bein-, und Bauchmuskulatur trainiert und gekräftigt!' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (45, 'BBP Plus', 'Filiz', '18-08-2022','12:00','40 Minuten', 'Bei diesem Kurs wird mit intensiven Übungen die Gesäß-, Bein-, und Bauchmuskulatur trainiert und gekräftigt!' );");

        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (46, 'Body Balance', 'Bryan', '14-08-2022','11:00','40 Minuten', 'Bodybalance ist das beruhigende und zentrierte Yoga-, Thai Chi- und Pilates Workout zur Förderung von Beweglichkeit und Kraft-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (47, 'Body Balance', 'Bryan', '15-08-2022','11:00','40 Minuten', 'Bodybalance ist das beruhigende und zentrierte Yoga-, Thai Chi- und Pilates Workout zur Förderung von Beweglichkeit und Kraft-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (48, 'Body Balance', 'Bryan', '16-08-2022','11:00','40 Minuten', 'Bodybalance ist das beruhigende und zentrierte Yoga-, Thai Chi- und Pilates Workout zur Förderung von Beweglichkeit und Kraft-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (49, 'Body Balance', 'Bryan', '17-08-2022','11:00','40 Minuten', 'Bodybalance ist das beruhigende und zentrierte Yoga-, Thai Chi- und Pilates Workout zur Förderung von Beweglichkeit und Kraft-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (50, 'Body Balance', 'Bryan', '18-08-2022','11:00','40 Minuten', 'Bodybalance ist das beruhigende und zentrierte Yoga-, Thai Chi- und Pilates Workout zur Förderung von Beweglichkeit und Kraft-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (51, 'Body Balance', 'Bryan', '07-07-2022','16:00','40 Minuten', 'Bodybalance ist das beruhigende und zentrierte Yoga-, Thai Chi- und Pilates Workout zur Förderung von Beweglichkeit und Kraft-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (52, 'Body Balance', 'Bryan', '14-07-2022','16:00','40 Minuten', 'Bodybalance ist das beruhigende und zentrierte Yoga-, Thai Chi- und Pilates Workout zur Förderung von Beweglichkeit und Kraft-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (53, 'Body Balance', 'Bryan', '21-07-2022','16:00','40 Minuten', 'Bodybalance ist das beruhigende und zentrierte Yoga-, Thai Chi- und Pilates Workout zur Förderung von Beweglichkeit und Kraft-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (54, 'Body Balance', 'Bryan', '28-07-2022','16:00','40 Minuten', 'Bodybalance ist das beruhigende und zentrierte Yoga-, Thai Chi- und Pilates Workout zur Förderung von Beweglichkeit und Kraft-' );");
        db.execSQL("INSERT INTO courses_table (courseid, courseName, courseTrainer, courseDate, courseStartTime, courseTimeDuration, courseDescription ) VALUES (55, 'Body Balance', 'Bryan', '04-08-2022','16:00','40 Minuten', 'Bodybalance ist das beruhigende und zentrierte Yoga-, Thai Chi- und Pilates Workout zur Förderung von Beweglichkeit und Kraft-' );");
    }

    public void fillVertragsDaten(SQLiteDatabase db){

        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (1, '01.02.2020', '01.02.2024', '19,99','Jens','Thieke','02.10.1988', 'jens.thieke@gmail.com', '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (2, '01.02.2020', '01.02.2024', '19,99','Ella','Barth','02.05.1996', 'ella.barth@gmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (3, '01.02.2020', '01.02.2024', '19,99','Leander','Moeller','02.01.1990', 'leander_moeller32@gmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (4, '15.02.2020', '15.02.2022', '29,99','Fabrice','James','24.05.1995', 'fabrice75@hotmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (5, '15.02.2020', '15.02.2023', '19,99','Eleni','James','16.07.1990', 'eleni36@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (6, '01.02.2021', '01.02.2023', '19,99','Cedric','Barranco','19.09.2001', 'cedric99@hotmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (7, '01.03.2020', '01.03.2024', '29,99','Mike','Franzis','07.07.1964', 'mike_franzis@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (8, '01.04.2021', '01.04.2023', '19,99','Klemens','Klein','19.07.1999', 'klemens56@gmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (9, '01.06.2020', '01.06.2024', '19,99','Alisa','Kunz','29.05.1996', 'alisa.kunz@gmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (10, '01.06.2020', '01.06.2024', '19,99','Juliana','Liedtke','28.08.1995', 'juliana12@gmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (11, '01.03.2020', '01.09.2024', '39,99','Kim','Possible','01.01.1993', 'kim1@hotmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (12, '15.06.2021', '15.06.2023', '19,99','Morten','Dan','08.08.1994', 'morten29@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (13, '01.01.2022', '01.01.2023', '19,99','Elli','Coleman','28.03.1980', 'elli77@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (14, '01.01.2022', '01.01.2023', '39,99','Samia','Humbert','21.06.2000', 'samia.humbert@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (15, '01.01.2022', '01.01.2023', '19,99','Alisa','Reus','13.01.1994', 'alisa.reus27@hotmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (16, '01.01.2022', '01.01.2023', '19,99','Anna','Huber','01.02.1992', 'ann.huber@gmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (17, '01.01.2022', '01.01.2023', '29,99','Elena','Kortmann','06.09.2000', 'elena83@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (18, '01.01.2022', '01.01.2023', '29,99','Eren','Krause','29.08.1988', 'eren.krause@gmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (19, '01.01.2022', '01.01.2023', '29,99','Dominik','Stuckmann','15.05.1997', 'dominik14@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (20, '01.01.2022', '01.01.2023', '19,99','Valentin','Linke','02.10.1992', 'valentin_linke9@gmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (21, '01.01.2022', '01.01.2023', '19,99','Malina','Koster','01.02.1992', 'malina_koster@gmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (22, '01.01.2022', '01.01.2023', '29,99','Elsa','Radow','08.02.1992', 'elsa73@hotmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (23, '01.01.2022', '01.01.2023', '19,99','Hailey','Bieber','21.02.1992', 'hailey49@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (24, '01.01.2022', '01.01.2023', '39,99','Nathalie','Freimuth','28.02.1992', 'nathalie_freimuth@hotmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (25, '01.01.2022', '01.01.2023', '39,99','Peter','Pan','17.02.1992', 'peter19@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (26, '01.01.2022', '01.01.2023', '29,99','Johnny','Depp','06.05.1992', 'john80@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (27, '01.01.2022', '01.01.2023', '29,99','Moritz','Grams','03.02.1992', 'moritz.grams@hotmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (28, '15.01.2022', '15.01.2023', '19,99','Mariella','Spitzmueller','05.12.1992', 'mariella.spitzmueller@yahoo.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (29, '15.01.2022', '15.01.2023', '19,99','James','Rodriguez','11.02.1992', 'james73@hotmail.com' , '0' );");
        db.execSQL("INSERT INTO VERTRAG (vertragID, startlaufzeit, endlaufzeit, preis, vorname, nachname, geburtsdatum, email, KuendigungVorgemerkt ) VALUES (30, '15.01.2022', '15.01.2023', '19,99','Rayan','Khan','23.03.1992', 'k' , '0' );");
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

    public ArrayList<String> getCourseData(String courseName){

        ArrayList<String> courseData = new ArrayList<>();

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from courses_table where courseName = ?", new String[]{courseName});

        if (cursor.moveToFirst()){

            String db_courseName = cursor.getString(1);
            String  db_courseTrainer = cursor.getString(2);
            String db_courseDate = cursor.getString(3);
            String db_courseStartTime = cursor.getString(4);
            String db_courseDuration = cursor.getString(5);
            String db_courseDescription = cursor.getString(6);


            courseData.add(db_courseName);
            courseData.add(db_courseTrainer);
            courseData.add(db_courseDate);
            courseData.add(db_courseStartTime);
            courseData.add(db_courseDuration);
            courseData.add(db_courseDescription);

        }
        cursor.close();
        return courseData;
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
            String kuendigungVormerkung = cursor.getString(8);

            userData.add(startLaufzeit);
            userData.add(endLaufzeit);
            userData.add(preis);
            userData.add(vorname);
            userData.add(nachname);
            userData.add(geburtsdatum);
            userData.add(email);
            userData.add(vertragsnummer);
            userData.add(kuendigungVormerkung);

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
            Toast.makeText(dbContext, "Für Kurs erfolgreich eingetragen", Toast.LENGTH_SHORT).show();;
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
