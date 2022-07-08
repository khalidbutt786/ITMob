package com.example.itmob;


import static java.lang.String.valueOf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Workout1 extends Fragment {

    protected View view;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_workout1, container, false);


        HomeActivity activity = (HomeActivity) getActivity();
        String email = activity.getUsername();
        String finalEmail = email;
        DBHelper dataBaseHelper = new DBHelper(getContext());
        int userId = dataBaseHelper.getUserID(finalEmail);
        String userID = valueOf(userId);
        ArrayList<Uebung> people = dataBaseHelper.getAll(userID);
        btn = (Button) view.findViewById(R.id.btnAdd);

        // Initialize RecyclerView.
        MyFragmentViewAdapter fragmentViewAdapter = new MyFragmentViewAdapter(people);
        RecyclerView fragmentRecyclerView = view.findViewById(R.id.infoDisplay);
        fragmentRecyclerView.setAdapter(fragmentViewAdapter);
        fragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //HomeActivity activity = (HomeActivity) getActivity();
        //String email = activity.getUsername();
        //String finalEmail = email;
        //String userId = activity.getUserID();


        btn.setOnClickListener(view -> {
            // Go to Fragment 2 to view data.
            //int userId = dataBaseHelper.getUserID(finalEmail);

            //Toast.makeText(getContext(), valueOf(userId), Toast.LENGTH_SHORT).show();
            Fragment fragment = new NeueUebung();
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
