package com.example.dominotracker.activities.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.dominotracker.model.events.Calendrier;
import com.example.dominotracker.model.events.Event;
import com.example.dominotracker.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {
    private ArrayList<Event> eventList = new ArrayList<>();
    private DatePickerDialog datePickerDialog;
    private LocalDate selectedDate = LocalDate.now();

    private Calendrier calendrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.calendrier = new Calendrier();
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.event_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventAdapter adapter = new EventAdapter(eventList);
        recyclerView.setAdapter(adapter);

        // Ajouter des événements à la liste des événements :
        eventList = calendrier.getEvents();



        // Créer un DatePickerDialog pour permettre à l'utilisateur de sélectionner une date
        datePickerDialog = new DatePickerDialog(EventsActivity.this, (view, year, monthOfYear, dayOfMonth) -> {



            //monthOfYear est entre 0 et 11, or il faut entre 1 et 12
            selectedDate = LocalDate.of(year, monthOfYear+1, dayOfMonth);


            // Afficher la liste des événements filtrés
            filterAndUpdate(adapter, selectedDate);

        }, selectedDate.getYear(), selectedDate.getMonthValue()+1, selectedDate.getDayOfMonth());

        filterAndUpdate(adapter, selectedDate);

        Button btnSelectDate = findViewById(R.id.btn_select_date);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ouvrir le DatePickerDialog
                datePickerDialog.show();
            }
        });

        Button previousDayButton = findViewById(R.id.previous_day_button);
        previousDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.minusDays(1); // Reculer d'un jour
                filterAndUpdate(adapter, selectedDate);
            }
        });

        Button nextDayButton = findViewById(R.id.next_day_button);
        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.plusDays(1); // Reculer d'un jour
                filterAndUpdate(adapter, selectedDate);
            }
        });
        updateSelectedDateText();

        Button ajoutButton = findViewById(R.id.btn_insert);
        ajoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEventActivity();
                filterAndUpdate(adapter, selectedDate);

            }
        });



    }

    private void addEventActivity() {
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("EXTRA_USER", getIntent().getIntExtra("EXTRA_USER", 0));

        // Lancement de l'activité
        startActivity(intent);
        finish();
    }

    private void updateSelectedDateText() {
        Button dateSelect = findViewById(R.id.btn_select_date);
        dateSelect.setText(selectedDate.toString());
        datePickerDialog.updateDate(selectedDate.getYear(), selectedDate.getMonthValue()-1, selectedDate.getDayOfMonth());
    }

    private void filterAndUpdate(EventAdapter adapt, LocalDate selectedDate){
        updateSelectedDateText();
        adapt.setEventList(calendrier.filterToDate(eventList, selectedDate));
    }


}