/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author jdani
 * @param <K>
 * @param <V>
 */
public class ArbolBinarioBusqueda<K extends Comparable<K>, V>
        implements IArbolBusqueda<K, V> {

    protected NodoBinario<K, V> raiz;

    public ArbolBinarioBusqueda() {

    }

    public ArbolBinarioBusqueda(
            List<K> clavesInOrden,
            List<V> valoresInOrden,
            List<K> clavesNoInOrden,
            List<V> valoresNoInOrden,
            boolean conPreOrden) {
        if (conPreOrden) {
            this.raiz = reconstruirConPreOrden(clavesInOrden,
                    valoresInOrden, clavesNoInOrden,
                    valoresNoInOrden);
        } else {
            this.raiz = reconstruirConPostOrden(clavesInOrden,
                    valoresInOrden, clavesNoInOrden,
                    valoresNoInOrden);
        }

    }

    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("clave invalida, no se aceptan claves nulas");
        }
        if (valorAsociado == null) {
            throw new IllegalArgumentException("valor invalido, no se aceptan valores nulos");
        }

        if (this.esArbolvacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAsociado);
            return;
        }

        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        boolean insertarAIzquierda = true;

        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveDelNodoActual = nodoActual.getClave();
            if (claveAInsertar.compareTo(claveDelNodoActual) < 0) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoIzquierdo();
                insertarAIzquierda = true;
            } else if (claveAInsertar.compareTo(claveDelNodoActual) > 0) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoDerecho();
                insertarAIzquierda = false;
            } else {
                nodoActual.setValor(valorAsociado);
                return;
            }
        }//
        NodoBinario<K, V> nodoNuevo = new NodoBinario<>(claveAInsertar, valorAsociado);
        if (insertarAIzquierda) {
            nodoAnterior.setHijoIzquierdo(nodoNuevo);
        } else {
            nodoAnterior.setHijoDerecho(nodoNuevo);
        }

    }

    @Override
    public V eliminar(K claveAEliminar) throws ClaveNoExisteExcepcion {
        if (claveAEliminar == null) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        V valoreAsociado = buscar(claveAEliminar);
        if (valoreAsociado == null) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valoreAsociado;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return nodoActual;
        }

        //caso 1    si es Hoja 
        if (esHoja(nodoActual.getClave())) {
            return NodoBinario.nodoVacio();
        }

        //caso 2   si tiene un hijo  
        if (nodoActual.esVacioElHijoIzquierdo() && !nodoActual.esVacioElHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }
        if (!nodoActual.esVacioElHijoIzquierdo() && nodoActual.esVacioElHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }

        // caso 3   si tiene 2 hijos 
        NodoBinario<K, V> nodoDelSucesor = obtenerNodoDelSucesor(nodoActual.getHijoDerecho());

        NodoBinario<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijo);

        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());

        return nodoActual;

    }

    protected NodoBinario<K, V> obtenerNodoDelSucesor(NodoBinario<K, V> unNodo) {
        NodoBinario<K, V> nodoAnterior;
        do {
            nodoAnterior = unNodo;
            unNodo = unNodo.getHijoIzquierdo();
        } while (!NodoBinario.esNodoVacio(unNodo));

        return nodoAnterior;
    }

    @Override
    public V buscar(K claveABuscar) {
        if (!this.esArbolvacio()) {

            NodoBinario<K, V> nodoActual = this.raiz;

            do {
                K claveDelNodoActual = nodoActual.getClave();
                if (claveABuscar.compareTo(claveDelNodoActual) < 0) {
                    nodoActual = nodoActual.getHijoIzquierdo();
                } else if (claveABuscar.compareTo(claveDelNodoActual) > 0) {
                    nodoActual = nodoActual.getHijoDerecho();
                } else {
                    return nodoActual.getValor();
                }

            } while (!NodoBinario.esNodoVacio(nodoActual));
        }
        return null;
    }

    @Override
    public boolean contiene(K claveAVerificar) {
        return this.buscar(claveAVerificar) != null;
    }

    @Override
    public int size() {
        int cantidad = 0;
        if (!this.esArbolvacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();    // retorna el valor y despues lo elimina de la cola
                cantidad++;
                if (!nodoActual.esVacioElHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioElHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            } while (!colaDeNodos.isEmpty());
        }
        return cantidad;
    }

    public int sizeRec2() {
        return sizeRec2(this.raiz);
    }

    private int sizeRec2(NodoBinario nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int sizeIzq = sizeRec2(nodoActual.getHijoIzquierdo());
        int sizeDer = sizeRec2(nodoActual.getHijoDerecho());
        return sizeDer + sizeIzq + 1;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoBinario nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaIzq = altura(nodoActual.getHijoIzquierdo());
        int alturaDer = altura(nodoActual.getHijoDerecho());
        return alturaIzq > alturaDer ? alturaIzq + 1 : alturaDer + 1;
    }

    public int alturaV2() {
        int altura = 0;
        if (!this.esArbolvacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                int nroDeNodosDelNivel = colaDeNodos.size();
                while (nroDeNodosDelNivel > 0) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();    // retorna el valor y despues lo elimina de la cola
                    if (!nodoActual.esVacioElHijoIzquierdo()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esVacioElHijoDerecho()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                    nroDeNodosDelNivel--;
                }
                altura++;
            } while (!colaDeNodos.isEmpty());
        }
        return altura;
    }

    @Override
    public int vaciar() {
        this.raiz = NodoBinario.nodoVacio();
        return 0;
    }

    @Override
    public boolean esArbolvacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new LinkedList<>();
        if (!this.esArbolvacio()) {
            //Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            Stack<NodoBinario<K, V>> pilaDeNodo = new Stack<>();
            NodoBinario<K, V> nodoActual = this.raiz;

            apilarParaInOrden(pilaDeNodo, nodoActual);

            while (!pilaDeNodo.isEmpty()) {
                nodoActual = pilaDeNodo.pop();
                recorrido.add(nodoActual.getClave());
                if (!nodoActual.esVacioElHijoDerecho()) {
                    nodoActual = nodoActual.getHijoDerecho();
                    apilarParaInOrden(pilaDeNodo, nodoActual);

                }
            }
        }
        return recorrido;
    }

    private void apilarParaInOrden(Stack<NodoBinario<K, V>> pilaDeNodo, NodoBinario<K, V> nodoActual) {
        do {
            pilaDeNodo.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        } while (!NodoBinario.esNodoVacio(nodoActual));
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoBinario nodoActual, List<K> Lista) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            Lista.add((K) nodoActual.getClave());
            recorridoEnPreOrden(nodoActual.getHijoIzquierdo(), Lista);
            recorridoEnPreOrden(nodoActual.getHijoDerecho(), Lista);
        }
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> listaDeAlmacenamiento = new LinkedList<>();
        recorridoEnPostOrden(this.raiz, listaDeAlmacenamiento);
        return listaDeAlmacenamiento;
    }

    private void recorridoEnPostOrden(NodoBinario nodoActual, List<K> recorridoPostOrden) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            recorridoEnPostOrden(nodoActual.getHijoIzquierdo(), recorridoPostOrden);
            recorridoEnPostOrden(nodoActual.getHijoDerecho(), recorridoPostOrden);
            recorridoPostOrden.add((K) nodoActual.getClave());
        }
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (!this.esArbolvacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();    // retorna el valor y despues lo elimina de la cola
                recorrido.add(nodoActual.getClave());
                if (!nodoActual.esVacioElHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioElHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            } while (!colaDeNodos.isEmpty());
        }
        return recorrido;
    }

    @Override
    public boolean esHoja(K claveABuscar) {
        if (!this.esArbolvacio()) {

            NodoBinario<K, V> nodoActual = this.raiz;

            do {
                K claveDelNodoActual = nodoActual.getClave();
                if (claveABuscar.compareTo(claveDelNodoActual) < 0) {
                    nodoActual = nodoActual.getHijoIzquierdo();
                } else if (claveABuscar.compareTo(claveDelNodoActual) > 0) {
                    nodoActual = nodoActual.getHijoDerecho();
                } else {
                    return nodoActual.getHijoIzquierdo() == null && nodoActual.getHijoDerecho() == null;
                }

            } while (!NodoBinario.esNodoVacio(nodoActual));
        }
        return false;
    }

    @Override
    public byte getNumHoja() {
        byte cantHoja = 0;
        if (!this.esArbolvacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodo = new Stack<>();
            NodoBinario<K, V> nodoActual = this.raiz;
            do {
                pilaDeNodo.push(nodoActual);
                nodoActual = nodoActual.getHijoIzquierdo();
            } while (!NodoBinario.esNodoVacio(nodoActual));
            while (!pilaDeNodo.isEmpty()) {
                nodoActual = pilaDeNodo.pop();
                if (esHoja(nodoActual.getClave())) {
                    cantHoja++;
                }
                if (!nodoActual.esVacioElHijoDerecho()) {
                    nodoActual = nodoActual.getHijoDerecho();
                    do {
                        pilaDeNodo.push(nodoActual);
                        nodoActual = nodoActual.getHijoIzquierdo();
                    } while (!NodoBinario.esNodoVacio(nodoActual));
                }
            }
        }
        return cantHoja;
    }
