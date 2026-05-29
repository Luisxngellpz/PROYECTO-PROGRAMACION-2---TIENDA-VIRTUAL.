package com.corestore.modelo;

/**
 * =========================================================
 *  CLASE MODELO: Producto
 * =========================================================
 *  Representa un producto dentro del sistema CoreStore.
 *  Es un POJO puro: sólo campos, getters y setters.
 *  Esta clase es usada por las estructuras de datos y
 *  por la interfaz gráfica administrativa.
 * =========================================================
 */
public class Producto {

    // ===== CAMPOS =====
    private String id;           // Identificador único (SKU)
    private String titulo;       // Nombre del producto
    private String subtitulo;    // Subtítulo descriptivo
    private String descripcion;  // Descripción larga
    private String categoria;    // Ej: iPhone, MacBook, iPad...
    private String condicion;    // Ej: Nuevo, Usado, Reacondicionado
    private String marca;        // Ej: Apple, Samsung...
    private String sku;          // Código de referencia
    private double precio;       // Precio base
    private String moneda;       // Ej: EUR, USD...
    private int stockMinimo;     // Alerta si baja de aquí
    private int stockActual;     // Stock disponible
    private double peso;         // Peso en gramos
    private double ancho;        // Dimensión ancho (cm)
    private double alto;         // Dimensión alto (cm)
    private boolean visible;     // ¿Visible en tienda?
    private boolean destacado;   // ¿Aparece como destacado?
    private String estado;       // Borrador / Publicado
    private String rutaImagen;   // Ruta o URL de imagen principal
    private String seoTitulo;    // Meta-título SEO
    private String seoDescripcion; // Meta-descripción SEO
    private String etiquetas;    // Etiquetas separadas por coma
    private String metodoEnvio;  // Ej: Estándar (48-72h)
    private double costoEnvio;   // Costo de envío
    private double envioGratisDesde; // Monto mínimo para envío gratis
    private boolean requiereFirma;   // ¿Requiere firma al recibir?
    private boolean permiteDevoluciones; // ¿Permite devoluciones?

    // ===== CONSTRUCTOR VACÍO =====
    public Producto() {}

    // ===== CONSTRUCTOR COMPLETO =====
    public Producto(String id, String titulo, String subtitulo,
                    String descripcion, String categoria, String condicion,
                    String marca, String sku, double precio, String moneda,
                    int stockMinimo, int stockActual, double peso,
                    double ancho, double alto, boolean visible,
                    boolean destacado, String estado, String rutaImagen,
                    String seoTitulo, String seoDescripcion, String etiquetas,
                    String metodoEnvio, double costoEnvio,
                    double envioGratisDesde, boolean requiereFirma,
                    boolean permiteDevoluciones) {

        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.condicion = condicion;
        this.marca = marca;
        this.sku = sku;
        this.precio = precio;
        this.moneda = moneda;
        this.stockMinimo = stockMinimo;
        this.stockActual = stockActual;
        this.peso = peso;
        this.ancho = ancho;
        this.alto = alto;
        this.visible = visible;
        this.destacado = destacado;
        this.estado = estado;
        this.rutaImagen = rutaImagen;
        this.seoTitulo = seoTitulo;
        this.seoDescripcion = seoDescripcion;
        this.etiquetas = etiquetas;
        this.metodoEnvio = metodoEnvio;
        this.costoEnvio = costoEnvio;
        this.envioGratisDesde = envioGratisDesde;
        this.requiereFirma = requiereFirma;
        this.permiteDevoluciones = permiteDevoluciones;
    }

    // ===== GETTERS Y SETTERS =====
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSubtitulo() { return subtitulo; }
    public void setSubtitulo(String subtitulo) { this.subtitulo = subtitulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getCondicion() { return condicion; }
    public void setCondicion(String condicion) { this.condicion = condicion; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public int getStockActual() { return stockActual; }
    public void setStockActual(int stockActual) { this.stockActual = stockActual; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getAncho() { return ancho; }
    public void setAncho(double ancho) { this.ancho = ancho; }

    public double getAlto() { return alto; }
    public void setAlto(double alto) { this.alto = alto; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }

    public boolean isDestacado() { return destacado; }
    public void setDestacado(boolean destacado) { this.destacado = destacado; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public String getSeoTitulo() { return seoTitulo; }
    public void setSeoTitulo(String seoTitulo) { this.seoTitulo = seoTitulo; }

    public String getSeoDescripcion() { return seoDescripcion; }
    public void setSeoDescripcion(String seoDescripcion) { this.seoDescripcion = seoDescripcion; }

    public String getEtiquetas() { return etiquetas; }
    public void setEtiquetas(String etiquetas) { this.etiquetas = etiquetas; }

    public String getMetodoEnvio() { return metodoEnvio; }
    public void setMetodoEnvio(String metodoEnvio) { this.metodoEnvio = metodoEnvio; }

    public double getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(double costoEnvio) { this.costoEnvio = costoEnvio; }

    public double getEnvioGratisDesde() { return envioGratisDesde; }
    public void setEnvioGratisDesde(double envioGratisDesde) { this.envioGratisDesde = envioGratisDesde; }

    public boolean isRequiereFirma() { return requiereFirma; }
    public void setRequiereFirma(boolean requiereFirma) { this.requiereFirma = requiereFirma; }

    public boolean isPermiteDevoluciones() { return permiteDevoluciones; }
    public void setPermiteDevoluciones(boolean permiteDevoluciones) { this.permiteDevoluciones = permiteDevoluciones; }

    // ===== toString PARA DEPURACIÓN =====
    @Override
    public String toString() {
        return "Producto{" +
               "sku='" + sku + '\'' +
               ", titulo='" + titulo + '\'' +
               ", precio=" + precio +
               ", stock=" + stockActual +
               ", estado='" + estado + '\'' +
               '}';
    }
}
