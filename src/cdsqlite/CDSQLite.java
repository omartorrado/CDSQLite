/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdsqlite;

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
        System.out.println(miDB.conectar());
    }
    
}
