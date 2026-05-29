package com.corestore;

public class ProductoCarrito {

    private String ruta;
    private String nombre;
    private String precio;
    private String descripcion;
    private int cantidad;

    public ProductoCarrito(String ruta, String nombre, String precio,
                            String descripcion, int cantidad) {

        this.ruta = ruta;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    // ===== GETTERS =====
    public String getRuta() {
        return ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }
}