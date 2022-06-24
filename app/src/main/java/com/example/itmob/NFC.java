package com.example.itmob;

import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxpilotto.creditcardview.CreditCardView;


public class NFC extends Fragment {
    NfcAdapter nfcAdapter;
    TextView textView;

    int count = 1;

    CreditCardView creditCardView;

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

        View view = getView();

        creditCardView = view.findViewById(R.id.card);
        creditCardView.setHolder("KHALID");


        textView = view.findViewById(R.id.label);
        String textview = (String) textView.getText();
        textView.setText("test");

    }

    @Override
    public void onResume() {
        super.onResume();
        textView.setText("test"+count);

        count++;

    }
}