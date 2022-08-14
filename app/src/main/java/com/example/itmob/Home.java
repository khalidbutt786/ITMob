package com.example.itmob;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.ArrayList;


public class Home extends Fragment {
    NfcAdapter nfcAdapter;
    TextView mitgliedsnummer,vertragsinhaber, gueltig_bis, auslastung, auswertung;

    String email;
    private String endLaufzeit;
    private String vorname;
    private String nachname;
    private String vertragsnummer;
    int count = 1;
    private int progress = 0;

    private ProgressBar progressBar;
    private TextView textViewProgress, auslastung_beschreibung, welcomeUser;

    ImageView qrcode;


    public Home() {


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
        DBHelper db = new DBHelper(this.getContext());
        int count = db.getActiveUser();
        int alleMitglieder = db.getUserCount();
        double anzahlMitgliederInProzentDouble = ((double) count) / alleMitglieder;

        double anzahlMitgliederInProzent = anzahlMitgliederInProzentDouble*100;
        String result = String.format("%.0f", anzahlMitgliederInProzent);





        qrcode = view.findViewById(R.id.qrCode);

        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(emailuser, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) view.findViewById(R.id.qrCode)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }


        progressBar = view.findViewById(R.id.progress_bar);
        textViewProgress = view.findViewById(R.id.text_view_progress);

        progressBar.setProgress((int) anzahlMitgliederInProzent);
        textViewProgress.setText(result+"%");

        auslastung_beschreibung = view.findViewById(R.id.auslastung_beschreibung);


        if(anzahlMitgliederInProzent>=65){
            auslastung_beschreibung.setText("Es ist viel los aktuell");
        }
        if(anzahlMitgliederInProzent<65 && anzahlMitgliederInProzent>35){
            auslastung_beschreibung.setText("Es ist durschnittlich viel los aktuell");
        }
        if(anzahlMitgliederInProzent<=34){
            auslastung_beschreibung.setText("Es ist wenig los aktuell");
        }


        welcomeUser = view.findViewById(R.id.textView_welcomeUser);

        ArrayList<String> userdata = db.getUserData(emailuser);
        vorname = userdata.get(3);
        welcomeUser.setText("Hallo "+vorname+"!");


    }


    @Override
    public void onResume() {
        super.onResume();

        HomeActivity homeActivity = ((HomeActivity) getActivity());
        homeActivity.setTitle("Dashboard");
    }

    private void updateProgressBar()
    {
        progressBar.setProgress(progress);
        textViewProgress.setText(progress + "%");
    }



}