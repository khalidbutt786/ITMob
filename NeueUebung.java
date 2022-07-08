package com.example.itmob;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NeueUebung extends Fragment {

    private EditText nameText;
    private EditText muskelgruppeText, wiederholungText, saetzeText;

    protected View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.neueuebung_fragment, container, false);
        nameText = view.findViewById(R.id.name);
        muskelgruppeText = view.findViewById(R.id.muskelgruppe);
        wiederholungText = view.findViewById(R.id.Wiederholung);
        saetzeText = view.findViewById(R.id.Saetze);
        Button commitButton, viewButton;


        commitButton = view.findViewById(R.id.commit);
        //viewButton = view.findViewById(R.id.viewData);
        //Toast.makeText(getContext(), finalUserID, Toast.LENGTH_SHORT).show();
        commitButton.setOnClickListener(view -> {
            if(nameText.getText().toString().isEmpty() || muskelgruppeText.getText().toString().isEmpty() || wiederholungText.getText().toString().isEmpty() || saetzeText.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Bitte alle Felder ausfüllen",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                createEntry();
                Fragment fragment = new Workout1();
                replaceFragment(fragment);
            }
        });
        /*viewButton.setOnClickListener(view -> {
            Fragment fragment = new Workout();
            replaceFragment(fragment);
        });*/

        return view;
    }

    private void createEntry()
    {
        Uebung uebung;
        HomeActivity activity = (HomeActivity) getActivity();
        String id = activity.getUsername();
        DBHelper db = new DBHelper(getContext());
        int finalUserID = db.getUserID(id);

        try {
            uebung = new Uebung(-1, finalUserID, nameText.getText().toString(),
                    muskelgruppeText.getText().toString(),
                    Integer.parseInt(wiederholungText.getText().toString()),
                    Integer.parseInt(saetzeText.getText().toString()));
        }
        catch (Exception e) {
            uebung = new Uebung(-1, -1, "", "", -1, -1);
        }

        DBHelper dataBaseHelper = new DBHelper(getContext());

        boolean success = dataBaseHelper.addOne(uebung);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}