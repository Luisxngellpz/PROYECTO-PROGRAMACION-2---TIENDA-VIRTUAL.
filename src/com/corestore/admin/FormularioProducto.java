package com.corestore.admin;

import com.corestore.modelo.Producto;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;
import java.io.File;
import java.util.UUID;

/**
 * =========================================================
 *  CLASE: FormularioProducto
 * =========================================================
 *  Es el formulario principal del módulo administrativo.
 *  Replica visualmente las imágenes adjuntas:
 *    - Panel izquierdo: campos del producto (título,
 *      subtítulo, descripción, categoría, SKU, precio,
 *      stock, peso, dimensiones, visibilidad, galería)
 *    - Panel derecho: datos rápidos, imagen destacada,
 *      estado/etiquetas, SEO
 *    - Panel inferior: reglas de envío
 *
 *  Funciona en DOS MODOS:
 *    1. modo=null → AGREGAR nuevo producto
 *    2. modo=Producto → EDITAR producto existente
 *
 *  Después de guardar, notifica al PanelAdmin para que
 *  actualice la tabla de productos.
 * =========================================================
 */
public class FormularioProducto {

    // ===== DEPENDENCIAS =====
    private final GestorProductos gestor = GestorProductos.getInstance();
    private final Producto productoAEditar;  // null = modo agregar
    private final PanelAdmin panelAdmin;     // para refrescar la tabla

    // ===== CAMPOS DEL FORMULARIO =====
    private TextField campoTitulo;
    private TextField campoSubtitulo;
    private TextArea  campoDescripcion;
    private ComboBox<String> comboCat;
    private ComboBox<String> comboSubCat;
    private ComboBox<String> comboCondicion;
    private ComboBox<String> comboMarca;
    private TextField campoSku;
    private TextField campoPrecio;
    private ComboBox<String> comboMoneda;
    private TextField campoStockMin;
    private TextField campoStock;
    private TextField campoPeso;
    private TextField campoAncho;
    private TextField campoAlto;
    private CheckBox  chkVisible;
    private CheckBox  chkDestacado;
    private ComboBox<String> comboEstado;
    private TextField campoSeoTitulo;
    private TextArea  campoSeoDesc;
    private ComboBox<String> comboEnvio;
    private TextField campoCostoEnvio;
    private TextField campoGratisDesde;
    private CheckBox  chkFirma;
    private CheckBox  chkDevoluciones;

    // ===== IMAGEN =====
    private String rutaImagenSeleccionada = "";
    private ImageView imagenDestacada;
    private HBox galeria;

    // ===== STAGE =====
    private Stage stage;

    // =========================================================
    //  CONSTRUCTOR
    // =========================================================
    public FormularioProducto(Producto productoAEditar, PanelAdmin panelAdmin) {
        this.productoAEditar = productoAEditar;
        this.panelAdmin      = panelAdmin;
    }

    // =========================================================
    //  MOSTRAR FORMULARIO
    // =========================================================
    public void mostrar() {
        stage = new Stage();
        stage.setTitle(productoAEditar == null
            ? "CoreStore — Añadir nuevo producto"
            : "CoreStore — Editar: " + productoAEditar.getTitulo());
        stage.setMaximized(true);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");
        root.setTop(construirNavbar());
        root.setBottom(construirFooter());

        // Layout principal: formulario izquierda + sidebar derecha
        HBox cuerpo = new HBox(20);
        cuerpo.setPadding(new Insets(20));

        VBox panelIzquierdo = construirPanelIzquierdo();
        VBox panelDerecho   = construirPanelDerecho();

        HBox.setHgrow(panelIzquierdo, Priority.ALWAYS);
        panelDerecho.setPrefWidth(320);

        cuerpo.getChildren().addAll(panelIzquierdo, panelDerecho);

        ScrollPane scroll = new ScrollPane(cuerpo);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent;" +
                        "-fx-background: transparent;");

        root.setCenter(scroll);

        // Si es edición, pre-cargar campos
        if (productoAEditar != null) {
            preCargarCampos(productoAEditar);
        }

