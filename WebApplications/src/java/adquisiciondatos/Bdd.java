/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adquisiciondatos;

/**
 *
 * @author Alberto
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase bdd se usa para asistir la administracion de origenes de datos
 * mediantes instrucciones sql
 */
public class Bdd {

    private ResultSet resultado;
    private Connection conexion = null;
    

    /**
     * Construye conecciones con origenes de datos ODBC
     */
    public Bdd(String origenDatos) {

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println("controlador JDBC-ODBC cargado");
            conexion = DriverManager.getConnection("jdbc:odbc:" + origenDatos);
            System.out.println("conexion con " + origenDatos + " establecida");
        } catch (ClassNotFoundException e) {
            System.out.println("No se pudo cargar el controlador JDBC-ODBC");
            System.out.println(e.toString());
            //System.out.println(e.toString);
        } catch (SQLException e) {
            System.out.println("error de conexion con " + origenDatos);
        }
    }


    // Construye conecciones con origenes de datos ODBC
    public Bdd(String origenDatos, String login, String password) {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println("controlador JDBC-ODBC cargado");
            conexion = DriverManager.getConnection("jdbc:odbc:" + origenDatos, login, password);
            
            System.out.println("conexion con " + origenDatos + " establecida");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }    
    
     // Esta es para conexiones directas a mysql
    public Bdd(String servidor, String nDB, String login, String password) {
        try {
            //Class.forName("com.mysql.jdbc.Driver"); // para version 5 del driver de mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            // el complemento es opcional
            String complemento="?useUnicode=true&use"+"JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&"+"serverTimezone=UTC";
            //conexion=DriverManager.getConnection("jdbc:mysql://"+servidor+":3306/"+nDB, login, password);
            conexion=DriverManager.getConnection("jdbc:mysql://"+servidor+":3306/"+nDB+complemento, login, password);
                // jdbc:mysql://localhost:3306/simulacion
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
    
        public Connection getConexion() {
        return conexion;
    }
    
    /**
     * Se encarga de cerrar la conexion con el origen de datos actual
     */
    public void cerrarConexion() {
        try {
            conexion.close();
            System.out.println("conexion cerrada");
            resultado.close();
            //System.out.println("resultado cerrado");
        } catch (SQLException e) {
        }
    }

    /**
     * Vacia la tabla especificada sin destruir la estructura ni la tabla misma
     */
    public void vaciarTabla(String NombreTabla) {

        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            try {
                sentencia.executeUpdate("DELETE FROM " + NombreTabla);
            } catch (SQLException e) {
                System.out.println("Error al vaciar la tabla");
                System.out.println(e);
            }
        } catch (SQLException e) {
        }
    }

    /**
     * cuentaRegs devuelve el numero de registros de la tabla especificada
     */
    public int cuentaRegs(String NombreTabla) {

        Statement sentencia;

        try {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery("SELECT * FROM " + NombreTabla);

            int line = 0;
            while (resultado.next()) {
                line++;
            }

            return line;

        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * consulta devuelve una cadena con el valor de un campo en una tabla bajo
     * cierta condicion ejemplo: consulta("TABLA_CTCC", "valor", "variable = '"
     * + var + "'");
     */
    public String consulta(String NombreTabla, String campo, String Condicion) {

        Statement sentencia;
        String s = "SELECT " + campo + " FROM " + NombreTabla + " WHERE " + Condicion;
        // System.out.println(s);


        try {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(s);
            if (resultado.next()) {
                return (resultado.getString(campo)); 
            } else {
                return ("");
            }
        } catch (SQLException e) {
            return ("Error de datos ..");
        }
    }

    
    
    /**
     * actualizar actualiza un dato dado de un campo en una tabla especifica
     * bajo una condicion dada ej. ID='L001'. ejemplo:
     * actualizar("TABLA_CTCC","valor",""+valor,"variable = '"+variable+"'");
     */
    public void actualizar(String tabla, String campo, String dato, String condicion) {

        Statement sentencia;
        String s;

        s = "UPDATE " + tabla + " SET " + campo + "=" + "'" + dato + "'" + " WHERE " + condicion;
        //System.out.println(s);

        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println("No se puede actualizar el dato");
        }
    }

    /**
     * insertar_fila agrega un registro con los datos especificados en el
     * arreglo valores
     */
    public void insertar_fila(String tabla, String[] valores) {

        Statement sentencia;
        String str = "";

        for (int i = 0; i < valores.length; i++) {
            if (i == valores.length - 1) {
                str = str + "'" + valores[i] + "'";
            } else {
                str = str + "'" + valores[i] + "',";
            }
        }

        try {

            sentencia = conexion.createStatement();
            sentencia.executeUpdate("INSERT INTO " + tabla
                    + " VALUES (" + str + ")");
            //System.out.println("INSERT INTO "+tabla+
            //                     " VALUES ("+  str  +")");
        } catch (SQLException e) {
        }
    }

    /**
     * getData genera una matriz a partir de una consulta en una tabla y campos
     * especificados
     */
    public String[][] getData(String NombreTabla, String[] campos) {
        Statement sentencia;
        int noRegs = cuentaRegs(NombreTabla);
        String[][] datos = new String[noRegs][campos.length];

        try {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery("SELECT * FROM " + NombreTabla);

            int line = 0;
            while (resultado.next()) {
                for (int col = 0; col < campos.length; col++) {
                    datos[line][col] = resultado.getString(campos[col]);
                }
                line++;
            }
            return datos;

        } catch (SQLException e) {
            return datos;
        }
    }

    public void displayData(String NombreTabla, String[] campos) {
        Statement sentencia;

        try {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery("SELECT * FROM " + NombreTabla);

            while (resultado.next()) {
                for (int col = 0; col < campos.length; col++) {
                    System.out.print(resultado.getString(campos[col]) + "\t");
                }
                System.out.println("");

            }
        } catch (SQLException e) {
        }
    }
    
    public void displayData(String NombreTabla, String[] campos, String condicion) {
        Statement sentencia;

        try {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery("SELECT * FROM " + NombreTabla + " WHERE "+condicion);

            while (resultado.next()) {
                for (int col = 0; col < campos.length; col++) {
                    System.out.print(resultado.getString(campos[col]) + "\t");
                }
                System.out.println("");

            }
        } catch (SQLException e) {
        }
    }    
    
        public double[] getRegistro(String NombreTabla, String[] campos, String condicion) {
        Statement sentencia;
        double[] registro=new double[campos.length];

        try {
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery("SELECT * FROM " + NombreTabla + " WHERE "+condicion);

            while (resultado.next()) {
                for (int col = 0; col < campos.length; col++) {
                    registro[col]=Double.parseDouble(resultado.getString(campos[col]));

                }
                //System.out.println("");

            }
        } catch (SQLException e) {
        }
        return registro;
    } 

    public void crearTabla(String NombreTabla, String[] sentencias) {

        Statement sentencia;
        String params = sentencias[0];

        for (int i = 1; i < sentencias.length; i++) {
            params = params + "," + sentencias[i];
        }

        vaciarTabla(NombreTabla);

        try {

            sentencia = conexion.createStatement();
            sentencia.executeUpdate("CREATE TABLE " + NombreTabla + "(" + params + ")");

            System.out.println("tabla " + NombreTabla + " creada con exito");
        } catch (SQLException e) {
        }
    }

    public static void main(String args[]) {

        // conexiones directas a controlador
        Bdd bd1 = new Bdd("localhost", "tablas_vapor", "root", "mysqladmin");
        // Bdd bd2= new Bdd("localhost","simulacion","root","admin");

        // conexion a origen de datos
        // Bdd bd3= new Bdd("TurboGas");
        String campos1[] = {"Pressure", "Temperature", "LVolume", "VVolume", "LEnthalpy", "VEnthalpy", "LEntropy", "VEntropy"};
        // String campos2[] = {"variable", "valor","descripcion"};

        //  bd1.displayData("tablas_sat_2", campos1);
        double presion = 12.3;

         bd1.displayData("tablas_sat_2", campos1,  "Pressure=floor("+presion+")");
        // bd1.displayData("tablas_sat_2", campos1,  "Pressure=ceiling("+presion+")");

//        double[] A = bd1.getRegistro("tablas_sat_2", campos1, "Pressure=floor(" + presion + ")");
//        double[] B = bd1.getRegistro("tablas_sat_2", campos1, "Pressure=ceiling(" + presion + ")");

//        System.out.println(B[0]);
        bd1.cerrarConexion();
        // bd2.displayData("TABLA_CTCC", campos2);
        // bd2.cerrarConexion();
    }
}
