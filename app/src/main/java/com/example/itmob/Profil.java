package com.example.itmob;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executor;

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
    private String vertragsnummer;


    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    Boolean kuendigungbereitsvorgemerkt = false;

    TextView textView_name, textView_email, textView_geburtsdatum, textView_startlaufzeit, textView_endlaufzeit, textView_preis;

    Button kuendigung, ausloggen, deleteAccount ;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        kuendigung = view.findViewById(R.id.kuendigungVormerken);

        ausloggen = view.findViewById(R.id.abmelden);

        deleteAccount = view.findViewById(R.id.deleteAccount);

        ausloggen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });



        showPopupMessage();


        HomeActivity activity = (HomeActivity) getActivity();
        String email = activity.getUsername();

        DBHelper db = new DBHelper(this.getContext());

        String finalEmail = email;

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("Konto löschen?")
                        .setMessage("Falls Sie Ihr Konto löschen möchten, \n\nklicken Sie auf Weiter um die Löschung durchzuführen.\n\nSie können sich jederzeit wieder registrieren.")
                        .setPositiveButton("Weiter", null)
                        .setNegativeButton("Abbrechen", null)
                        .show();

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        executor = ContextCompat.getMainExecutor(view.getContext());
                        biometricPrompt = new BiometricPrompt((FragmentActivity) view.getContext(),
                                executor, new BiometricPrompt.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationError(int errorCode,
                                                              @NonNull CharSequence errString) {
                                super.onAuthenticationError(errorCode, errString);
                                Toast.makeText(view.getContext(),
                                                "Authentifizierung fehlgeschlagen: " + errString, Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onAuthenticationSucceeded(
                                    @NonNull BiometricPrompt.AuthenticationResult result) {
                                super.onAuthenticationSucceeded(result);
                                dialog.dismiss();

                                db.deleteUser(finalEmail);
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(view.getContext(),
                                        "Konto wurde gelöscht!", Toast.LENGTH_SHORT).show();
                                activity.finish();
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                super.onAuthenticationFailed();
                                Toast.makeText(view.getContext(), "Authentifizierung fehlgeschlagen",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

                        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                                .setTitle("Fingerabrduck verwenden")
                                .setSubtitle("Verifikation wird benötigt um die Konto zu löschen.")
                                .setNegativeButtonText("PIN-Eingabe")
                                .build();

                        // Prompt appears when user clicks "Log in".
                        // Consider integrating with the keystore to unlock cryptographic operations,
                        // if needed by your app.

                        biometricPrompt.authenticate(promptInfo);



                    }
                });


            }
        });


        ArrayList<String> userdata = db.getUserData(email);
        startLaufzeit = userdata.get(0);
        endLaufzeit = userdata.get(1);
        preis = userdata.get(2);
        vorname = userdata.get(3);
        nachname = userdata.get(4);
        geburtsdatum = userdata.get(5);
        email = userdata.get(6);
        vertragsnummer = userdata.get(7);

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

        if(db.getKuendigungsStatus(vertragsnummer)){
            kuendigung.setText("Kündigung bereits vorgemerkt");
            kuendigung.setOnClickListener(null);
        }


        return view;
    }




    private void showPopupMessage() {
        kuendigung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("Kündigung vormerken")
                        .setMessage("Falls Sie planen Ihre Mitgliedschaft zu kündigen, können Sie Ihre Kündigung über unsere App vormerken. \n\nKlicken Sie auf Weiter um die Kündigung vorzumerken.")
                        .setPositiveButton("Weiter", null)
                        .setNegativeButton("Abbrechen", null)
                        .show();

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        executor = ContextCompat.getMainExecutor(view.getContext());
                        biometricPrompt = new BiometricPrompt((FragmentActivity) view.getContext(),
                                executor, new BiometricPrompt.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationError(int errorCode,
                                                              @NonNull CharSequence errString) {
                                super.onAuthenticationError(errorCode, errString);
                                Toast.makeText(view.getContext(),
                                                "Authentifizierung fehlgeschlagen: " + errString, Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onAuthenticationSucceeded(
                                    @NonNull BiometricPrompt.AuthenticationResult result) {
                                super.onAuthenticationSucceeded(result);
                                Toast.makeText(view.getContext(),
                                        "Authentifizierung erfolgreich!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                zeigeBestaetigungsPopUp(view);


                            }

                            @Override
                            public void onAuthenticationFailed() {
                                super.onAuthenticationFailed();
                                Toast.makeText(view.getContext(), "Authentifizierung fehlgeschlagen",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

                        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                                .setTitle("Fingerabrduck verwenden")
                                .setSubtitle("Verifikation wird benötigt um die Kündigung vorzumerken.")
                                .setNegativeButtonText("PIN-Eingabe")
                                .build();

                        // Prompt appears when user clicks "Log in".
                        // Consider integrating with the keystore to unlock cryptographic operations,
                        // if needed by your app.

                            biometricPrompt.authenticate(promptInfo);



                    }
                });

            }
        });
    }

    private void zeigeBestaetigungsPopUp(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

        builder1.setMessage("Hallo "+vorname+ "\n\nWir haben deine Vormerkung erhalten und melden uns innerhalb der nächsten 2-3 Tage postialisch bei dir. \n\n\nDein Fitnessstudio \nFutureFitness");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Alles Klar!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        DBHelper db = new DBHelper(this.getContext());
        db.updateKuendigungsstatus(vertragsnummer);

        boolean status = db.getKuendigungsStatus(vertragsnummer);
        kuendigungbereitsvorgemerkt = status;


    }


}