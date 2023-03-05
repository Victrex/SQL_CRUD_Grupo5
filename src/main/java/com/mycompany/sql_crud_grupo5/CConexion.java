/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sql_crud_grupo5;

import java.awt.HeadlessException;
import java.sql.*;

import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class CConexion {

    String username = "root";
    String bd = "estudianteDB";
    String password = "";
    String url = "jdbc:sqlserver://localhost:1433;databaseName=estudianteDB;encrypt=true;trustServerCertificate=true";

    public Connection establecerConexion() {
        Connection conn = null;
        try {
            checkDB();
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(url, username, password);

            Statement stmt = conn.createStatement();

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "estudiantes", null);

            if (!rs.next()) {
                String sql = "CREATE TABLE estudiantes (id INT PRIMARY KEY, nombre VARCHAR(50), apellido VARCHAR(50), numCuenta VARCHAR(11), carrera VARCHAR(100), correo VARCHAR(100))";
                stmt.executeUpdate(sql);
            }
            JOptionPane.showMessageDialog(null, "Si se conecto a la base de datos");
        } catch (HeadlessException | ClassNotFoundException | SQLException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se conecto a la base de datos");
        }

        return conn;

    }

    public void checkDB() {

        String databaseName = "estudianteDB";
        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=root;password=;encrypt=true;trustServerCertificate=true";

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sys.databases WHERE name = '" + databaseName + "'");
            if (resultSet.next()) {
                System.out.println("Database " + databaseName + " exists.");
            } else {
                System.out.println("Database " + databaseName + " does not exist.");
                statement.executeUpdate("CREATE DATABASE " + databaseName);
                System.out.println("Database " + databaseName + " created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("prueba");
            String sql = "INSERT INTO estudiantes (id, nombre, apellido, numCuenta, carrera, correo) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "123");
            statement.setString(2, "kene");
            statement.setString(3, "caca");
            statement.setString(4, "202010000");
            statement.setString(5, "sistemas");
            statement.setString(6, "correo@gmail.com");

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Object[][] imprimir() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("imprimir");
            String sql = "SELECT * FROM estudiantes";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int count = 0;
            Object [][] array=new Object[100][6];
            int j = 0;
            while (result.next()) {
                // (id, nombre, apellido, numCuenta, carrera, correo
                array[j][0] = result.getString(1);
                array[j][1] = result.getString(2);
                array[j][2] = result.getString(3);
                array[j][3] = result.getString(4);
                array[j][4] = result.getString(5);
                array[j][5] = result.getString(6);
                j++;
                 
                 
                
                //String output = "%s - %s - %s - %s - %s - %s";
                //System.out.println(String.format(output, id, nombre, apellido, numCuenta, carrera, correo));
                
            }
            return array;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