        Scene scene = new Scene(root, 1400, 900);
        stage.setScene(scene);
        stage.show();
    }

    // =========================================================
    //  NAVBAR
    // =========================================================
    private BorderPane construirNavbar() {
        BorderPane navbar = new BorderPane();
        navbar.setPadding(new Insets(12, 20, 12, 20));
        navbar.setStyle(
            "-fx-background-color: #111111;" +
            "-fx-border-color: #222222;" +
            "-fx-border-width: 0 0 1 0;"
        );

        Label logo = new Label("■ CoreStore");
        logo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        logo.setTextFill(Color.WHITE);

        HBox menu = new HBox(25);
        menu.setAlignment(Pos.CENTER);
        String[] opciones = {"Panel", "Productos", "Pedidos", "Clientes", "Marketing"};
        for (String op : opciones) {
            Label item = new Label(op);
            item.setFont(Font.font("Segoe UI", 14));
            item.setTextFill(op.equals("Productos") ? Color.WHITE : Color.rgb(180, 180, 180));
            menu.getChildren().add(item);
        }

        TextField busqueda = new TextField();
        busqueda.setPromptText("Buscar en catálogo, pedidos, clientes...");
        busqueda.setPrefWidth(280);
        busqueda.setStyle(
            "-fx-background-color: #222222;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: #888888;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 12 8 12;"
        );

        Button btnBorrador = new Button("💾 Guardar borrador");
        btnBorrador.setStyle(
            "-fx-background-color: #111111;" +
            "-fx-text-fill: white;" +
            "-fx-border-color: white;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 14 8 14;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        );

        HBox derecha = new HBox(10, busqueda, btnBorrador);
        derecha.setAlignment(Pos.CENTER_RIGHT);

        navbar.setLeft(logo);
        navbar.setCenter(menu);
        navbar.setRight(derecha);
        return navbar;
    }

    // =========================================================
    //  PANEL IZQUIERDO — Formulario principal
    // =========================================================
    private VBox construirPanelIzquierdo() {
        VBox panel = new VBox(20);

        // === TARJETA: AÑADIR NUEVO PRODUCTO ===
        VBox cardPrincipal = crearCard();

        Label lblTituloPag = new Label(
            productoAEditar == null ? "Añadir nuevo producto" : "Editar producto"
        );
        lblTituloPag.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));

        Label lblSubTituloPag = new Label(
            "Completa la información para publicar el artículo en tu tienda StoreShop."
        );
        lblSubTituloPag.setFont(Font.font("Segoe UI", 13));
        lblSubTituloPag.setTextFill(Color.GRAY);

        Separator sep0 = new Separator();
        sep0.setPadding(new Insets(5, 0, 5, 0));

        // Título del producto
        campoTitulo = crearCampoTexto("Ej: iPhone 17 Pro Max 256 GB - Azul Intenso");
        campoTitulo.setStyle(campoTitulo.getStyle() +
            "-fx-background-color: #111111;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: #888888;"
        );

        // Subtítulo
        campoSubtitulo = crearCampoTexto("Ej: Potencia, elegancia y tecnología en la palma de tu mano.");
        campoSubtitulo.setStyle(campoSubtitulo.getStyle() +
            "-fx-background-color: #111111;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: #888888;"
        );

        // Descripción
        campoDescripcion = new TextArea();
        campoDescripcion.setPromptText("Escribe una descripción detallada del producto...");
        campoDescripcion.setPrefRowCount(5);
        campoDescripcion.setWrapText(true);
        campoDescripcion.setStyle(
            "-fx-background-color: #111111;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: #888888;" +
            "-fx-background-radius: 8;" +
            "-fx-border-radius: 8;" +
            "-fx-font-size: 13px;" +
            "-fx-padding: 10;"
        );

        // Fila: Categoría + Condición
        comboCat = new ComboBox<>();
        comboCat.getItems().addAll("iPhone", "MacBook", "iPad", "Accesorios", "Watch", "AirPods");
        comboCat.setValue("iPhone");
        comboCat.setPrefWidth(150);

        comboSubCat = new ComboBox<>();
        comboSubCat.getItems().addAll("Magsafe", "Lightning", "USB-C", "Sin accesorio");
        comboSubCat.setValue("Magsafe");
        comboSubCat.setPrefWidth(150);

        comboCondicion = new ComboBox<>();
        comboCondicion.getItems().addAll("Nuevo", "Usado", "Reacondicionado");
        comboCondicion.setValue("Nuevo");
        comboCondicion.setPrefWidth(150);

        comboMarca = new ComboBox<>();
        comboMarca.getItems().addAll("Apple", "Samsung", "Xiaomi", "Google", "Otra");
        comboMarca.setValue("Apple");
        comboMarca.setPrefWidth(150);
        comboMarca.setPromptText("Marca: Apple");

        HBox filaCategoria = new HBox(10,
            columnaLabel("Categoría", comboCat),
            columnaLabel("", comboSubCat),
            columnaLabel("Condición", comboCondicion),
            columnaLabel("", comboMarca)
        );
        filaCategoria.setAlignment(Pos.CENTER_LEFT);

        // Fila: SKU + Precio + Stock
        campoSku       = crearCampoTexto("RSN-NEO2-BW");
        campoSku.setPrefWidth(180);
        campoPrecio    = crearCampoTexto("120");
        campoPrecio.setPrefWidth(100);
        comboMoneda    = new ComboBox<>();
        comboMoneda.getItems().addAll("EUR", "USD", "COP", "GBP");
        comboMoneda.setValue("EUR");
        campoStockMin  = crearCampoTexto("5");
        campoStockMin.setPrefWidth(70);
        campoStock     = crearCampoTexto("138");
        campoStock.setPrefWidth(70);

        HBox filaPrecios = new HBox(10,
            columnaLabel("SKU", campoSku),
            columnaLabel("Precio", campoPrecio),
            comboMoneda,
            columnaLabel("Stock", campoStockMin),
            campoStock
        );
        filaPrecios.setAlignment(Pos.BOTTOM_LEFT);

        // Fila: Peso + Dimensiones + Visibilidad + Destacado
        campoPeso   = crearCampoTexto("231");
        campoPeso.setPrefWidth(120);
        campoAncho  = crearCampoTexto("7");
        campoAncho.setPrefWidth(70);
        campoAlto   = crearCampoTexto("16");
        campoAlto.setPrefWidth(70);
        chkVisible   = new CheckBox();
        chkDestacado = new CheckBox();

        Button btnUpgrade = new Button("UPGRADE PLAN");
        btnUpgrade.setStyle(
            "-fx-background-color: #111111;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 12 8 12;" +
            "-fx-font-size: 11px;"
        );

        HBox filaPeso = new HBox(10,
            columnaLabel("Peso (g)", campoPeso),
            columnaLabel("Dimensiones (cm)", campoAncho),
            campoAlto,
            columnaLabel("Visibilidad", chkVisible),
            columnaLabel("Destacado", chkDestacado),
            btnUpgrade
        );
        filaPeso.setAlignment(Pos.BOTTOM_LEFT);

        // Galería
        Label lblGaleria = new Label("Galería del producto");
        lblGaleria.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));

        galeria = new HBox(15);
        galeria.setPadding(new Insets(15));
        galeria.setMinHeight(130);
        galeria.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #DDDDDD;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );

        Label lblDropZone = new Label(
            "Arrastra y suelta imágenes. Se recomienda al menos 4 fotos con fondo neutro."
        );
        lblDropZone.setTextFill(Color.GRAY);
        lblDropZone.setFont(Font.font("Segoe UI", 12));

        galeria.getChildren().add(lblDropZone);

        // Botón para agregar imagen
        Button btnAgregarImagen = new Button("📁 Seleccionar imagen");
        btnAgregarImagen.setStyle(
            "-fx-background-color: #EEEEEE;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 14 8 14;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        );
        btnAgregarImagen.setOnAction(e -> seleccionarImagen());

        // Botones finales
        Button btnCancelar      = crearBotonForm("Cancelar", "#EEEEEE", "#333333");
        Button btnGuardar       = crearBotonForm("Guardar", "#444444", "white");
        Button btnGuardarPublicar = crearBotonForm("Guardar y publicar", "#111111", "white");

        btnCancelar.setOnAction(e -> stage.close());
        btnGuardar.setOnAction(e -> guardarProducto(false));
        btnGuardarPublicar.setOnAction(e -> guardarProducto(true));

        HBox btnRow = new HBox(10,
            btnCancelar, btnGuardar, btnGuardarPublicar
        );
        btnRow.setAlignment(Pos.CENTER_RIGHT);
        btnRow.setPadding(new Insets(10, 0, 0, 0));

        cardPrincipal.getChildren().addAll(
            lblTituloPag, lblSubTituloPag, sep0,
            etiquetaFormulario("Título del producto"), campoTitulo,
            etiquetaFormulario("Subtítulo (se muestra bajo el nombre)"), campoSubtitulo,
            etiquetaFormulario("Descripción"), campoDescripcion,
            filaCategoria,
            filaPrecios,
            filaPeso,
            new Separator(),
            lblGaleria, galeria,
            btnAgregarImagen,
            new Separator(),
            btnRow
        );

        // === TARJETA: REGLAS DE ENVÍO ===
        VBox cardEnvio = crearCard();
        Label lblEnvioTitulo = new Label("Reglas de envío");
        lblEnvioTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));

        Label lblEnvioOpcional = new Label("Opcional");
        lblEnvioOpcional.setFont(Font.font("Segoe UI", 12));
        lblEnvioOpcional.setTextFill(Color.GRAY);

        HBox encabezadoEnvio = new HBox();
        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);
        encabezadoEnvio.getChildren().addAll(lblEnvioTitulo, espacio, lblEnvioOpcional);

        comboEnvio = new ComboBox<>();
        comboEnvio.getItems().addAll(
            "Estándar (48-72h)", "Express (24h)", "Mismo día", "Sin envío"
        );
        comboEnvio.setValue("Estándar (48-72h)");
        comboEnvio.setPrefWidth(220);

        campoCostoEnvio  = crearCampoTexto("4");
        campoCostoEnvio.setPrefWidth(120);
        campoGratisDesde = crearCampoTexto("60");
        campoGratisDesde.setPrefWidth(120);

        HBox filaEnvio = new HBox(15,
            columnaLabel("Método", comboEnvio),
            columnaLabel("Costo", campoCostoEnvio),
            columnaLabel("Gratis desde", campoGratisDesde)
        );
        filaEnvio.setAlignment(Pos.BOTTOM_LEFT);

        chkFirma       = new CheckBox("Requiere firma del receptor");
        chkFirma.setFont(Font.font("Segoe UI", 13));
        chkDevoluciones = new CheckBox("Permitir devoluciones");
        chkDevoluciones.setFont(Font.font("Segoe UI", 13));

        HBox filaChecks = new HBox(20, chkFirma, chkDevoluciones);

        cardEnvio.getChildren().addAll(encabezadoEnvio, filaEnvio, filaChecks);

        panel.getChildren().addAll(cardPrincipal, cardEnvio);
        return panel;
    }

    // =========================================================
    //  PANEL DERECHO — Datos rápidos + Imagen + Estado + SEO
    // =========================================================
    private VBox construirPanelDerecho() {
        VBox panel = new VBox(15);

        // === DATOS RÁPIDOS ===
        VBox cardDatos = crearCard();
        Label lblDatos = new Label("Datos rápidos");
        lblDatos.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

        HBox statsRow = new HBox(10);
        VBox ventasCard = crearMiniStat("Ventas 30d", "187");
        VBox devolCard  = crearMiniStat("Devoluciones", "3");
        HBox.setHgrow(ventasCard, Priority.ALWAYS);
        HBox.setHgrow(devolCard, Priority.ALWAYS);
        statsRow.getChildren().addAll(ventasCard, devolCard);

        // Gráfica simulada (placeholder visual)
        Label grafica = new Label("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
        grafica.setFont(Font.font("Monospaced", 12));
        grafica.setTextFill(Color.LIGHTGRAY);
        grafica.setPadding(new Insets(10, 0, 0, 0));

        cardDatos.getChildren().addAll(lblDatos, statsRow, grafica);

        // === IMAGEN DESTACADA ===
        VBox cardImagen = crearCard();
        HBox headerImagen = new HBox();
        Label lblImagen = new Label("Imagen destacada");
        lblImagen.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        Region spacerImg = new Region();
        HBox.setHgrow(spacerImg, Priority.ALWAYS);
        Button btnEditarImg = new Button("Editar");
        btnEditarImg.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #555555;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );
        btnEditarImg.setOnAction(e -> seleccionarImagen());
        headerImagen.getChildren().addAll(lblImagen, spacerImg, btnEditarImg);

        imagenDestacada = new ImageView();
        imagenDestacada.setFitWidth(260);
        imagenDestacada.setFitHeight(180);
        imagenDestacada.setPreserveRatio(true);
        imagenDestacada.setStyle("-fx-background-color: #EEEEEE;");

        // Imagen por defecto (placeholder)
        try {
            java.net.URL urlDefault = getClass().getResource(
                "/com/corestore/imag/01_iphone14.png"
            );
            if (urlDefault != null) {
                imagenDestacada.setImage(new Image(urlDefault.toExternalForm()));
            }
        } catch (Exception ex) {
            System.out.println("Sin imagen por defecto.");
        }

        StackPane contenedorImagen = new StackPane(imagenDestacada);
        contenedorImagen.setStyle(
            "-fx-background-color: #F0F0F0;" +
            "-fx-background-radius: 8;"
        );
        contenedorImagen.setPrefHeight(190);

        cardImagen.getChildren().addAll(headerImagen, contenedorImagen);

        // === ESTADO Y ETIQUETAS ===
        VBox cardEstado = crearCard();
        Label lblEstadoTit = new Label("Estado y etiquetas");
        lblEstadoTit.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));

        HBox filaEstado = new HBox(10);
        Label lblEst = new Label("Estado:");
        lblEst.setFont(Font.font("Segoe UI", 13));
        comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Borrador", "Publicado", "Archivado");
        comboEstado.setValue("Borrador");
        comboEstado.setStyle("-fx-background-radius: 8; -fx-font-size: 13px;");
        filaEstado.getChildren().addAll(lblEst, comboEstado);
        filaEstado.setAlignment(Pos.CENTER_LEFT);

        Label lblEtiq = new Label("Etiquetas");
        lblEtiq.setFont(Font.font("Segoe UI", 13));
        HBox etiquetasRow = new HBox(8,
            crearChip("Reacondicionado"),
            crearChip("Usado")
        );

        // Miniaturas de galería decorativas
        HBox miniGaleria = new HBox(8);
        for (int i = 1; i <= 4; i++) {
            StackPane thumb = new StackPane();
            thumb.setPrefSize(54, 54);
            thumb.setStyle(
                "-fx-background-color: #EEEEEE;" +
                "-fx-background-radius: 6;"
            );
            try {
                String ruta = "/com/corestore/imag/0" + i + "_" +
                    new String[]{"iphone14","macbook","ipad","airpods"}[i-1] + ".png";
                java.net.URL u = getClass().getResource(ruta);
                if (u != null) {
                    ImageView iv = new ImageView(new Image(u.toExternalForm()));
                    iv.setFitWidth(50); iv.setFitHeight(50);
                    iv.setPreserveRatio(true);
                    thumb.getChildren().add(iv);
                }
            } catch (Exception ex) {/* ignorar */}
            miniGaleria.getChildren().add(thumb);
        }

        Button btnGuardarCambios = crearBotonForm("Guardar cambios", "#111111", "white");
        Button btnActualizar     = crearBotonForm("Actualizar", "#111111", "white");
        btnGuardarCambios.setOnAction(e -> guardarProducto(false));
        btnActualizar.setOnAction(e -> guardarProducto(true));
        HBox btnEstRow = new HBox(10, btnGuardarCambios, btnActualizar);

        cardEstado.getChildren().addAll(
            lblEstadoTit, filaEstado,
            lblEtiq, etiquetasRow,
            miniGaleria,
            btnEstRow
        );

        // === SEO ===
        VBox cardSeo = crearCard();
        Label lblSeoTit = new Label("SEO");
        lblSeoTit.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));

        campoSeoTitulo = crearCampoTexto("Meta-título del producto");
        campoSeoTitulo.setStyle(
            campoSeoTitulo.getStyle() +
            "-fx-background-color: #111111;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: #888888;"
        );

        campoSeoDesc = new TextArea();
        campoSeoDesc.setPromptText("Meta-descripción del producto...");
        campoSeoDesc.setPrefRowCount(3);
        campoSeoDesc.setWrapText(true);
        campoSeoDesc.setStyle(
            "-fx-background-color: #111111;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: #888888;" +
            "-fx-background-radius: 8;" +
            "-fx-font-size: 12px;" +
            "-fx-padding: 8;"
        );

        cardSeo.getChildren().addAll(lblSeoTit, campoSeoTitulo, campoSeoDesc);

        panel.getChildren().addAll(cardDatos, cardImagen, cardEstado, cardSeo);
        return panel;
    }

    // =========================================================
    //  FOOTER
    // =========================================================
    private HBox construirFooter() {
        HBox footer = new HBox(30);
        footer.setPadding(new Insets(12, 20, 12, 20));
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #E0E0E0;" +
            "-fx-border-width: 1 0 0 0;"
        );
        String[] links = {"Pedidos", "Historial", "Envíos", "Devoluciones", "Pagos"};
        for (String l : links) {
            Label lbl = new Label(l);
            lbl.setFont(Font.font("Segoe UI", 13));
            lbl.setTextFill(Color.GRAY);
            footer.getChildren().add(lbl);
        }
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label ayuda = new Label("Ayuda  Privacidad  Términos");
        ayuda.setFont(Font.font("Segoe UI", 13));
        ayuda.setTextFill(Color.GRAY);
        footer.getChildren().addAll(spacer, ayuda);
        return footer;
    }

    // =========================================================
    //  SELECCIONAR IMAGEN
    // =========================================================
    private void seleccionarImagen() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar imagen del producto");
        chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        File archivo = chooser.showOpenDialog(stage);
        if (archivo != null) {
            rutaImagenSeleccionada = archivo.toURI().toString();
            Image img = new Image(rutaImagenSeleccionada);
            imagenDestacada.setImage(img);

            // Agregar miniatura a la galería
            ImageView miniatura = new ImageView(img);
            miniatura.setFitWidth(90);
            miniatura.setFitHeight(90);
            miniatura.setPreserveRatio(true);

            StackPane thumb = new StackPane(miniatura);
            thumb.setStyle(
                "-fx-background-color: #F0F0F0;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #DDDDDD;" +
                "-fx-border-radius: 8;"
            );
            thumb.setPrefSize(100, 100);

            // Limpiar placeholder si es la primera imagen
            galeria.getChildren().removeIf(n -> n instanceof Label);
            galeria.getChildren().add(thumb);
        }
    }

    // =========================================================
    //  GUARDAR PRODUCTO
    // =========================================================
    private void guardarProducto(boolean publicar) {

        // === VALIDACIONES ===
        if (!validarCampos()) return;

        // === CONSTRUIR OBJETO ===
        Producto p = new Producto();

        if (productoAEditar != null) {
            p.setId(productoAEditar.getId());
        } else {
            p.setId(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        p.setTitulo(campoTitulo.getText().trim());
        p.setSubtitulo(campoSubtitulo.getText().trim());
        p.setDescripcion(campoDescripcion.getText().trim());
        p.setCategoria(comboCat.getValue());
        p.setCondicion(comboCondicion.getValue());
        p.setMarca(comboMarca.getValue());
        p.setSku(campoSku.getText().trim());

        try {
            p.setPrecio(Double.parseDouble(campoPrecio.getText().trim()));
        } catch (NumberFormatException ex) {
            p.setPrecio(0);
        }

        p.setMoneda(comboMoneda.getValue());

        try {
            p.setStockMinimo(Integer.parseInt(campoStockMin.getText().trim()));
            p.setStockActual(Integer.parseInt(campoStock.getText().trim()));
        } catch (NumberFormatException ex) {
            p.setStockMinimo(0);
            p.setStockActual(0);
        }

        try {
            p.setPeso(Double.parseDouble(campoPeso.getText().trim()));
            p.setAncho(Double.parseDouble(campoAncho.getText().trim()));
            p.setAlto(Double.parseDouble(campoAlto.getText().trim()));
        } catch (NumberFormatException ex) {
            p.setPeso(0); p.setAncho(0); p.setAlto(0);
        }

        p.setVisible(chkVisible.isSelected());
        p.setDestacado(chkDestacado.isSelected());
        p.setEstado(publicar ? "Publicado" : comboEstado.getValue());
        p.setRutaImagen(rutaImagenSeleccionada.isEmpty()
            ? (productoAEditar != null ? productoAEditar.getRutaImagen() : "")
            : rutaImagenSeleccionada);
        p.setSeoTitulo(campoSeoTitulo.getText().trim());
        p.setSeoDescripcion(campoSeoDesc.getText().trim());
        p.setMetodoEnvio(comboEnvio.getValue());

        try {
            p.setCostoEnvio(Double.parseDouble(campoCostoEnvio.getText().trim()));
            p.setEnvioGratisDesde(Double.parseDouble(campoGratisDesde.getText().trim()));
        } catch (NumberFormatException ex) {
            p.setCostoEnvio(0); p.setEnvioGratisDesde(0);
        }

        p.setRequiereFirma(chkFirma.isSelected());
        p.setPermiteDevoluciones(chkDevoluciones.isSelected());

        // === PERSISTIR ===
        if (productoAEditar == null) {
            gestor.agregar(p);
            mostrarExito("Producto agregado correctamente.",
                "\"" + p.getTitulo() + "\" fue guardado en el sistema.");
        } else {
            gestor.editar(p);
            mostrarExito("Producto actualizado.",
                "Los cambios en \"" + p.getTitulo() + "\" fueron guardados.");
        }

        panelAdmin.actualizarTabla();
        stage.close();
    }

    // =========================================================
    //  VALIDACIONES
    // =========================================================
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (campoTitulo.getText().trim().isEmpty())
            errores.append("• El título del producto es obligatorio.\n");
        if (campoSku.getText().trim().isEmpty())
            errores.append("• El SKU es obligatorio.\n");
        if (campoPrecio.getText().trim().isEmpty()) {
            errores.append("• El precio es obligatorio.\n");
        } else {
            try { Double.parseDouble(campoPrecio.getText().trim()); }
            catch (NumberFormatException ex) {
                errores.append("• El precio debe ser un número válido.\n");
            }
        }
        if (campoStock.getText().trim().isEmpty()) {
            errores.append("• El stock es obligatorio.\n");
        } else {
            try { Integer.parseInt(campoStock.getText().trim()); }
            catch (NumberFormatException ex) {
                errores.append("• El stock debe ser un número entero.\n");
            }
        }

        if (errores.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errores de validación");
            alert.setHeaderText("Por favor corrige los siguientes campos:");
            alert.setContentText(errores.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // =========================================================
    //  PRE-CARGAR CAMPOS (modo edición)
    // =========================================================
    private void preCargarCampos(Producto p) {
        campoTitulo.setText(p.getTitulo());
        campoSubtitulo.setText(p.getSubtitulo() != null ? p.getSubtitulo() : "");
        campoDescripcion.setText(p.getDescripcion() != null ? p.getDescripcion() : "");

        if (comboCat.getItems().contains(p.getCategoria()))
            comboCat.setValue(p.getCategoria());
        if (comboCondicion.getItems().contains(p.getCondicion()))
            comboCondicion.setValue(p.getCondicion());
        if (comboMarca.getItems().contains(p.getMarca()))
            comboMarca.setValue(p.getMarca());

        campoSku.setText(p.getSku());
        campoPrecio.setText(String.valueOf(p.getPrecio()));

        if (comboMoneda.getItems().contains(p.getMoneda()))
            comboMoneda.setValue(p.getMoneda());

        campoStockMin.setText(String.valueOf(p.getStockMinimo()));
        campoStock.setText(String.valueOf(p.getStockActual()));
        campoPeso.setText(String.valueOf(p.getPeso()));
        campoAncho.setText(String.valueOf(p.getAncho()));
        campoAlto.setText(String.valueOf(p.getAlto()));
        chkVisible.setSelected(p.isVisible());
        chkDestacado.setSelected(p.isDestacado());

        if (comboEstado.getItems().contains(p.getEstado()))
            comboEstado.setValue(p.getEstado());

        if (p.getSeoTitulo() != null) campoSeoTitulo.setText(p.getSeoTitulo());
        if (p.getSeoDescripcion() != null) campoSeoDesc.setText(p.getSeoDescripcion());

        if (comboEnvio.getItems().contains(p.getMetodoEnvio()))
            comboEnvio.setValue(p.getMetodoEnvio());

        campoCostoEnvio.setText(String.valueOf(p.getCostoEnvio()));
        campoGratisDesde.setText(String.valueOf(p.getEnvioGratisDesde()));
        chkFirma.setSelected(p.isRequiereFirma());
        chkDevoluciones.setSelected(p.isPermiteDevoluciones());

        // Cargar imagen si existe
        if (p.getRutaImagen() != null && !p.getRutaImagen().isEmpty()) {
            try {
                String ruta = p.getRutaImagen();
                Image img;
                if (ruta.startsWith("/com/corestore/")) {
                    java.net.URL url = getClass().getResource(ruta);
                    img = url != null ? new Image(url.toExternalForm()) : null;
                } else {
                    img = new Image(ruta);
                }
                if (img != null) imagenDestacada.setImage(img);
            } catch (Exception ex) {/* ignorar */}
        }
    }

    // =========================================================
    //  HELPERS DE UI
    // =========================================================
    private VBox crearCard() {
        VBox card = new VBox(14);
        card.setPadding(new Insets(22));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #E5E5E5;" +
            "-fx-border-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 8, 0, 0, 2);"
        );
        return card;
    }

    private TextField crearCampoTexto(String placeholder) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
        tf.setMaxWidth(Double.MAX_VALUE);
        tf.setStyle(
            "-fx-background-radius: 8;" +
            "-fx-border-color: #DDDDDD;" +
            "-fx-border-radius: 8;" +
            "-fx-padding: 10 12 10 12;" +
            "-fx-font-size: 13px;"
        );
        return tf;
    }

    private Label etiquetaFormulario(String texto) {
        Label lbl = new Label(texto);
        lbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        lbl.setTextFill(Color.rgb(60, 60, 60));
        return lbl;
    }

    private VBox columnaLabel(String titulo, Node control) {
        VBox vbox = new VBox(5);
        if (!titulo.isEmpty()) {
            Label lbl = new Label(titulo);
            lbl.setFont(Font.font("Segoe UI", 12));
            lbl.setTextFill(Color.GRAY);
            vbox.getChildren().add(lbl);
        }
        vbox.getChildren().add(control);
        return vbox;
    }

    private Button crearBotonForm(String texto, String bg, String fg) {
        Button btn = new Button(texto);
        btn.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-text-fill: " + fg + ";" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 10 20 10 20;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    private VBox crearMiniStat(String titulo, String valor) {
        Label lbl1 = new Label(titulo);
        lbl1.setFont(Font.font("Segoe UI", 11));
        lbl1.setTextFill(Color.GRAY);
        Label lbl2 = new Label(valor);
        lbl2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        VBox box = new VBox(2, lbl1, lbl2);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #F5F5F5; -fx-background-radius: 8;");
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setPrefWidth(120);
        return box;
    }

    private Label crearChip(String texto) {
        Label lbl = new Label(texto);
        lbl.setFont(Font.font("Segoe UI", 12));
        lbl.setPadding(new Insets(4, 12, 4, 12));
        lbl.setStyle(
            "-fx-background-color: #EEEEEE;" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: #444444;"
        );
        return lbl;
    }

    private void mostrarExito(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}