package com.corestore.estructuras;

import com.corestore.modelo.Producto;

/**
 * =========================================================
 *  CLASE: NodoProducto
 * =========================================================
 *  Nodo utilizado por la Lista Doble Enlazada.
 *  Cada nodo guarda:
 *    - Un Producto (el dato)
 *    - Referencia al nodo ANTERIOR
 *    - Referencia al nodo SIGUIENTE
 *
 *  Esto permite recorrer la lista en ambas direcciones.
 * =========================================================
 */
public class NodoProducto {

    // ===== DATO =====
    public Producto producto;

    // ===== PUNTEROS =====
    public NodoProducto anterior;
    public NodoProducto siguiente;

    // ===== CONSTRUCTOR =====
    public NodoProducto(Producto producto) {
        this.producto = producto;
        this.anterior = null;
        this.siguiente = null;
    }
}