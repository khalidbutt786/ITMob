package com.example.itmob;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profil extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String startLaufzeit ;
    private String endLaufzeit;
    private String preis;
    private String vorname;
    private String nachname;
    private String geburtsdatum;
    private String email;

    TextView textView_name, textView_email, textView_geburtsdatum, textView_startlaufzeit, textView_endlaufzeit, textView_preis;

    public Profil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profil.
     */
    // TODO: Rename and change types and number of parameters
    public static Profil newInstance(String param1, String param2) {
        Profil fragment = new Profil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }





    }

    private void refreshFragment() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getFragmentManager().beginTransaction().detach(this).commitNow();
            getFragmentManager().beginTransaction().attach(this).commitNow();
        } else {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_profil, container, false);


        HomeActivity activity = (HomeActivity) getActivity();
        String email = activity.getUsername();

        DBHelper db = new DBHelper(this.getContext());
        ArrayList<String> userdata = db.getUserData(email);
        startLaufzeit = userdata.get(0);
        endLaufzeit = userdata.get(1);
        preis = userdata.get(2);
        vorname = userdata.get(3);
        nachname = userdata.get(4);
        geburtsdatum = userdata.get(5);
        email = userdata.get(6);

        textView_name = view.findViewById(R.id.name_label);
        textView_email = view.findViewById(R.id.email_label);
        textView_geburtsdatum = view.findViewById(R.id.geburtsdatum_label);
        textView_startlaufzeit = view.findViewById(R.id.startLaufzeit_label);
        textView_endlaufzeit = view.findViewById(R.id.endlaufzeit_label);
        textView_preis = view.findViewById(R.id.preis_label);

        textView_name.setText(vorname+" "+nachname);
        textView_endlaufzeit.setText(endLaufzeit);
        textView_startlaufzeit.setText(startLaufzeit);
        textView_preis.setText(preis+"€");
        textView_geburtsdatum.setText(geburtsdatum);
        textView_email.setText(email);

        return view;
    }


}