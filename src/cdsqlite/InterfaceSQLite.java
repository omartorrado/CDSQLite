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

    private String url = "jdbc:sqlite:default.db";
    public Connection cn = null;

    /**
     *Constructor por defecto. La base de datos será default.db
     */
    public InterfaceSQLite() {

    }

    /**
     * Constructor al que se le pasa el nombre de la base de datos
     * @param s Nombre de la base de datos
     */
    public InterfaceSQLite(String s) {
        url = "jdbc:sqlite:" + s;
    }

    /**
     * Metodo que se usa para establecer la conexión con la base de datos 
     * indicada por el constructor
     * @return true si la conexion es correcta, false en caso contrario
     */
    public boolean conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            cn = DriverManager.getConnection(url);
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            return false;
        }
    }

    /**
     * Este metodo realiza insertar sencillas en una tabla Utilizo String...
     * para que se pueden realizar consultas con diferente numero de parametros
     * (campos). No permite establecer condiciones de busqueda, solo indicar los
     * campos a mostrar
     * @param tabla El nombre de la tabla en la que se realiza la busqueda
     * @param campo Lista de los campos a buscar, separados por comas
     * @return numero de resultados de la consulta
     */
    public int consultar(String tabla, String... campo) {
        int numEntries=0;
        try {
            String consulta = "select ";
            for (int i = 0; i < campo.length; i++) {
                if (i == (campo.length - 1)) {
                    consulta = consulta + campo[i] + " from " + tabla;
                } else {
                    consulta = consulta + campo[i] + ", ";
                }
            }
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            /*
            Aqui tenemos el problema de que no todas las tablas estan formadas por 
            strings. En este caso, si pillamos un int y puede hacer la conversion, la realiza
            correctamente, pero si no, pondria un 0. A la hora de crear la tabla ocurre
            lo mismo, le pasamos un numero como string a un campo declarado como integer
            y realiza la conversion al añadir el dato
             */
            while (rs.next()) {
                numEntries++;
                System.out.println(rs.getString(1) + " , " + rs.getString(2) + " , " + rs.getInt(3));
            }
            return numEntries;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Este metodo se usa para insertar nuevos valores en una tabla 
     * @param tabla nombre de la tabla donde se quiere hacer la inserción
     * @param values Valores de la nueva fila a insertar
     * @return true si la inserción es correcta, false si falla
     */
    public boolean inserta(String tabla, String... values) {
        try {
            String insertar = "insert into " + tabla + " values(";
            for (int i = 0; i < values.length; i++) {
                if (i == (values.length - 1)) {
                    insertar = insertar + "'" + values[i] + "')";
                } else {
                    insertar = insertar + "'" + values[i] + "', ";
                }
            }
            Statement st2 = cn.createStatement();
            st2.executeUpdate(insertar);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Este metodo se usa para eliminar una entrada de una tabla. Tenemos que 
     * pasarle los valores de TODOS los campos de dicha fila
     * @param tabla nombre de la tabla donde se quiere borrar una fila
     * @param values valores de la fila que queremos borrar
     * @return true si el borrado es correcto, false si falla
     */
    public boolean borrar(String tabla, String... values) {
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("pragma table_info(" + tabla + ")");
            String borrar = "delete from " + tabla + " where ";
            for (int i = 0; i < values.length; i++) {
                if (i == (values.length - 1)) {
                    rs.next();
                    borrar = borrar + rs.getString(2) + "='" + values[i] + "')";
                } else {
                    rs.next();
                    borrar = borrar +rs.getString(2) + "='" + values[i] + "', ";
                }
            }
            Statement st2 = cn.createStatement();
            st2.executeUpdate(borrar);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Este metodo se usa para modificar una fila de la tabla
     * @param tabla nombre de la tabla donde se quiere hacer la inserción
     * @param primaryKeyName Nombre del campo que hace de clave primaria. Se usa
     * para localizar la fila concreta a borrar
     * @param primaryKeyValue valor de la primary key
     * @param values nuevos valores a cambiar en la tabla. Se deben incluir todos
     * los campos de la tabla, aunque no se modifiquen
     * @return true si la modificación es correcta, false si falla
     */
    public boolean modificar(String tabla, String primaryKeyName, String primaryKeyValue, String... values) {
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("pragma table_info(" + tabla + ")");
            String modificar = "update " + tabla + " set ";
            for (int i = 0; i < values.length; i++) {
                if (i == (values.length - 1)) {
                    rs.next();
                    modificar = modificar + rs.getString(2) + "='" + values[i] + "')";
                } else {
                    rs.next();
                    modificar = modificar +rs.getString(2) + "='" + values[i] + "', ";
                }
            }
            modificar= modificar + "where "+primaryKeyName+"='"+primaryKeyValue+"'";
            Statement st2 = cn.createStatement();
            st2.executeUpdate(modificar);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
