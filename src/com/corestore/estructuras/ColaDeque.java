package com.corestore.estructuras;

import com.corestore.modelo.Producto;

/**
 * =========================================================
 *  ESTRUCTURA DE DATOS: ColaDeque (Cola Doblemente Enlazada)
 * =========================================================
 *  Implementación de un Deque (Double Ended Queue) usando
 *  nodos doblemente enlazados.
 *
 *  ¿Para qué se usa en CoreStore Admin?
 *  → Historial de acciones recientes del administrador.
 *    Cada vez que se agrega, edita o elimina un producto,
 *    la acción se registra aquí. Permite deshacer/rehacer.
 *
 *  ¿Por qué Deque y no una cola simple?
 *  → Un Deque permite insertar y extraer por ambos extremos,
 *    lo cual es necesario para un historial con límite
 *    (si supera maxCapacidad, se elimina la acción más antigua).
 *
 *  Operaciones:
 *  - encolarFrente(accion)  → agrega al frente
 *  - encolarFinal(accion)   → agrega al final
 *  - desencolarFrente()     → extrae del frente
 *  - desencolarFinal()      → extrae del final
 *  - verFrente()            → consulta sin extraer
 *  - verFinal()             → consulta sin extraer
 *  - tamaño() / estaVacia()
 * =========================================================
 */
public class ColaDeque {

    // ===== NODO INTERNO =====
    private static class NodoAccion {
        String descripcion;     // Ej: "Agregó: iPhone 17 Pro Max"
        String tipoAccion;      // "AGREGAR", "EDITAR", "ELIMINAR"
        Producto productoRef;   // Referencia al producto afectado
        NodoAccion anterior;
        NodoAccion siguiente;

        NodoAccion(String descripcion, String tipoAccion, Producto producto) {
            this.descripcion  = descripcion;
            this.tipoAccion   = tipoAccion;
            this.productoRef  = producto;
            this.anterior     = null;
            this.siguiente    = null;
        }
    }

    // ===== CABEZA, COLA, TAMAÑO Y LÍMITE =====
    private NodoAccion frente;
    private NodoAccion fondo;
    private int tamaño;
    private final int maxCapacidad;

    // ===== CONSTRUCTOR =====
    public ColaDeque(int maxCapacidad) {
        this.frente       = null;
        this.fondo        = null;
        this.tamaño       = 0;
        this.maxCapacidad = maxCapacidad;
    }

    public ColaDeque() {
        this(50); // Por defecto: 50 acciones en historial
    }

    // =========================================================
    //  ENCOLAR AL FRENTE (más reciente adelante)
    // =========================================================
    public void encolarFrente(String descripcion, String tipoAccion, Producto producto) {
        NodoAccion nuevo = new NodoAccion(descripcion, tipoAccion, producto);

        if (estaVacia()) {
            frente = nuevo;
            fondo  = nuevo;
        } else {
            nuevo.siguiente = frente;
            frente.anterior = nuevo;
            frente          = nuevo;
        }
        tamaño++;

        // Si supera el límite, eliminar el más antiguo
        if (tamaño > maxCapacidad) {
            desencolarFondo();
        }
    }

    // =========================================================
    //  ENCOLAR AL FONDO (más antiguo atrás)
    // =========================================================
    public void encolarFondo(String descripcion, String tipoAccion, Producto producto) {
        NodoAccion nuevo = new NodoAccion(descripcion, tipoAccion, producto);

        if (estaVacia()) {
            frente = nuevo;
            fondo  = nuevo;
        } else {
            nuevo.anterior = fondo;
            fondo.siguiente = nuevo;
            fondo = nuevo;
        }
        tamaño++;
    }

    // =========================================================
    //  DESENCOLAR DEL FRENTE
    // =========================================================
    public String desencolarFrente() {
        if (estaVacia()) return null;

        String desc = frente.descripcion;

        if (frente == fondo) {
            frente = null;
            fondo  = null;
        } else {
            frente = frente.siguiente;
            frente.anterior = null;
        }
        tamaño--;
        return desc;
    }

    // =========================================================
    //  DESENCOLAR DEL FONDO
    // =========================================================
    public String desencolarFondo() {
        if (estaVacia()) return null;

        String desc = fondo.descripcion;

        if (frente == fondo) {
            frente = null;
            fondo  = null;
        } else {
            fondo = fondo.anterior;
            fondo.siguiente = null;
        }
        tamaño--;
        return desc;
    }

    // =========================================================
    //  VER SIN EXTRAER
    // =========================================================
    public String verFrente() {
        return estaVacia() ? null : frente.descripcion;
    }

    public String verFondo() {
        return estaVacia() ? null : fondo.descripcion;
    }

    // =========================================================
    //  OBTENER HISTORIAL COMPLETO (para mostrar en UI)
    // =========================================================
    public java.util.List<String> obtenerHistorial() {
        java.util.List<String> lista = new java.util.ArrayList<>();
        NodoAccion actual = frente;
        while (actual != null) {
            lista.add(actual.descripcion);
            actual = actual.siguiente;
        }
        return lista;
    }

    // =========================================================
    //  ESTADO
    // =========================================================
    public int tamaño()      { return tamaño; }
    public boolean estaVacia() { return frente == null; }
}