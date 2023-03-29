package com.example.dominotracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dominotracker.R;
import com.example.dominotracker.model.InscriptionException;
import com.example.dominotracker.model.User;

public class InscriptionActivity extends AppCompatActivity {

    private static Button btn_send, btn_connexion;
    private static EditText pseudo, mdp, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        User userBD = new User();
        pseudo = (EditText) findViewById(R.id.pseudo);
        mdp = (EditText) findViewById(R.id.mdp);
        email = (EditText) findViewById(R.id.email);

        btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    Log.i("pseudo", pseudo.getText().toString());
                    userBD.inscription(pseudo.getText().toString(), mdp.getText().toString(), email.getText().toString());
                    int result = userBD.connexion(pseudo.getText().toString(), mdp.getText().toString());
                    NextActivity(result);
                }catch(InscriptionException e){
                    popup(e.getMessage());
                }

            }
        });

        btn_connexion = (Button) findViewById(R.id.btn_connexion);

        btn_connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                gotoConnexion();
            }
        });


        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    private void gotoConnexion() {
        Intent intent = new Intent(InscriptionActivity.this, ConnexionActivity.class);
        startActivity(intent);
        finish();
    }

    private void popup(String message) {
        // AlertDialog pour informer l'utilisateur
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erreur d'inscription");
        builder.setMessage(message);


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void NextActivity(int userid) {
        Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
        intent.putExtra("EXTRA_USER", userid);
        startActivity(intent);
        finish();
    }
}