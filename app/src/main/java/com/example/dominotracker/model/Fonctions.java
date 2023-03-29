package com.example.dominotracker.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Fonctions {

    private String ip = "192.168.1.15";

    private String port = "3306";

    private String nomBD = "domino_tracker";

    private String url = "jdbc:mysql://"+ ip + ":" + port + "/"+ nomBD;

    private static final String user = "monty";

    private static final String pass = "some_pass";

    private Connection conn;


    private void connectToBD() throws ClassNotFoundException, SQLException {
        if(conn == null){
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, user, pass);
        }
    }


    public Statement getStatement() {

        try {

            connectToBD();

            Statement st = conn.createStatement();



            return st;

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

            return null;

        } catch (SQLException e) {
            System.err.println("          ");
            e.printStackTrace();

            return null;

        }

        //conn.close();

    }

    public void inscription(String pseudo, String mdp, String email) throws InscriptionException {


        try {
            connectToBD();
            String sql = "SELECT userid from user where ?=?;";

            PreparedStatement testStatement = conn.prepareStatement(sql);
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
            PreparedStatement prepInsert = conn.prepareStatement(sqlInsert);
            prepInsert.setString(1, pseudo);
            prepInsert.setString(2, mdp);
            prepInsert.setString(3, email);
            prepInsert.executeUpdate();

        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
            throw new InscriptionException("Erreur avec la base de donnée");
        }
    }

    public int connectUser(String pseudo, String password) {

        try {

            connectToBD();
            if(pseudo.length()>16 || password.length()>100) return 0;

            String functionName = "connexion";
            String query = "SELECT " + functionName + "('" + pseudo + "','" + password + "')";

            Statement statement = getStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

            return -1;

        }


    }

    public void addEvent(int user, int type) {

        try {

            connectToBD();



            String call = "{call Ajout_Evenement(?, ?)}";
            CallableStatement statement = conn.prepareCall(call);
            statement.setInt(1, user);
            statement.setInt(2, type);
            statement.execute();

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

        }


    }



}

