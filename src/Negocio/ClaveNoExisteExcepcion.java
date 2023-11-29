/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author jdani
 */
public class ClaveNoExisteExcepcion extends Exception{

    public ClaveNoExisteExcepcion(String message) {
        super(message);
    }

    public ClaveNoExisteExcepcion() {
        super("clave no existe en el arbol");
    }
    
    
}
