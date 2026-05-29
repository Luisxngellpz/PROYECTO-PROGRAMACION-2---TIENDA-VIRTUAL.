package com.corestore.estructuras;

import com.corestore.modelo.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================
 *  ESTRUCTURA DE DATOS: ListaDobleProductos
 * =========================================================
 *  Implementación de una Lista Doblemente Enlazada.
 *
 *  ¿Por qué una lista doble?
 *  - Permite inserción/eliminación eficiente en O(1)
 *    cuando conocemos el nodo directamente.
 *  - Permite recorrer productos hacia adelante y atrás
 *    (útil para paginación en el panel admin).
 *  - Cada nodo apunta al anterior y al siguiente.
 *
 *  Estructura visual:
 *
 *  null ← [Nodo1] ↔ [Nodo2] ↔ [Nodo3] → null
 *          cabeza                  cola
 *
 *  Operaciones implementadas:
 *  - agregar(producto)     → agrega al final
 *  - agregarAlInicio(p)    → agrega al inicio
 *  - eliminar(sku)         → elimina por SKU
 *  - buscarPorSku(sku)     → retorna Producto o null
 *  - actualizar(producto)  → reemplaza datos por SKU
 *  - obtenerTodos()        → retorna List<Producto>
 *  - tamaño()              → número de productos
 *  - estaVacia()           → boolean
 * =========================================================
 */
public class ListaDobleProductos {

    // ===== CABEZA Y COLA =====
    private NodoProducto cabeza;
    private NodoProducto cola;
    private int tamaño;

    // ===== CONSTRUCTOR =====
    public ListaDobleProductos() {
        this.cabeza = null;
        this.cola   = null;
        this.tamaño = 0;
    }

    // =========================================================
    //  AGREGAR AL FINAL
    //  Caso 1: lista vacía → cabeza = cola = nuevoNodo
    //  Caso 2: lista con nodos → cola.siguiente = nuevoNodo
    //                           nuevoNodo.anterior = cola
    //                           cola = nuevoNodo
    // =========================================================
    public void agregar(Producto producto) {
        NodoProducto nuevoNodo = new NodoProducto(producto);

        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola   = nuevoNodo;
        } else {
            nuevoNodo.anterior = cola;
            cola.siguiente     = nuevoNodo;
            cola               = nuevoNodo;
        }
        tamaño++;
    }

    // =========================================================
    //  AGREGAR AL INICIO
    //  Útil para mostrar el producto más reciente primero.
    // =========================================================
    public void agregarAlInicio(Producto producto) {
        NodoProducto nuevoNodo = new NodoProducto(producto);

        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola   = nuevoNodo;
        } else {
            nuevoNodo.siguiente = cabeza;
            cabeza.anterior     = nuevoNodo;
            cabeza              = nuevoNodo;
        }
        tamaño++;
    }

    // =========================================================
    //  ELIMINAR POR SKU
    //  Recorre la lista buscando el nodo con ese SKU.
    //  Al encontrarlo, conecta el nodo anterior con el siguiente
    //  (saltando el nodo a eliminar).
    // =========================================================
    public boolean eliminar(String sku) {
        NodoProducto actual = cabeza;

        while (actual != null) {
            if (actual.producto.getSku().equals(sku)) {

                // ¿Tiene nodo anterior?
                if (actual.anterior != null) {
                    actual.anterior.siguiente = actual.siguiente;
                } else {
                    // Era la cabeza
                    cabeza = actual.siguiente;
                }

                // ¿Tiene nodo siguiente?
                if (actual.siguiente != null) {
                    actual.siguiente.anterior = actual.anterior;
                } else {
                    // Era la cola
                    cola = actual.anterior;
                }

                tamaño--;
                return true; // Eliminado correctamente
            }
            actual = actual.siguiente;
        }
        return false; // No encontrado
    }

    // =========================================================
    //  BUSCAR POR SKU
    //  Recorre desde cabeza hasta encontrar el SKU.
    // =========================================================
    public Producto buscarPorSku(String sku) {
        NodoProducto actual = cabeza;

        while (actual != null) {
            if (actual.producto.getSku().equals(sku)) {
                return actual.producto;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    // =========================================================
    //  ACTUALIZAR PRODUCTO
    //  Reemplaza los datos de un producto existente (mismo SKU).
    // =========================================================
    public boolean actualizar(Producto productoActualizado) {
        NodoProducto actual = cabeza;

        while (actual != null) {
            if (actual.producto.getSku()
                    .equals(productoActualizado.getSku())) {
                actual.producto = productoActualizado;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    // =========================================================
    //  OBTENER TODOS
    //  Convierte la lista enlazada en un ArrayList estándar
    //  para poder usarla en la interfaz gráfica (TableView, etc.)
    // =========================================================
    public List<Producto> obtenerTodos() {
        List<Producto> lista = new ArrayList<>();
        NodoProducto actual = cabeza;

        while (actual != null) {
            lista.add(actual.producto);
            actual = actual.siguiente;
        }
        return lista;
    }

    // =========================================================
    //  BUSCAR POR TEXTO (título o categoría)
    // =========================================================
    public List<Producto> buscarPorTexto(String texto) {
        List<Producto> resultados = new ArrayList<>();
        NodoProducto actual = cabeza;
        String textoBusqueda = texto.toLowerCase();

        while (actual != null) {
            Producto p = actual.producto;
            if (p.getTitulo().toLowerCase().contains(textoBusqueda) ||
                p.getCategoria().toLowerCase().contains(textoBusqueda) ||
                p.getSku().toLowerCase().contains(textoBusqueda)) {
                resultados.add(p);
            }
            actual = actual.siguiente;
        }
        return resultados;
    }

    // =========================================================
    //  TAMAÑO Y ESTADO
    // =========================================================
    public int tamaño() { return tamaño; }

    public boolean estaVacia() { return cabeza == null; }

    public NodoProducto getCabeza() { return cabeza; }

    public NodoProducto getCola()   { return cola; }
}