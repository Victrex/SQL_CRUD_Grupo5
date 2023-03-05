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
 * @author Grupo5
 */
public class CConexion {
    String username = "root";
    String bd = "estudianteDB";
    String password = "";
    String url = "jdbc:sqlserver://localhost:1433;databaseName=estudianteDB;encrypt=true;trustServerCertificate=true";
    
    /**
     * Metodo que crea la conexion con la base de datos.
     * Si la base de datos o la tabla respectiva no ha sido creada, se crearan automaticamente.
     * @return conn : Connection
     */
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
                String nombreTabla = JOptionPane.showInputDialog("No hay tablas existentes \n Ingrese el nombre de una nueva tabla");
                if(nombreTabla != null){
                    String sql = "CREATE TABLE "+nombreTabla+" (id INT PRIMARY KEY, nombre VARCHAR(50), apellido VARCHAR(50), numCuenta VARCHAR(11), carrera VARCHAR(100), correo VARCHAR(100))";
                stmt.executeUpdate(sql);
                }else{
                    JOptionPane.showMessageDialog(null,"No hay tablas disponibles a las que se pueda acceder");
                    System.exit(0);
                }
            }
            JOptionPane.showMessageDialog(null, "Conexion exitosa a la BD");
        } catch (HeadlessException | ClassNotFoundException | SQLException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se conecto a la base de datos");
            System.exit(1);
        }

        return conn;
    }    
    public Connection conexion() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.toString());
        }
        
        return conn;
    }

    /**
     * Metodo que crea la base de datos, en caso de que no exista.
     */
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
}
