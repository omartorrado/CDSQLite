/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdsqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author otorradomiguez
 */
public class InterfaceSQLite {
    
    private String url="jdbc:sqlite:default.db";
    private Connection cn=null;

    public InterfaceSQLite() {
        
    }
    
    public InterfaceSQLite(String s) {
        url="jdbc:sqlite:"+s;
    }
    
    public boolean conectar(){
        try{
            Class.forName("org.sqlite.JDBC");
            cn=DriverManager.getConnection(url);
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            return false;
        }
    }
    
    public void insertar(){
        
    }
    
    public void borrar(){
        
    }
    
    public void modificar(){
        
    }
}
