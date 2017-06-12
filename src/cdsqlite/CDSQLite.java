/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdsqlite;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author otorradomiguez
 */
public class CDSQLite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InterfaceSQLite miDB= new InterfaceSQLite();
        miDB.conectar();
        try {
            Statement st=miDB.cn.createStatement();
            st.executeUpdate("drop table prueba");
            st.executeUpdate("create table prueba(nombre string, apellido varchar, id integer, primary key(id))");
            miDB.inserta("prueba", "Paco","Garcia","71");
            miDB.inserta("prueba", "Miguel", "Lopez","666");
        } catch (SQLException ex) {
            Logger.getLogger(CDSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("");
        miDB.consultar("prueba", "nombre","apellido","id");
        System.out.println(miDB.modificar("prueba", "id", "71", "Pedro", "Garcia", "7777"));
        miDB.consultar("prueba", "nombre","apellido","id");
        System.out.println(miDB.borrar("prueba", "Pedro", "Garcia", "7777"));
        System.out.println(miDB.consultar("prueba", "nombre","apellido","id"));
        
    }
    
}
