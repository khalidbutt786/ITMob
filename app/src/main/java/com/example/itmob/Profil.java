package com.example.itmob;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
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


    int SELECT_IMAGE_CODE = 1;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    Boolean kuendigungbereitsvorgemerkt = false;

    TextView textView_email, textView_name;
    private String path;

    ShapeableImageView photo_profil;


    LinearLayout vertragsdaten, kontakt, ausloggen, impressum, datenschutz, agb, deleteAccount;

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


        HomeActivity activity = (HomeActivity) getActivity();
        String email = activity.getUsername();

        DBHelper db = new DBHelper(this.getContext());

        String finalEmail = email;

        vertragsdaten = view.findViewById(R.id.vertragsdaten_btn);
        vertragsdaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new VertragsdatenFragment(), "FRGVertragsdaten").addToBackStack(null).commit();
            }
        });

        ausloggen = view.findViewById(R.id.ausloggen_btn);

        ausloggen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zeigeBestaetigungsPopUpAusloggen(view);
            }
        });


        kontakt = view.findViewById(R.id.kontakt_btn);
        kontakt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new KontaktFragment()).addToBackStack(null).commit();


            }
        });

        impressum = view.findViewById(R.id.impressum_btn);
        impressum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ImpressumFragment()).addToBackStack(null).commit();
            }
        });

        datenschutz = view.findViewById(R.id.datenschutz_btn);
        datenschutz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new DatenschutzFragment()).addToBackStack(null).commit();
            }
        });

        agb = view.findViewById(R.id.agb_btn);
        agb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AGBFragment()).addToBackStack(null).commit();
            }
        });

        deleteAccount = view.findViewById(R.id.konto_loeschen_btn);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kontoloeschen(activity,db, finalEmail);
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

        textView_email = view.findViewById(R.id.email_label);
        textView_email.setText(email);

        textView_name = view.findViewById(R.id.name_label);
        textView_name.setText(vorname+" "+nachname);

        photo_profil = view.findViewById(R.id.photo_profil);





        String finalEmail1 = email;
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            photo_profil.setImageURI(uri);
                            db.updateImagePath(finalEmail1, uri.getPath());
                        }
                    }
                });

        photo_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                someActivityResultLauncher.launch(intent);


            }
        });


        return view;
    }


    private void kontoloeschen(HomeActivity activity, DBHelper db, String finalEmail) {
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
    }

    private void zeigeBestaetigungsPopUpAusloggen(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());

        builder1.setMessage("Abmelden?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ja",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent1);
                        getActivity().finish();

                    }
                });

        builder1.setNegativeButton(
                "Nein",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}