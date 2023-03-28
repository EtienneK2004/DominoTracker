package com.example.dominotracker.model;

import java.sql.ResultSet;
import java.sql.Statement;

public class User {

    public int connexion(String pseudo, String mdp) {
        return new Fonctions().connectUser(pseudo, mdp);
    }
}
