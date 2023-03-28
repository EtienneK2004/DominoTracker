package com.example.dominotracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.dominotracker.model.Calendrier;
import com.example.dominotracker.R;

public class AddEventActivity extends AppCompatActivity {

    private String selectedCategory;
    private Spinner spinner;
    private Button btnValider;
    private Calendrier calendrier;

    private int user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        this.calendrier = new Calendrier();

        Intent intent = getIntent();
        user = intent.getIntExtra("EXTRA_USER", 0);

        //Remplissage du spinner
        spinner = findViewById(R.id.spinner_choices);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, calendrier.getCategories());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnValider = findViewById(R.id.btn_valider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSelectedEvent();

            }
        });

        Button btnAnnuler = findViewById(R.id.btn_annuler);
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAct();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                btnValider.setVisibility(View.VISIBLE);
                String selectedOption = adapterView.getItemAtPosition(position).toString();
                selectedCategory = selectedOption;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                btnValider.setVisibility(View.GONE);
                selectedCategory = "";
            }
        });


    }

    @Override
    public void onBackPressed(){
        finishAct();
    }

    private void finishAct() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EXTRA_USER", getIntent().getIntExtra("EXTRA_USER", 0));

        // Lancement de l'activité
        startActivity(intent);
        finish();
    }

    private void addSelectedEvent(){
        AddEventTask task = new AddEventTask(this);
        task.execute();
    }

    private class AddEventTask extends AsyncTask<Void, Void, Void> {

        ProgressBar progressBar;
        Context context;

        AddEventTask(Context c){
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            ((Activity) context).addContentView(progressBar, params);
        }

        @Override
        protected Void doInBackground(Void... params) {
            calendrier.addEvent(selectedCategory, user);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            finishAct();
        }
    }





}