package com.example.itmob;

public class Uebung {



    private int id = 0;
    private String name = " ";
    private String muskelgruppe = " ";
    private int saetze = 0;
    private int wiederholungen = 0;
    private int userid=0;

    Uebung(int i, int u, String n, String m, int s, int w) {
        this.id = i;
        this.name = n;
        this.muskelgruppe = m;
        this.saetze = s;
        this.wiederholungen = w;
        this.userid = u;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMuskelgruppe() {
        return muskelgruppe;
    }

    public int getSaetze() {
        return saetze;
    }
    public int getWiederholungen() {
        return wiederholungen;
    }
    public int getUserid() {return userid;}

}
