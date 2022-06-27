package com.example.itmob;

import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NFC extends Fragment {
    NfcAdapter nfcAdapter;
    TextView mitgliedsnummer,vertragsinhaber, gueltig_bis, auslastung, auswertung;

    String email;
    private String endLaufzeit;
    private String vorname;
    private String nachname;
    private String vertragsnummer;
    int count = 1;


    public NFC() {


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        float scale = this.getContext().getResources().getDisplayMetrics().density;

        HomeActivity activity = (HomeActivity) getActivity();
        String emailuser = activity.getUsername();

        View view = getView();
        gueltig_bis = view.findViewById(R.id.gueltig);
        vertragsinhaber = view.findViewById(R.id.vertragsinhaber);
        mitgliedsnummer = view.findViewById(R.id.mitgliedsnr_lbl2);
        auslastung = view.findViewById(R.id.anzahlMitglieder);
        auswertung = view.findViewById(R.id.auswertung_Auslastung);


        DBHelper db = new DBHelper(this.getContext());
        ArrayList<String> userdata = db.getUserData(emailuser);
        endLaufzeit = userdata.get(1);
        vorname = userdata.get(3);
        nachname = userdata.get(4);
        email = userdata.get(6);
        vertragsnummer = userdata.get(7);

        gueltig_bis.setText(endLaufzeit);
        vertragsinhaber.setText(vorname+" "+nachname);
        mitgliedsnummer.setText(vertragsnummer);

        int count = db.getActiveUser();
        auslastung.setText(""+count);

        if(count>=35){
            auswertung.setText("Es ist viel los heute");
        }
        if(count<19 && count>15){
            auswertung.setText("Es ist durschnittlich viel los heute");
        }
        if(count<=10){
            auswertung.setText("Es ist wenig los heute");
        }



    }

    @Override
    public void onResume() {
        super.onResume();

    }
}