//-----------------------------------------------------------------------------

    public int getNumHojasRecursivo() {   // basado en size(); 
        return getNumHojasRecursivo(this.raiz);
    }

    private int getNumHojasRecursivo(NodoBinario nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual) || esHoja((K) nodoActual.getClave())) {
            return NodoBinario.esNodoVacio(nodoActual) ? 0 : 1;
        }
        int NumIzq = getNumHojasRecursivo(nodoActual.getHijoIzquierdo());
        int NumDer = getNumHojasRecursivo(nodoActual.getHijoDerecho());
        return NumDer + NumIzq;

    }

    /*
    public int numNodosConDosHijosSinElNivelRecursivo(int nivel){
        
    }
    
    public int numNodosConDosHijosSinElNivelRecursivo(NodoBinario<K,V> nodoActual,List Lista){
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            Lista.add((K) nodoActual.getClave());
            recorridoEnPreOrden(nodoActual.getHijoIzquierdo(), Lista);
            recorridoEnPreOrden(nodoActual.getHijoDerecho(), Lista);
        }
    }
     */
    public int numNodosConDosHijosSinElNivel(int nivel) {//--------------------examen de simulacion 
        int contador = 0;
        int nivelActual = -1;
        //List<K> recorrido = new ArrayList<>();
        if (!this.esArbolvacio()) {
            nivelActual++; //0
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                int nodosEnNivel = colaDeNodos.size();
                while (nodosEnNivel > 0) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();    // retorna el valor y despues lo elimina de la cola
                    if (!nodoActual.esVacioElHijoIzquierdo() && !nodoActual.esVacioElHijoDerecho() && nivel != nivelActual ) {
                        contador++;
                    }
                    if (!nodoActual.esVacioElHijoIzquierdo()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esVacioElHijoDerecho()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                    nodosEnNivel--;
                }
                nivelActual++;
            } while (!colaDeNodos.isEmpty());
        }
        return contador;
    }

