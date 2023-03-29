package com.example.dominotracker.model;

import java.sql.ResultSet;
import java.sql.Statement;

public class User {

    public int connexion(String pseudo, String mdp) {
        return new Fonctions().connectUser(pseudo, mdp);
    }

    public void inscription(String pseudo, String mdp, String email) throws InscriptionException{
        if(pseudo.equals("") || mdp.equals("") || email.equals("") || pseudo == null || mdp == null || email == null)
            throw new InscriptionException("Veuillez remplir tous les champs");
        new Fonctions().inscription(pseudo, mdp, email);
    }


}
