package com.example.dominotracker.model;

import android.util.Log;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Calendrier {

    public ArrayList<String> getCategories(){
        ArrayList<String> list = new ArrayList<>();


        try {
            Fonctions fct = new Fonctions();
            Statement st = fct.connexionBDDSQL();

            String sqlQuery = "SELECT name FROM actioncat;";


            ResultSet rs = st.executeQuery(sqlQuery);
            while(rs.next()){
                String cat = rs.getString(1);
                list.add(cat);
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addEvent(String cat, int user){
        try {
            Fonctions fct = new Fonctions();
            Statement st = fct.connexionBDDSQL();

            String sqlInsert = "insert into historique(category, user) VALUES("+
                    getCatId(cat)+
                    ","+
                    user+
                    ");";

            synchronized (Calendrier.class) {
                st.executeUpdate(sqlInsert);
            }
            Log.i("aaa", "insert");



        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Event> filterToDate(ArrayList<Event> events, LocalDate date){
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.getDate().isEqual(date)) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }

    public ArrayList<Event> getEvents(){
        ArrayList<Event> eventList = new ArrayList<>();


        try {
            Fonctions fct = new Fonctions();
            Statement st = fct.connexionBDDSQL();

            String sqlQuery = "SELECT H.time, U.username, C.name FROM historique H, user U, actioncat C WHERE H.user=U.userid AND H.category=C.category_id";

            synchronized (Calendrier.class) {
                ResultSet rs = st.executeQuery(sqlQuery);

                SimpleDateFormat heure = new SimpleDateFormat("HH:mm");
                SimpleDateFormat jour = new SimpleDateFormat("yyyy-MM-dd");
                while (rs.next()) {
                    Timestamp time = rs.getTimestamp(1);
                    String user = rs.getString(2);
                    String cat = rs.getString(3);
                    eventList.add(new Event(
                            cat,
                            heure.format(time),
                            "Par " + user,
                            LocalDate.parse(jour.format(time)))
                    );
                }
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

        EventTimeComparator comparator = new EventTimeComparator();
        Collections.sort(eventList, comparator);
        return eventList;
    }

    private class EventTimeComparator implements Comparator<Event> {
        @Override
        public int compare(Event event1, Event event2) {
            String time1 = event1.getEventTime();
            String time2 = event2.getEventTime();
            return time1.compareTo(time2);
        }
    }

    private int getCatId(String name){
        int i = 0;
        try {
            Fonctions fct = new Fonctions();
            Statement st = fct.connexionBDDSQL();

            String sqlQuery = "SELECT category_id FROM actioncat WHERE name='"+name+"';";

            synchronized (Calendrier.class) {
                ResultSet rs = st.executeQuery(sqlQuery);
                rs.next();
                i = rs.getInt(1);
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