//-------------------------------------------------------------------------------
    public int getNivel(K claveABuscar) {  // basado en funcion buscar(K clave);
        int contadorDeNivel = -1;
        if (!this.esArbolvacio()) {
            NodoBinario<K, V> nodoActual = this.raiz;

            do {
                K claveDelNodoActual = nodoActual.getClave();
                if (claveABuscar.compareTo(claveDelNodoActual) < 0) {
                    nodoActual = nodoActual.getHijoIzquierdo();
                    contadorDeNivel++;
                } else if (claveABuscar.compareTo(claveDelNodoActual) > 0) {
                    nodoActual = nodoActual.getHijoDerecho();
                    contadorDeNivel++;
                } else {
                    return contadorDeNivel + 1;
                }

            } while (!NodoBinario.esNodoVacio(nodoActual));
        }
        return 0;
    }

    public int cantHojaNivel(int nivel) {
        int contadorNodos = 0;
        int nivelDeNodo = 0;
        if (!this.esArbolvacio() && (nivel <= altura() - 1)) {
            Queue<NodoBinario<K, V>> colaDeNodosHijo = new LinkedList<>();
            Queue<NodoBinario<K, V>> colaDeNodosPadre = new LinkedList<>();
            colaDeNodosPadre.offer(this.raiz);
            while (nivelDeNodo != nivel) {
                if (nivelDeNodo != nivel) {
                    nivelDeNodo++;
                    do {
                        NodoBinario<K, V> nodoActual = colaDeNodosPadre.poll();
                        if (!nodoActual.esVacioElHijoIzquierdo()) {
                            colaDeNodosHijo.offer(nodoActual.getHijoIzquierdo());
                        }
                        if (!nodoActual.esVacioElHijoDerecho()) {
                            colaDeNodosHijo.offer(nodoActual.getHijoDerecho());
                        }
                    } while (!colaDeNodosPadre.isEmpty());
                }
                if (nivelDeNodo != nivel) {
                    nivelDeNodo++;
                    do {
                        NodoBinario<K, V> nodoActual = colaDeNodosHijo.poll();
                        if (!nodoActual.esVacioElHijoIzquierdo()) {
                            colaDeNodosPadre.offer(nodoActual.getHijoIzquierdo());
                        }
                        if (!nodoActual.esVacioElHijoDerecho()) {
                            colaDeNodosPadre.offer(nodoActual.getHijoDerecho());
                        }
                    } while (!colaDeNodosHijo.isEmpty());
                }
            }
            if (colaDeNodosHijo.isEmpty()) {
                while (!colaDeNodosPadre.isEmpty()) {
                    NodoBinario<K, V> nodoActual = colaDeNodosPadre.poll();
                    if (esHoja(nodoActual.getClave())) {
                        contadorNodos++;
                    }
                }
            } else {
                while (!colaDeNodosHijo.isEmpty()) {
                    NodoBinario<K, V> nodoActual = colaDeNodosHijo.poll();
                    if (esHoja(nodoActual.getClave())) {
                        contadorNodos++;
                    }
                }
            }
        }
        return contadorNodos;
    }

    public int getNumHojasRecursivo(int Nivel) {
        return getNumHojasRecursivo(this.raiz, Nivel);
    }

    private int getNumHojasRecursivo(NodoBinario nodoActual, int Nivel) {
        if (NodoBinario.esNodoVacio(nodoActual) || getNivel((K) nodoActual.getClave()) == Nivel) {
            return NodoBinario.esNodoVacio(nodoActual) || !esHoja((K) nodoActual.getClave()) ? 0 : 1;
        }
        int nodoIzq = getNumHojasRecursivo(nodoActual.getHijoIzquierdo(), Nivel);
        int nodoDer = getNumHojasRecursivo(nodoActual.getHijoDerecho(), Nivel);
        return nodoDer + nodoIzq;
    }

    //  retornar el numero de nodos que existen en el arbol , contando desde la raiz hasta el nivel
    // pasado por paremetro ; 
    public int getNumNodosHastaUnNivel(int Nivel) {
        return getNumNodosHastaUnNivel(this.raiz, Nivel);
    }

    private int getNumNodosHastaUnNivel(NodoBinario nodoActual, int Nivel) {
        if (NodoBinario.esNodoVacio(nodoActual) || getNivel((K) nodoActual.getClave()) == Nivel) {
            return NodoBinario.esNodoVacio(nodoActual) ? 0 : 1;
        }
        int nodoIzq = getNumNodosHastaUnNivel(nodoActual.getHijoIzquierdo(), Nivel);
        int nodoDer = getNumNodosHastaUnNivel(nodoActual.getHijoDerecho(), Nivel);
        return nodoDer + nodoIzq + 1;
    }

    private NodoBinario<K, V> reconstruirConPreOrden(
            List<K> clavesInOrden,
            List<V> valoresInOrden,
            List<K> clavesPostOrden,
            List<V> valoresPostOrden) {

        if (clavesInOrden.isEmpty()) {
            //System.out.println("lista vacia");
            return NodoBinario.nodoVacio();
        }

        int posicionDeClaveEnPostOrden = 0;
        K claveDelNodoActual = clavesPostOrden.get(posicionDeClaveEnPostOrden);
        V valorAsociadoActual = valoresPostOrden.get(posicionDeClaveEnPostOrden);
        NodoBinario nodoActual = new NodoBinario<>(claveDelNodoActual, valorAsociadoActual);
        if (clavesInOrden.size() == 1) {
            return nodoActual;
        }

        int posicionDeClaveEnInOrden = this.buscarPosicionDeClave(clavesInOrden, claveDelNodoActual);
        //System.out.println("PosClaveIn " + posicionDeClaveEnInOrden);
        List<K> claveInOrdenPorIzquierda = clavesInOrden.subList(0, posicionDeClaveEnInOrden);
        List<V> valoresInOrdenPorIzquierda = valoresInOrden.subList(0, posicionDeClaveEnInOrden);

        List<K> clavesPostOrdenPorIzquierda = clavesPostOrden.subList(1, posicionDeClaveEnInOrden + 1);
        List<V> valoresPostOrdenPorIzquierda = valoresPostOrden.subList(1, posicionDeClaveEnInOrden + 1);

        //System.out.println("InIzq " + claveInOrdenPorIzquierda);
        //System.out.println("PreIz " +  clavesPostOrdenPorIzquierda);
        NodoBinario hijoIzquierdo = reconstruirConPreOrden(
                claveInOrdenPorIzquierda,
                valoresInOrdenPorIzquierda,
                clavesPostOrdenPorIzquierda,
                valoresPostOrdenPorIzquierda);

        List<K> claveInOrdenPorDerecha = clavesInOrden.subList(posicionDeClaveEnInOrden + 1, clavesInOrden.size());
        List<V> valoresInOrdenPorDerecha = valoresInOrden.subList(posicionDeClaveEnInOrden + 1, clavesInOrden.size());
        List<K> clavesPostOrdenPorDerecha = clavesPostOrden.subList(posicionDeClaveEnInOrden + 1, clavesPostOrden.size());
        List<V> valoresPostOrdenPorDerecha = valoresPostOrden.subList(posicionDeClaveEnInOrden + 1, clavesPostOrden.size());

        //System.out.println("InDer " + claveInOrdenPorDerecha);
        //System.out.println("PreDer " + clavesPostOrdenPorDerecha);
        NodoBinario hijoDerecho = reconstruirConPreOrden(
                claveInOrdenPorDerecha,
                valoresInOrdenPorDerecha,
                clavesPostOrdenPorDerecha,
                valoresPostOrdenPorDerecha);

        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);

        return nodoActual;

    }

    private int buscarPosicionDeClave(List<K> claves, K claveABuscar) {

        for (int i = 0; i < claves.size(); i++) {
            K claveActual = claves.get(i);
            if (claveABuscar.compareTo(claveActual) == 0) {
                return i;
            }

        }

        return -1;
    }

    private NodoBinario<K, V> reconstruirConPostOrden(
            List<K> clavesInOrden,
            List<V> valoresInOrden,
            List<K> clavesPostOrden,
            List<V> valoresPostOrden) {
        if (clavesInOrden.isEmpty()) {
            //System.out.println("lista vacia");
            return NodoBinario.nodoVacio();
        }

        int posicionDeClaveEnPostOrden = clavesPostOrden.size() - 1;
        //System.out.println(posicionDeClaveEnPostOrden);
        K claveDelNodoActual = clavesPostOrden.get(posicionDeClaveEnPostOrden);
        V valorAsociadoActual = valoresPostOrden.get(posicionDeClaveEnPostOrden);
        NodoBinario nodoActual = new NodoBinario<>(claveDelNodoActual, valorAsociadoActual);
        if (clavesInOrden.size() == 1) {
            return nodoActual;
        }

        int posicionDeClaveEnInOrden = this.buscarPosicionDeClave(clavesInOrden, claveDelNodoActual);
        //System.out.println("PosClaveIn " + posicionDeClaveEnInOrden);
        List<K> claveInOrdenPorIzquierda = clavesInOrden.subList(0, posicionDeClaveEnInOrden);
        List<V> valoresInOrdenPorIzquierda = valoresInOrden.subList(0, posicionDeClaveEnInOrden);

        List<K> clavesPostOrdenPorIzquierda = clavesPostOrden.subList(0, posicionDeClaveEnInOrden);
        List<V> valoresPostOrdenPorIzquierda = valoresPostOrden.subList(0, posicionDeClaveEnInOrden);

        //System.out.println("InIzq " + claveInOrdenPorIzquierda);
        //System.out.println("PostIz " +  clavesPostOrdenPorIzquierda);
        NodoBinario hijoIzquierdo = reconstruirConPostOrden(
                claveInOrdenPorIzquierda,
                valoresInOrdenPorIzquierda,
                clavesPostOrdenPorIzquierda,
                valoresPostOrdenPorIzquierda);

        List<K> claveInOrdenPorDerecha = clavesInOrden.subList(posicionDeClaveEnInOrden + 1, clavesInOrden.size());
        List<V> valoresInOrdenPorDerecha = valoresInOrden.subList(posicionDeClaveEnInOrden + 1, clavesInOrden.size());
        List<K> clavesPostOrdenPorDerecha = clavesPostOrden.subList(posicionDeClaveEnInOrden, clavesPostOrden.size() - 1);
        List<V> valoresPostOrdenPorDerecha = valoresPostOrden.subList(posicionDeClaveEnInOrden, clavesPostOrden.size() - 1);

        //System.out.println("InDer " + claveInOrdenPorDerecha);
        //System.out.println("PostDer " + clavesPostOrdenPorDerecha);
        NodoBinario hijoDerecho = reconstruirConPostOrden(
                claveInOrdenPorDerecha,
                valoresInOrdenPorDerecha,
                clavesPostOrdenPorDerecha,
                valoresPostOrdenPorDerecha);

        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);

        return nodoActual;

    }

    @Override
    public List<V> recorridoEnInOrdenDeValores() {
         List<V> recorrido = new LinkedList<>();
        if (!this.esArbolvacio()) {
            //Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            Stack<NodoBinario<K, V>> pilaDeNodo = new Stack<>();
            NodoBinario<K, V> nodoActual = this.raiz;

            apilarParaInOrden(pilaDeNodo, nodoActual);

            while (!pilaDeNodo.isEmpty()) {
                nodoActual = pilaDeNodo.pop();
                recorrido.add(nodoActual.getValor());
                if (!nodoActual.esVacioElHijoDerecho()) {
                    nodoActual = nodoActual.getHijoDerecho();
                    apilarParaInOrden(pilaDeNodo, nodoActual);

                }
            }
        }
        return recorrido;
    }

}
