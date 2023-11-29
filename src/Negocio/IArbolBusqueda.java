/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.util.List;

/**
 *
 * @author jdani
 * @param <K>
 * @param<V>
 */
public interface IArbolBusqueda <K extends Comparable<K> , V> {
    
    void insertar(K claveAInsertar , V valorAsociado); 
    V eliminar(K claveAEliminar) throws ClaveNoExisteExcepcion;
    V buscar(K clave); 
    boolean contiene(K clave) ; 
    int size(); 
    int altura();
    int vaciar();
    boolean esArbolvacio();
    boolean esHoja(K clave);
    byte getNumHoja();
    int nivel();
    List<K> recorridoEnInOrden();
    List<V> recorridoEnInOrdenDeValores();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnPostOrden();
    List<K> recorridoPorNiveles();
    
    
}
