package com.example.dominotracker.model.user;

import com.example.dominotracker.model.connexion.ConnexionBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    public int connexion(String pseudo, String mdp) {
        return new ConnexionBD().connectUser(pseudo, mdp);
    }

    public void inscription(String pseudo, String mdp, String email) throws InscriptionException {
        if(pseudo.equals("") || mdp.equals("") || email.equals("") || pseudo == null || mdp == null || email == null)
            throw new InscriptionException("Veuillez remplir tous les champs");

        try {
            String sql = "SELECT userid from user where ?=?;";

            PreparedStatement testStatement = new ConnexionBD().getPreparedStatement(sql);
            testStatement.setString(1, "username");
            testStatement.setString(2, pseudo);
            //Test username déjà pris

            ResultSet resultUsername = testStatement.executeQuery();
            if (resultUsername.next()) {
                throw new InscriptionException("Nom d'utilisateur déjà pris");
            }

            //Test email déjà pris
            testStatement.setString(1, "email");
            testStatement.setString(2, email);
            ResultSet resultEmail = testStatement.executeQuery();
            if (resultEmail.next()) {
                throw new InscriptionException("Email déjà utilisé");
            }

            //Insertion
            String sqlInsert = "INSERT INTO user(username, password, email) VALUES(?, SHA2(?, 256), ?);";
            PreparedStatement prepInsert = new ConnexionBD().getPreparedStatement(sqlInsert);
            prepInsert.setString(1, pseudo);
            prepInsert.setString(2, mdp);
            prepInsert.setString(3, email);
            prepInsert.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
            throw new InscriptionException("Erreur avec la base de donnée");
        }
    }


}
