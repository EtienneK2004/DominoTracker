package com.example.dominotracker.model;

import java.sql.ResultSet;
import java.sql.Statement;

public class User {

    public int connexion(String pseudo, String mdp) {
        try {
            Fonctions fct = new Fonctions();
            Statement st = fct.connexionBDDSQL();

            String sqlQuery = "SELECT password, userid FROM User WHERE username = '" + pseudo + "'";


            ResultSet rs = st.executeQuery(sqlQuery);
            rs.next();
            if(rs.getString(1).equals(mdp)) {
                return rs.getInt(2);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
