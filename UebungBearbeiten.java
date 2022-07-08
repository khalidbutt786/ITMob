package com.example.itmob;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UebungBearbeiten extends Fragment {

    private EditText uebung, muskelgruppe, saetze, wiederholungen, id;
    protected View view;
    Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DBHelper db = new DBHelper(getContext());

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.uebungbearbeiten_fragment, container, false);

        Bundle bundle = this.getArguments();

        String name = bundle.getString("Name");
        String muskelgruppe = bundle.getString("Muskelgruppe");
        String saetze = bundle.getString("Saetze");
        String wiederholung = bundle.getString("Wiederholung");
        int id = bundle.getInt("ID");

        EditText etName, etMuskelgruppe, etSaetze, etWiederholung, etID;

        etName = (EditText) view.findViewById(R.id.uebungName);
        etMuskelgruppe = (EditText) view.findViewById(R.id.uebungMuskelgruppe);
        etSaetze = (EditText) view.findViewById(R.id.uebungSaetze);
        etWiederholung = (EditText) view.findViewById(R.id.uebungWiederholung);
        etID = (EditText) view.findViewById(R.id.uebungId);

        String sId = String.valueOf(etID);


        etName.setText(name);
        etMuskelgruppe.setText(muskelgruppe);
        etSaetze.setText(saetze);
        etWiederholung.setText(wiederholung);

        btnSave = (Button) view.findViewById(R.id.saveChanges);
        btnSave.setOnClickListener(view -> {
            String sid=String.valueOf(etID);
            String newTitle = etName.getText().toString();
            String newMuskelgruppe = etMuskelgruppe.getText().toString();
            String newSaetze = etSaetze.getText().toString();
            String newWiederholungen = etWiederholung.getText().toString();
            //String newId =
            etID.setText(sId);


            //int newId2 = Integer.parseInt(sId);
            //Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
            db.update(id, newTitle, newMuskelgruppe, newSaetze, newWiederholungen);


            // Go to Fragment 2 to view data.
            Fragment fragment = new Workout();
            replaceFragment(fragment);
        });







        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        // Add to the backstack so we can return to current fragment.
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}