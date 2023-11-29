/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Negocio;
/**
 *
 * @author jdani
 * @param <K>
 * @param <V>
 */
public class NodoBinario<K , V>{
    private K clave ; 
    private V valor ;
    private NodoBinario hijoIzquierdo;
    private NodoBinario hijoDerecho;

    public NodoBinario getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoBinario hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public NodoBinario getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(NodoBinario hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
    

    public NodoBinario(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }
    
    public static NodoBinario nodoVacio(){
        return null;
    }
    
    public static boolean esNodoVacio(NodoBinario nodo){
        return nodo==NodoBinario.nodoVacio(); 
    }
    
    
    public boolean esVacioElHijoIzquierdo(){
        return NodoBinario.esNodoVacio(this.getHijoIzquierdo()); 
    }
    public boolean esVacioElHijoDerecho(){
        return NodoBinario.esNodoVacio(this.getHijoDerecho()); 
    }
}
