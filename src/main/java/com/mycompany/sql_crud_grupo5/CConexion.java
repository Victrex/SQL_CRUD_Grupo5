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
    String bd ="estudianteDB";
    String password = "";
    String url = "jdbc:sqlserver://localhost:1433;databaseName=estudianteDB;encrypt=true;trustServerCertificate=true";

    public Connection establecerConexion(){
        Connection conn = null;
        try {
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
            JOptionPane.showMessageDialog(null,"Si se conecto a la base de datos");
        } catch (HeadlessException | ClassNotFoundException | SQLException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(null,"No se conecto a la base de datos");
        }
        
        return conn;

    }
           
}
