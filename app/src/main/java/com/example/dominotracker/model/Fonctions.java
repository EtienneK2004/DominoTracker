package com.example.dominotracker.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class Fonctions {

    public String ip = "192.168.1.15";

    public String port = "3306";

    public String nomBD = "domino_tracker";

    public String url = "jdbc:mysql://"+ ip + ":" + port + "/"+ nomBD;

    public static final String user = "monty";

    public static final String pass = "some_pass";



    public Statement connexionBDDSQL() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, pass);

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



}

