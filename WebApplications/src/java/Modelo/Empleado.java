/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import adquisiciondatos.*;

/**
 *
 * @author areyes
 */
public class Empleado {

    String nde,nombre,apellido;
    
    public Empleado(String nde, String nombre, String apellido) {
        this.nde = nde;
        this.nombre = nombre;
        this.apellido = apellido;
    }
    
    public Empleado(){}

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNDE() {
        return nde;
    }
    
        public void setNombre(String nombre) {
        this.nombre=nombre;
    }

    public void setApellido(String apellido) {
        this.apellido=apellido;
    }

    public void setNDE(String nde) {
        this.nde=nde;
    }
    
}
