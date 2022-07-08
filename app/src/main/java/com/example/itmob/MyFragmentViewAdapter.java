package com.example.itmob;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;


public class MyFragmentViewAdapter extends RecyclerView.Adapter<MyFragmentViewAdapter.ViewHolder> {

    // List of people to populate the RecyclerView with.
    private static ArrayList<Uebung> trainingsplan;

    MyFragmentViewAdapter(ArrayList<Uebung> i) {
        trainingsplan = i;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        // Inflate RecyclerView with cards.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MyFragmentViewAdapter.ViewHolder holder, int position) {
        // Create Uebung object for current card.
        final Uebung currentUebung = trainingsplan.get(position);
        // Set Uebung name to name TextView.

        holder.nameField.setText(currentUebung.getName());
        holder.muskelgruppeField.setText(currentUebung.getMuskelgruppe());
        holder.saetzeField.setText(String.valueOf(currentUebung.getSaetze()));
        holder.wiederholungField.setText(String.valueOf(currentUebung.getWiederholungen()));
        holder.idField.setText(String.valueOf(currentUebung.getId()));
    }

    @Override
    public int getItemCount() {
        return trainingsplan.size();
    }


    /* Member class for ViewHolder */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        protected Context context;
        protected TextView nameField;
        protected TextView muskelgruppeField;
        protected TextView saetzeField;
        protected TextView wiederholungField;
        protected TextView idField;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {

            super(itemView);

            // Initialize context
            context = itemView.getContext();

            // Grab CardView TextView IDs.
            nameField = itemView.findViewById(R.id.uebungName);
            muskelgruppeField = itemView.findViewById(R.id.uebungMuskelgruppe);
            saetzeField = itemView.findViewById(R.id.uebungSaetze);
            wiederholungField = itemView.findViewById(R.id.uebungWiederholung);
            idField = itemView.findViewById(R.id.uebungId);

            DBHelper dataBaseHelper = new DBHelper(context);

            itemView.setOnClickListener(view -> {

                String name, muskelgruppe, saetze, wiederholung;
                name = nameField.getText().toString();
                muskelgruppe = muskelgruppeField.getText().toString();
                saetze = saetzeField.getText().toString();
                wiederholung = wiederholungField.getText().toString();
                int id = Integer.parseInt(idField.getText().toString());
                // Create uebung from card clicked by user.
                final Uebung thisUebung = trainingsplan.get(getAdapterPosition());
                // Try to delete the uebung & remove the card from the RecyclerView.
                try {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment fragment3 = new UebungBearbeiten();
                    //Toast.makeText(context, name+muskelgruppe+saetze+wiederholung, Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("Name", name);
                    bundle.putString("Muskelgruppe", muskelgruppe);
                    bundle.putString("Saetze", saetze);
                    bundle.putString("Wiederholung", wiederholung);
                    bundle.putInt("ID", id);
                    fragment3.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).addToBackStack(null).commit();
                    //dataBaseHelper.deleteOne(thisUebung);

                }

                catch(Exception e) {
                    Toast.makeText(context, "Fehler!", Toast.LENGTH_SHORT).show();
                }

            });

            itemView.setOnLongClickListener(view -> {
                final Uebung thisUebung = trainingsplan.get(getAdapterPosition());
                final android.app.AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("")
                        .setMessage("Übung löschen?")
                        .setPositiveButton("Weiter", null)
                        .setNegativeButton("Abbrechen", null)
                        .show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            dataBaseHelper.deleteOne(thisUebung);

                        } catch (Exception e) {
                            Toast.makeText(context, "Fehler", Toast.LENGTH_SHORT).show();
                        }
                        trainingsplan.remove(thisUebung);
                        dialog.dismiss();
                        notifyItemRemoved(getAdapterPosition());
                    }
                });

                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });



                return true;
            });

        }

    }

}
