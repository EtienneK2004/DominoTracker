package com.example.dominotracker.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.dominotracker.R;
import com.example.dominotracker.model.User;

public class ConnexionActivity extends AppCompatActivity {

    private static Button btn_send;
    private static EditText pseudo, mdp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        User userBD = new User();
        pseudo = (EditText) findViewById(R.id.pseudo);
        mdp = (EditText) findViewById(R.id.mdp);

        btn_send = (Button) findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int result = userBD.connexion(pseudo.getText().toString(), mdp.getText().toString());
                if(result==0){
                    popupMDP();
                }
                else{
                    NextActivity(result);
                }
            }
        });

        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    // Lorsque l'utilisateur rentre un mauvais MDP
    private void popupMDP() {
        // AlertDialog pour informer l'utilisateur
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erreur de connexion");
        builder.setMessage("Nom d'utilisateur ou mot de passe incorrect.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void NextActivity(int userid) {
        Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
        intent.putExtra("EXTRA_USER", userid);
        startActivity(intent);
        finish();
    }
}