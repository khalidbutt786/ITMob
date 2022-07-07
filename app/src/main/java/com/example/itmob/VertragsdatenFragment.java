package com.example.itmob;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VertragsdatenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VertragsdatenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LinearLayout kuendigung;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VertragsdatenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VertragsdatenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VertragsdatenFragment newInstance(String param1, String param2) {
        VertragsdatenFragment fragment = new VertragsdatenFragment();
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
        View view = inflater.inflate(R.layout.fragment_vertragsdaten, container, false);

        kuendigung = view.findViewById(R.id.kuendigungVormerken_btn);
        showPopupMessage();

        // Inflate the layout for this fragment
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

        builder1.setMessage("Hallo "+ "\n\nWir haben deine Vormerkung erhalten und melden uns innerhalb der nächsten 2-3 Tage postialisch bei dir. \n\n\nDein Fitnessstudio \nFutureFitness");
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
       // db.updateKuendigungsstatus(vertragsnummer);

      //  boolean status = db.getKuendigungsStatus(vertragsnummer);
     //   kuendigungbereitsvorgemerkt = status;


    }

}