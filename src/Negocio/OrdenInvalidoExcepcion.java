/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author JAVIER
 */
public class OrdenInvalidoExcepcion  extends Exception{

public OrdenInvalidoExcepcion(){
    super ("orden del arbol dabe ser al menos 3");
}   

public OrdenInvalidoExcepcion (String message){
    
    super ("message");
}
        
}

