package com.example.dominotracker.model.connexion;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionBD {

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

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

            return null;

        }

    }

    public PreparedStatement getPreparedStatement(String sql) {

        try {

            connectToBD();

            PreparedStatement ps = conn.prepareStatement(sql);



            return ps;

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();

            return null;

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

