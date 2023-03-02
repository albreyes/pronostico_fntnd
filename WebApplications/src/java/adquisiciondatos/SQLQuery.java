/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adquisiciondatos;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Alberto
 */
public class SQLQuery {
    
    private Connection conn;
    protected PreparedStatement consulta;
    protected ResultSet datos;
    

    public  SQLQuery(String servidor, String bd, String usuario, String password)  {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://"+servidor+":3306/"+bd, usuario, password);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    public void desconectar() {
        try {
            conn.close();
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void desconectar(ResultSet rs) {
        desconectar();
        try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void verTodos()  {
        try {
            consulta=conn.prepareStatement("SELECT * FROM actor");
                datos = consulta.executeQuery();
                
                while(datos.next()){
                    System.out.println("first_name: "+datos.getString("first_name"));
                }
        } catch (SQLException ex) {
            Logger.getLogger(SQLQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }        

        public void displayData(String NombreTabla, String[] campos) {
        Statement sentencia;
        int noRegs = cuentaRegs(NombreTabla);
        // String[][] datos=new String[noRegs][campos.length];

        try {
            sentencia = conn.createStatement();
            datos = sentencia.executeQuery("SELECT * FROM " + NombreTabla);

            while (datos.next()) {
                for (int col = 0; col < campos.length; col++) {
                    System.out.print(datos.getString(campos[col]) + "\t");
                }
                System.out.println("");

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
            sentencia = conn.createStatement();
            datos = sentencia.executeQuery("SELECT * FROM " + NombreTabla);

            int line = 0;
            while (datos.next()) {
                line++;
            }

            return line;

        } catch (SQLException e) {
            return -1;
        }
    }
        
        public static void main(String args[]) {
            SQLQuery sqlq=new SQLQuery("localhost", "sakila", "root", "admin");
            sqlq.verTodos();
           //         String campos[] = {"id_ag", "pos_x","pos_y", "id_tipo_ag", "nombre_parque"};

       // sqlq.displayData("aerogenerador", campos);
            sqlq.desconectar();
        }
}
