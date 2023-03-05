/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sql_crud_grupo5;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Grupo5
 */
public class Estudiantes {

    public void crearTabla() {
        CConexion objC = new CConexion();
        String name = JOptionPane.showInputDialog("Ingrese el nombre de la nueva tabla");
        Statement st;
        try {
            st = objC.conexion().createStatement();
            String sql = "CREATE TABLE " + name + "(id INT PRIMARY KEY, nombre VARCHAR(50), apellido VARCHAR(50), numCuenta VARCHAR(11), carrera VARCHAR(100), correo VARCHAR(100))";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"tabla creada con exito");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }

    }

    public String seleccionarTabla() {
        CConexion objC = new CConexion();
        ArrayList<String> tables = new ArrayList<String>();
//        String sql = "";
//        sql = "SHOW TABLES;";
//        Statement st;
        try {
//            st = objC.conexion().createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            String datos[] = new String[20];
//            while (rs.next()) {
//                for (int i = 0; i <= datos.length - 1; i++) {
//                    datos[i] = rs.getString(i + 1);
//                    System.out.println(datos[i]);
//                }
//            }
            Connection conn = null;
            conn = objC.conexion();
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables("estudianteDB", null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if (!tableName.contains("trace")) {
                    tables.add(tableName);
                }

            }
            Object[] elementos = tables.toArray();
            String elementoSeleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione un elemento:", "Lista de elementos", JOptionPane.PLAIN_MESSAGE, null, elementos, elementos[0]);
            return elementoSeleccionado;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
        return null;
    }

    /**
     * Metodo para mostrar los valores de cada registro en la tabla cuando se
     * inicia el programa, si es que existen.
     *
     * @param tblEstudiantes : JTable
     */
    public void mostrarEstudiantes(JTable tblEstudiantes, JLabel jLabel7, String seleccion) {
        List<JTextField> textFields = new ArrayList<JTextField>();
        CConexion objC = new CConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        String sql = "";
        sql = "SELECT * FROM " + seleccion + ";";
        Statement st;
        try {
            st = objC.conexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            String datos[] = new String[columnsNumber];

            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) {
                }
                modelo.addColumn(rsmd.getColumnName(i));
                //
                //
            }
            while (rs.next()) {
                for (int i = 0; i <= datos.length - 1; i++) {
                    datos[i] = rs.getString(i + 1);
                }
                modelo.addRow(datos);
            }
            tblEstudiantes.setModel(modelo);
            jLabel7.setText(seleccion);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }

    /**
     * Metodo que sirve para llenar los campos de tipo JTextField con sus
     * respectivos valores en la tabla.
     *
     * @param tblEstudiante : JTable
     * @param id : JTextField
     * @param nombre : JTextField
     * @param apellido : JTextField
     * @param numC : JTextField
     * @param carrera : JTextField
     * @param correo : JTextField
     */
    public void seleccionarEstudiante(JTable tblEstudiante, JTextField id, JTextField nombre, JTextField apellido,
            JTextField numC, JTextField carrera, JTextField correo) {
        try {
            int filaSelec = tblEstudiante.getSelectedRow();
            if (filaSelec >= 0) {
                id.setText(tblEstudiante.getValueAt(filaSelec, 0).toString());
                nombre.setText(tblEstudiante.getValueAt(filaSelec, 1).toString());
                apellido.setText(tblEstudiante.getValueAt(filaSelec, 2).toString());
                numC.setText(tblEstudiante.getValueAt(filaSelec, 3).toString());
                carrera.setText(tblEstudiante.getValueAt(filaSelec, 4).toString());
                correo.setText(tblEstudiante.getValueAt(filaSelec, 5).toString());
            } else {
                JOptionPane.showMessageDialog(null, "Seleccionar un registro!!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }

    /**
     * Metodo para guardar valores en la tabla.
     *
     * @param id : JTextField
     * @param nombre : JTextField
     * @param apellido : JTextField
     * @param numC : JTextField
     * @param carrera : JTextField
     * @param correo : JTextField
     */
    public void guardarEstudiantes(JTextField id, JTextField nombre, JTextField apellido,
            JTextField numC, JTextField carrera, JTextField correo) {
        CConexion objC = new CConexion();
        String sql = "INSERT INTO estudiantes (id,nombre,apellido,numCuenta,carrera,correo) VALUES(?,?,?,?,?,?);";

        try {
            CallableStatement callSt = objC.conexion().prepareCall(sql);
            callSt.setString(1, id.getText());
            callSt.setString(2, nombre.getText());
            callSt.setString(3, apellido.getText());
            callSt.setString(4, numC.getText());
            callSt.setString(5, carrera.getText());
            callSt.setString(6, correo.getText());
            callSt.execute();

            JOptionPane.showMessageDialog(null, "Valores insertados correctamente!!");
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            JOptionPane.showMessageDialog(null, "El ID que intenta ingresar ya existe.");
        }
    }

    /**
     * Metodo para modificar valores de la tabla
     *
     * @param id : JTextField
     * @param nombre : JTextField
     * @param apellido : JTextField
     * @param numC : JTextField
     * @param carrera : JTextField
     * @param correo : JTextField
     */
    public void modificarEstudiante(JTextField id, JTextField nombre, JTextField apellido,
            JTextField numC, JTextField carrera, JTextField correo) {
        CConexion objC = new CConexion();
        String sql = "UPDATE estudiantes SET estudiantes.id=" + id.getText() + ",estudiantes.nombre=?,estudiantes.apellido=?,"
                + "estudiantes.numCuenta=?,estudiantes.carrera=?,estudiantes.correo=? WHERE estudiantes.id=?;";
        try {
            CallableStatement callSt = objC.conexion().prepareCall(sql);
            //callSt.setString(1,id.getText());
            callSt.setString(1, nombre.getText());
            callSt.setString(2, apellido.getText());
            callSt.setString(3, numC.getText());
            callSt.setString(4, carrera.getText());
            callSt.setString(5, correo.getText());
            callSt.setString(6, id.getText());
            callSt.execute();

            JOptionPane.showMessageDialog(null, "Valores modificados correctamente!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }

    /**
     * Metodo para eliminar un registro de la tabla.
     *
     * @param id : JTextField
     */
    public void eliminarEstudiante(JTextField id) {
        CConexion objC = new CConexion();
        String sql = "DELETE FROM estudiantes WHERE estudiantes.id=?";

        try {
            CallableStatement callSt = objC.conexion().prepareCall(sql);
            callSt.setString(1, id.getText());
            callSt.execute();

            JOptionPane.showMessageDialog(null, "Valores eliminados correctamente!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }
}
