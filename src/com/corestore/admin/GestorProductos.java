package com.corestore.admin;

import com.corestore.estructuras.ColaDeque;
import com.corestore.estructuras.ListaDobleProductos;
import com.corestore.modelo.Producto;
import java.util.List;
import java.util.UUID;

/**
 * =========================================================
 *  CLASE: GestorProductos  (Singleton)
 * =========================================================
 *  Es el CONTROLADOR central del módulo administrativo.
 *  Actúa como puente entre:
 *    → La interfaz gráfica (vistas JavaFX)
 *    → Las estructuras de datos (Lista doble + Deque)
 *
 *  Patrón Singleton: existe UNA SOLA instancia en toda
 *  la aplicación, garantizando que todas las pantallas
 *  lean y escriban los mismos datos.
 *
 *  ¿Qué gestiona?
 *  - Almacena todos los productos en una ListaDobleProductos
 *  - Registra cada acción en un ColaDeque (historial)
 *  - Provee métodos CRUD a la interfaz
 *  - Genera IDs únicos automáticamente
 * =========================================================
 */
public class GestorProductos {

    // ===== SINGLETON =====
    private static GestorProductos instancia;

    // ===== ESTRUCTURAS DE DATOS =====
    private final ListaDobleProductos listaProductos;
    private final ColaDeque historialAcciones;

    // ===== CONSTRUCTOR PRIVADO =====
    private GestorProductos() {
        listaProductos   = new ListaDobleProductos();
        historialAcciones = new ColaDeque(100);
        cargarProductosIniciales();
    }

    // =========================================================
    //  OBTENER INSTANCIA (Singleton)
    // =========================================================
    public static GestorProductos getInstance() {
        if (instancia == null) {
            instancia = new GestorProductos();
        }
        return instancia;
    }

    // =========================================================
    //  DATOS INICIALES  (los mismos productos del Catalogo.java)
    //  Esto garantiza coherencia con el proyecto existente.
    // =========================================================
    private void cargarProductosIniciales() {

        agregarSinHistorial(new Producto(
            generarId(), "iPhone 14 Pro Max 256GB", "Potencia en tu bolsillo",
            "El iPhone 14 Pro Max es un smartphone de alta gama de Apple.",
            "iPhone", "Nuevo", "Apple", "APL-IP14PM-256",
            1399.0, "EUR", 5, 120, 228, 7.7, 16.0,
            true, true, "Publicado",
            "/com/corestore/imag/01_iphone14.png",
            "iPhone 14 Pro Max - CoreStore",
            "Compra el iPhone 14 Pro Max en CoreStore. Envío rápido.",
            "iPhone,Apple,Nuevo", "Estándar (48-72h)", 4.0, 60.0, false, true
        ));

        agregarSinHistorial(new Producto(
            generarId(), "MacBook Pro M2 512GB", "El portátil definitivo",
            "MacBook Pro con chip M2, rendimiento extraordinario.",
            "MacBook", "Nuevo", "Apple", "APL-MBP-M2-512",
            2199.0, "EUR", 3, 45, 2150, 31.4, 22.1,
            true, false, "Publicado",
            "/com/corestore/imag/02_macbook.png",
            "MacBook Pro M2 - CoreStore",
            "Compra el MacBook Pro M2 en CoreStore.",
            "MacBook,Apple,Nuevo", "Estándar (48-72h)", 4.0, 60.0, true, true
        ));

        agregarSinHistorial(new Producto(
            generarId(), "iPad Pro 11\"", "Creatividad sin límites",
            "iPad Pro con chip M2 y pantalla Liquid Retina.",
            "iPad", "Usado", "Apple", "APL-IPAD-PRO11",
            899.0, "EUR", 5, 32, 466, 24.7, 17.8,
            true, false, "Publicado",
            "/com/corestore/imag/03_ipad.png",
            "iPad Pro 11 - CoreStore",
            "Compra el iPad Pro 11 en CoreStore.",
            "iPad,Apple,Usado", "Estándar (48-72h)", 4.0, 60.0, false, true
        ));

        agregarSinHistorial(new Producto(
            generarId(), "AirPods Pro 2", "Audio de alta fidelidad",
            "AirPods Pro segunda generación con cancelación de ruido.",
            "Accesorios", "Nuevo", "Apple", "APL-APP-2GEN",
            249.0, "EUR", 10, 200, 61, 6.0, 4.5,
            true, true, "Publicado",
            "/com/corestore/imag/04_airpods.png",
            "AirPods Pro 2 - CoreStore",
            "Compra los AirPods Pro 2 en CoreStore.",
            "AirPods,Apple,Nuevo", "Estándar (48-72h)", 4.0, 60.0, false, true
        ));

        agregarSinHistorial(new Producto(
            generarId(), "Apple Watch Series 8", "Tu salud en la muñeca",
            "Apple Watch con sensores avanzados de salud.",
            "Accesorios", "Nuevo", "Apple", "APL-AWS8",
            499.0, "EUR", 8, 75, 38, 4.5, 3.8,
            true, true, "Publicado",
            "/com/corestore/imag/09_watch.png",
            "Apple Watch Series 8 - CoreStore",
            "Compra el Apple Watch Series 8 en CoreStore.",
            "Watch,Apple,Nuevo", "Estándar (48-72h)", 4.0, 60.0, false, true
        ));
    }

    // =========================================================
    //  AGREGAR PRODUCTO
    // =========================================================
    public void agregar(Producto producto) {
        if (producto.getId() == null || producto.getId().isEmpty()) {
            producto.setId(generarId());
        }
        listaProductos.agregar(producto);
        historialAcciones.encolarFrente(
            "✚ Agregó: " + producto.getTitulo(),
            "AGREGAR",
            producto
        );
    }

    // Versión sin registrar en historial (para datos iniciales)
    private void agregarSinHistorial(Producto producto) {
        listaProductos.agregar(producto);
    }

    // =========================================================
    //  EDITAR PRODUCTO
    // =========================================================
    public boolean editar(Producto productoActualizado) {
        boolean exito = listaProductos.actualizar(productoActualizado);
        if (exito) {
            historialAcciones.encolarFrente(
                "✎ Editó: " + productoActualizado.getTitulo(),
                "EDITAR",
                productoActualizado
            );
        }
        return exito;
    }

    // =========================================================
    //  ELIMINAR PRODUCTO
    // =========================================================
    public boolean eliminar(String sku) {
        Producto p = listaProductos.buscarPorSku(sku);
        boolean exito = listaProductos.eliminar(sku);
        if (exito && p != null) {
            historialAcciones.encolarFrente(
                "✖ Eliminó: " + p.getTitulo(),
                "ELIMINAR",
                p
            );
        }
        return exito;
    }

    // =========================================================
    //  CONSULTAS
    // =========================================================
    public List<Producto> obtenerTodos() {
        return listaProductos.obtenerTodos();
    }

    public Producto buscarPorSku(String sku) {
        return listaProductos.buscarPorSku(sku);
    }

    public List<Producto> buscarPorTexto(String texto) {
        return listaProductos.buscarPorTexto(texto);
    }

    public int totalProductos() {
        return listaProductos.tamaño();
    }

    public List<String> obtenerHistorial() {
        return historialAcciones.obtenerHistorial();
    }

    // =========================================================
    //  UTILIDADES
    // =========================================================
    private String generarId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
