package com.example.itmob;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;

public class NeueUebung extends Fragment {

    private EditText nameText, wiederholungText, saetzeText;
    private String selectedName, selectedMuskelgruppe;
    private Spinner spinnerName, spinnerMuskelgruppe;
    private ArrayAdapter<CharSequence> adapterName, adapterMuskelgruppe;
    protected View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.neueuebung_fragment, container, false);
        wiederholungText = view.findViewById(R.id.Wiederholung);
        saetzeText = view.findViewById(R.id.Saetze);
        Button commitButton, viewButton;

        //Spinner Muskelgruppe initalisieren
        spinnerMuskelgruppe = view.findViewById(R.id.spinnerMuskulatur);

        //ArrayAdapter mit StringArray
        adapterMuskelgruppe = ArrayAdapter.createFromResource(getActivity(), R.array.array_Muskelgruppe, R.layout.spinner_layout);
        adapterMuskelgruppe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMuskelgruppe.setAdapter(adapterMuskelgruppe);
        spinnerName = view.findViewById(R.id.spinnerName);

        //spezifizierter zweiter Spinner
        spinnerMuskelgruppe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMuskelgruppe = spinnerMuskelgruppe.getSelectedItem().toString();
                int parendID = R.id.spinnerMuskulatur;
                switch (selectedMuskelgruppe) {
                    case "Rücken":
                        adapterName = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.array_rücken, R.layout.spinner_layout);
                        break;
                    case "Brust":
                        adapterName = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.array_brust, R.layout.spinner_layout);
                        break;
                    case "Beine":
                        adapterName = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.array_bein, R.layout.spinner_layout);
                        break;
                    case "Arme":
                        adapterName = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.array_arm, R.layout.spinner_layout);
                        break;
                    case "Bauch":
                        adapterName = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.array_bauch, R.layout.spinner_layout);
                        break;
                    case "Schulter":
                        adapterName = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.array_schulter, R.layout.spinner_layout);
                        break;
                    default:
                        adapterName = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.array_default_Uebungen, R.layout.spinner_layout);
                        break;

                }
                adapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerName.setAdapter(adapterName);
                // Übung von der Liste bekommen und in String umwandeln
                spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedName = spinnerName.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        commitButton = view.findViewById(R.id.commit);

        commitButton.setOnClickListener(view -> {
            if(selectedName.isEmpty() || selectedMuskelgruppe.isEmpty() || wiederholungText.getText().toString().isEmpty() || saetzeText.getText().toString().isEmpty()){
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
            uebung = new Uebung(-1, finalUserID, selectedName,
                    selectedMuskelgruppe,
                    Integer.parseInt(saetzeText.getText().toString()),
                    Integer.parseInt(wiederholungText.getText().toString()));
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
