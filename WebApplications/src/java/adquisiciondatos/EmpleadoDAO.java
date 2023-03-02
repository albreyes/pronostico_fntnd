/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adquisiciondatos;
import java.sql.*;

/**
 *
 * @author areyes
 */
public class EmpleadoDAO {
    
    Bdd bd;
    
    public EmpleadoDAO(){
       // conexion=new Connection();
    }
    
    public Empleado verificaEmpleado(int nde) {
        bd = new Bdd("localhost", "classicmodels", "root", "mysqladmin");

        Empleado empleado = null;
        Connection accesoDB = bd.getConexion();

        try {
            PreparedStatement ps = accesoDB.prepareStatement("select * from employees where employeeNumber=?");
            ps.setString(1, "" + nde);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                empleado = new Empleado();
                empleado.setNDE(Integer.parseInt(rs.getString(1)));
                empleado.setApellido(rs.getString(2));
                empleado.setNombre(rs.getString(3));
                return empleado;
            }
            //System.out.println("No entró"); 
        } catch (Exception e) {
        }
        return empleado;
    }
    
    // no puedo registrar empleados por restriccciones de foreing keys 
    public String registraUsuario(int nde, String apellido, String nombre){
        String respuesta=null;
        
        bd = new Bdd("localhost", "classicmodels", "root", "mysqladmin");
        Connection accesoDB = bd.getConexion();
        
        try{
        PreparedStatement ps=accesoDB.prepareStatement("insert into employees(employeeNumber,lastName,firstName,extension,email,officeCode,reportsTo,jobTitle) values(?,?,?,?,?,?,?,?)");
        ps.setInt(1, nde);
        ps.setString(2,apellido);
        ps.setString(3, nombre);
        ps.setString(4, "vacio");
        ps.setString(5, "vacio");
        ps.setString(6, "vacio");
        ps.setInt(7, 0);
        ps.setString(8, "vacio");
        
        int rs=ps.executeUpdate();
        
        if(rs>0){
            respuesta="Registro exitoso";
        }
        
        }
        catch(Exception e){}
        
         return respuesta;
    }
    
    public static void main(String args[]) {
        /*
        // conexiones directas a controlador
        Bdd1 bd = new Bdd1("localhost", "classicmodels", "root", "admin");
        String campos1[] = {"employeeNumber","lastName","firstName","email"};
        bd.displayData("employees", campos1);
         bd.cerrarConexion();
         */
        int nde = 1088;

      //  EmpleadoDAO edao1 = new EmpleadoDAO();
     //   Empleado emp1 = edao1.verificaEmpleado(nde);

     //   System.out.println("NDE:" + emp1.getNDE());
     //   System.out.println("Nombre:" + emp1.getNombre());
     //   System.out.println("Apellido:" + emp1.getApellido());
        
        //String resp=edao1.registraUsuario(3587, "Reyes", "Alberto");
        //System.out.println(resp);
    }
    
}
