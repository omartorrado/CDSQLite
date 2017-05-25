/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdsqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author otorradomiguez
 */
public class InterfaceSQLite {
    
    private String url="jdbc:sqlite:default.db";
    public Connection cn=null;

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
    
    /**
     * Este metodo realiza insertar sencillas en una tabla
 Utilizo String... para que se pueden realizar consultas con diferente
 numero de parametros (campos)
     * @param tabla El nombre de la tabla en la que se realiza la busqueda
     * @param campo Lista de los campos a buscar, separados por comas
     * @return 
     */
    public boolean consultar(String tabla, String... campo){
        try{
            String consulta="select ";
            for(int i=0; i<campo.length;i++){
                if(i==(campo.length-1)){
                 consulta=consulta+campo[i]+" from "+tabla;   
                }else{
                consulta=consulta+campo[i]+", ";
                }
            }
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(consulta);
            /*
            Aqui tenemos el problema de que no todas las tablas estan formadas por 
            strings. En este caso, si pillamos un int y puede hacer la conversion, la realiza
            correctamente, pero si no, pondria un 0. A la hora de crear la tabla ocurre
            lo mismo, le pasamos un numero como string a un campo declarado como integer
            y realiza la conversion al añadir el dato
            */
            while(rs.next()){
                System.out.println(rs.getString(1)+" , "+rs.getString(2)+" , "+rs.getInt(3));
            }
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    
    public boolean insertar(String tabla, String... values){
        try{
            String insertar="insert into "+tabla+" values(";
            for(int i=0; i<values.length;i++){
                if(i==(values.length-1)){
                 insertar=insertar+"'"+values[i]+"')";   
                }else{
                insertar=insertar+"'"+values[i]+"', ";
                }
            }
            Statement st=cn.createStatement();
            st.executeQuery(insertar);
            return true;
        }catch(SQLException e){
            return false;
        }
    }
    
    public void borrar(){
        
    }
    
    public void modificar(){
        
    }
}
