/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sql_crud_grupo5;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Grupo5
 */
public class Estudiantes {
    /**
     * Metodo para mostrar los valores de cada registro en la tabla cuando se inicia el programa, si es que existen.
     * @param tblEstudiantes : JTable
     */
    public void mostrarEstudiantes(JTable tblEstudiantes){
        CConexion objC=new CConexion();
        DefaultTableModel modelo=new DefaultTableModel();
        String sql="";
        
        modelo.addColumn("id");
        modelo.addColumn("nombre");
        modelo.addColumn("apellido");
        modelo.addColumn("numCuenta");
        modelo.addColumn("carrera");
        modelo.addColumn("correo");        
        tblEstudiantes.setModel(modelo);
        
        sql="SELECT * FROM estudiantes;";
        String datos[]=new String[6];
        Statement st;
        
        try {
            st=objC.conexion().createStatement();
            ResultSet rs=st.executeQuery(sql);
            
            while(rs.next()){
                for (int i=0; i<=datos.length-1; i++) {
                    datos[i]=rs.getString(i+1);
                }
                modelo.addRow(datos);
            }
            tblEstudiantes.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.toString());
        }
    }
    
    /**
     * Metodo que sirve para llenar los campos de tipo JTextField con sus respectivos valores en la tabla.
     * @param tblEstudiante : JTable
     * @param id : JTextField
     * @param nombre : JTextField
     * @param apellido : JTextField
     * @param numC : JTextField
     * @param carrera : JTextField
     * @param correo : JTextField
     */
    public void seleccionarEstudiante(JTable tblEstudiante,JTextField id,JTextField nombre,JTextField apellido,
                                        JTextField numC,JTextField carrera,JTextField correo){
        try {
            int filaSelec=tblEstudiante.getSelectedRow();
            if(filaSelec>=0){
                id.setText(tblEstudiante.getValueAt(filaSelec, 0).toString());
                nombre.setText(tblEstudiante.getValueAt(filaSelec, 1).toString());
                apellido.setText(tblEstudiante.getValueAt(filaSelec, 2).toString());
                numC.setText(tblEstudiante.getValueAt(filaSelec, 3).toString());
                carrera.setText(tblEstudiante.getValueAt(filaSelec, 4).toString());
                correo.setText(tblEstudiante.getValueAt(filaSelec, 5).toString());
            }
            else{
                JOptionPane.showMessageDialog(null,"Seleccionar un registro!!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.toString());
        }
    }
    
    /**
     * Metodo para guardar valores en la tabla.
     * @param id : JTextField
     * @param nombre : JTextField
     * @param apellido : JTextField
     * @param numC : JTextField
     * @param carrera : JTextField
     * @param correo : JTextField
     */
    public void guardarEstudiantes(JTextField id,JTextField nombre,JTextField apellido,
                                        JTextField numC,JTextField carrera,JTextField correo){
        CConexion objC=new CConexion();
        String sql="INSERT INTO estudiantes (id,nombre,apellido,numCuenta,carrera,correo) VALUES(?,?,?,?,?,?);";
        
        try {
            CallableStatement callSt=objC.conexion().prepareCall(sql);
            callSt.setString(1,id.getText());
            callSt.setString(2,nombre.getText());
            callSt.setString(3,apellido.getText());
            callSt.setString(4,numC.getText());
            callSt.setString(5,carrera.getText());
            callSt.setString(6,correo.getText());
            callSt.execute();
            
            JOptionPane.showMessageDialog(null,"Valores insertados correctamente!!");
        } catch (Exception e) {
            System.out.println("Error: "+e.toString());
            JOptionPane.showMessageDialog(null,"El ID que intenta ingresar ya existe.");
        }
    }
    
    /**
     * Metodo para modificar valores de la tabla
     * @param id : JTextField
     * @param nombre : JTextField
     * @param apellido : JTextField
     * @param numC : JTextField
     * @param carrera : JTextField
     * @param correo : JTextField
     */
    public void modificarEstudiante(JTextField id,JTextField nombre,JTextField apellido,
                                        JTextField numC,JTextField carrera,JTextField correo){
        CConexion objC=new CConexion();
        String sql="UPDATE estudiantes SET estudiantes.id="+id.getText()+",estudiantes.nombre=?,estudiantes.apellido=?,"
                + "estudiantes.numCuenta=?,estudiantes.carrera=?,estudiantes.correo=? WHERE estudiantes.id=?;";
        try {
            CallableStatement callSt=objC.conexion().prepareCall(sql);
            //callSt.setString(1,id.getText());
            callSt.setString(1,nombre.getText());
            callSt.setString(2,apellido.getText());
            callSt.setString(3,numC.getText());
            callSt.setString(4,carrera.getText());
            callSt.setString(5,correo.getText());
            callSt.setString(6,id.getText());
            callSt.execute();
            
            JOptionPane.showMessageDialog(null,"Valores modificados correctamente!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.toString());
        }
    }
    
    /**
     * Metodo para eliminar un registro de la tabla.
     * @param id : JTextField
     */
    public void eliminarEstudiante(JTextField id){
        CConexion objC=new CConexion();
        String sql="DELETE FROM estudiantes WHERE estudiantes.id=?";
        
        try {
            CallableStatement callSt=objC.conexion().prepareCall(sql);
            callSt.setString(1,id.getText());
            callSt.execute();
            
            JOptionPane.showMessageDialog(null,"Valores eliminados correctamente!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.toString());
        }
    }
}
