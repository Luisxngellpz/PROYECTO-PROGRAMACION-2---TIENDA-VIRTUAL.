package com.corestore.admin;

import com.corestore.modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * =========================================================
 *  CLASE: PanelAdmin
 * =========================================================
 *  Es la ventana principal del módulo administrativo.
 *  Muestra:
 *    - Navbar con logo, menú y botones
 *    - Sidebar con datos rápidos e historial
 *    - Tabla principal con todos los productos
 *    - Botones: Nuevo Producto, Editar, Eliminar
 *
 *  Se abre desde el Catalogo.java principal cuando
 *  el usuario hace click en "Admin" (botón que agregaremos).
 * =========================================================
 */
public class PanelAdmin {

    // ===== GESTOR (Singleton) =====
    private final GestorProductos gestor = GestorProductos.getInstance();

    // ===== TABLA =====
    private TableView<Producto> tabla;
    private ObservableList<Producto> datosTabla;

    // ===== LABELS ESTADÍSTICAS =====
    private Label labelTotalProductos;
    private Label labelHistorial;

    // ===== ESCENA =====
    private Stage stage;

    // =========================================================
    //  MOSTRAR PANEL ADMIN
    // =========================================================
    public void mostrar() {
        stage = new Stage();
        stage.setTitle("CoreStore — Panel Administrativo");
        stage.setMaximized(true);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // Construir secciones
        root.setTop(construirNavbar());
        root.setLeft(construirSidebar());
        root.setCenter(construirContenido());
        root.setBottom(construirFooter());

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

        // Logo
        Label logo = new Label("■ CoreStore");
        logo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        logo.setTextFill(Color.WHITE);

        // Menú central
        HBox menu = new HBox(25);
        menu.setAlignment(Pos.CENTER);
        String[] opciones = {"Panel", "Productos", "Pedidos", "Clientes", "Marketing"};
        for (String op : opciones) {
            Label item = new Label(op);
            item.setFont(Font.font("Segoe UI", 14));
            item.setTextFill(op.equals("Productos")
                ? Color.WHITE
                : Color.rgb(180, 180, 180));
            if (op.equals("Productos")) {
                item.setStyle("-fx-border-color: transparent transparent white transparent;" +
                              "-fx-border-width: 0 0 2 0; -fx-padding: 0 0 3 0;");
            }
            menu.getChildren().add(item);
        }

        // Derecha
        TextField busqueda = new TextField();
        busqueda.setPromptText("Buscar en catálogo, pedidos, clientes...");
        busqueda.setPrefWidth(300);
        busqueda.setStyle(
            "-fx-background-color: #222222;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: #888888;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 12 8 12;"
        );

        Button btnGuardar = new Button("💾 Guardar borrador");
        btnGuardar.setStyle(
            "-fx-background-color: #111111;" +
            "-fx-text-fill: white;" +
            "-fx-border-color: #555555;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 14 8 14;" +
            "-fx-font-size: 13px;"
        );

        Button btnOpciones = new Button("•••");
        btnOpciones.setStyle(
            "-fx-background-color: #222222;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8 12 8 12;" +
            "-fx-font-size: 14px;"
        );

        HBox derecha = new HBox(10, busqueda, btnGuardar, btnOpciones);
        derecha.setAlignment(Pos.CENTER_RIGHT);

        navbar.setLeft(logo);
        navbar.setCenter(menu);
        navbar.setRight(derecha);

        return navbar;
    }

    // =========================================================
    //  SIDEBAR DERECHO (Datos rápidos + Estado + SEO)
    // =========================================================
    private VBox construirSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPrefWidth(280);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: white;" +
                         "-fx-border-color: #E0E0E0;" +
                         "-fx-border-width: 0 0 0 1;");

        // === DATOS RÁPIDOS ===
        Label tituloDatos = new Label("Datos rápidos");
        tituloDatos.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

        HBox statsRow = new HBox(10);
        VBox ventasBox = crearStatCard("Ventas 30d", "187");
        VBox devolBox  = crearStatCard("Devoluciones", "3");
        statsRow.getChildren().addAll(ventasBox, devolBox);

        // Total productos (dinámico)
        labelTotalProductos = new Label("Total: " + gestor.totalProductos() + " productos");
        labelTotalProductos.setFont(Font.font("Segoe UI", 12));
        labelTotalProductos.setTextFill(Color.GRAY);

        Separator sep1 = new Separator();

        // === ESTADO Y ETIQUETAS ===
        Label tituloEstado = new Label("Estado y etiquetas");
        tituloEstado.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));

        HBox estadoRow = new HBox(8);
        Label labelEstado = new Label("Estado:");
        labelEstado.setFont(Font.font("Segoe UI", 13));
        ComboBox<String> comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll("Borrador", "Publicado", "Archivado");
        comboEstado.setValue("Borrador");
        comboEstado.setStyle("-fx-background-radius: 6; -fx-font-size: 13px;");
        estadoRow.getChildren().addAll(labelEstado, comboEstado);
        estadoRow.setAlignment(Pos.CENTER_LEFT);

        Label labelEtiquetas = new Label("Etiquetas");
        labelEtiquetas.setFont(Font.font("Segoe UI", 13));
        HBox etiquetasRow = new HBox(8);
        etiquetasRow.getChildren().addAll(
            crearEtiqueta("Reacondicionado"),
            crearEtiqueta("Usado")
        );

        Separator sep2 = new Separator();

        // === HISTORIAL DE ACCIONES ===
        Label tituloHistorial = new Label("Historial reciente");
        tituloHistorial.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));

        labelHistorial = new Label(obtenerResumenHistorial());
        labelHistorial.setWrapText(true);
        labelHistorial.setFont(Font.font("Segoe UI", 12));
        labelHistorial.setTextFill(Color.GRAY);

        Separator sep3 = new Separator();

        // === SEO ===
        Label tituloSeo = new Label("SEO");
        tituloSeo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));

        Label seoTitulo = new Label("Meta-título: Gestión de Productos CoreStore");
        seoTitulo.setWrapText(true);
        seoTitulo.setFont(Font.font("Segoe UI", 12));
        seoTitulo.setStyle(
            "-fx-background-color: #111111;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 8 10 8 10;" +
            "-fx-background-radius: 6;"
        );

        Label seoDesc = new Label("Meta-descripción: Panel de administración. Gestiona productos, categorías y stock.");
        seoDesc.setWrapText(true);
        seoDesc.setFont(Font.font("Segoe UI", 12));
        seoDesc.setStyle(
            "-fx-background-color: #111111;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 8 10 8 10;" +
            "-fx-background-radius: 6;"
        );

        sidebar.getChildren().addAll(
            tituloDatos, statsRow, labelTotalProductos, sep1,
            tituloEstado, estadoRow, labelEtiquetas, etiquetasRow, sep2,
            tituloHistorial, labelHistorial, sep3,
            tituloSeo, seoTitulo, seoDesc
        );

        return sidebar;
    }

    // =========================================================
    //  CONTENIDO PRINCIPAL (Tabla de productos)
    // =========================================================
    private VBox construirContenido() {
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(25));

        // === ENCABEZADO ===
        Label titulo = new Label("Gestión de Productos");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));

        Label subtitulo = new Label(gestor.totalProductos() + " productos registrados");
        subtitulo.setFont(Font.font("Segoe UI", 14));
        subtitulo.setTextFill(Color.GRAY);

        VBox header = new VBox(4, titulo, subtitulo);

        // === BARRA DE ACCIONES ===
        HBox acciones = new HBox(12);
        acciones.setAlignment(Pos.CENTER_LEFT);

        TextField campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar producto por nombre, SKU...");
        campoBusqueda.setPrefWidth(320);
        campoBusqueda.setStyle(
            "-fx-background-radius: 8;" +
            "-fx-border-color: #DDDDDD;" +
            "-fx-border-radius: 8;" +
            "-fx-padding: 9 12 9 12;" +
            "-fx-font-size: 13px;"
        );

        Button btnBuscar = crearBoton("🔍 Buscar", "#111111", "white");
        Button btnNuevo  = crearBoton("+ Nuevo Producto", "#111111", "white");
        Button btnEditar = crearBoton("✎ Editar", "#444444", "white");
        Button btnElim   = crearBoton("✖ Eliminar", "#CC2222", "white");

        acciones.getChildren().addAll(campoBusqueda, btnBuscar, btnNuevo, btnEditar, btnElim);

        // === TABLA ===
        tabla = new TableView<>();
        tabla.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: #E5E5E5;" +
            "-fx-border-radius: 10;" +
            "-fx-font-size: 13px;"
        );
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabla, Priority.ALWAYS);

        // Columnas
        tabla.getColumns().addAll(
            crearColumna("SKU",       "sku",        100),
            crearColumna("Título",    "titulo",     250),
            crearColumna("Categoría", "categoria",  120),
            crearColumna("Condición", "condicion",  120),
            crearColumna("Precio",    "precio",      90),
            crearColumna("Stock",     "stockActual",  80),
            crearColumna("Estado",    "estado",      100),
            crearColumna("Visible",   "visible",      80)
        );

        // Datos
        datosTabla = FXCollections.observableArrayList(gestor.obtenerTodos());
        tabla.setItems(datosTabla);

        // === EVENTOS ===

        // Buscar
        btnBuscar.setOnAction(e -> {
            String txt = campoBusqueda.getText().trim();
            if (txt.isEmpty()) {
                datosTabla.setAll(gestor.obtenerTodos());
            } else {
                datosTabla.setAll(gestor.buscarPorTexto(txt));
            }
        });

        campoBusqueda.setOnAction(e -> btnBuscar.fire());

        // Nuevo Producto → abre FormularioProducto
        btnNuevo.setOnAction(e -> {
            FormularioProducto form = new FormularioProducto(null, this);
            form.mostrar();
        });

        // Editar → abre FormularioProducto con datos pre-cargados
        btnEditar.setOnAction(e -> {
            Producto seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarAlerta("Selección requerida",
                    "Por favor selecciona un producto de la tabla para editar.");
                return;
            }
            FormularioProducto form = new FormularioProducto(seleccionado, this);
            form.mostrar();
        });

        // Eliminar
        btnElim.setOnAction(e -> {
            Producto seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarAlerta("Selección requerida",
                    "Por favor selecciona un producto de la tabla para eliminar.");
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar eliminación");
            confirm.setHeaderText("¿Eliminar \"" + seleccionado.getTitulo() + "\"?");
            confirm.setContentText("Esta acción no se puede deshacer.");
            confirm.showAndWait().ifPresent(resp -> {
                if (resp == ButtonType.OK) {
                    gestor.eliminar(seleccionado.getSku());
                    actualizarTabla();
                }
            });
        });

        contenido.getChildren().addAll(header, acciones, tabla);
        return contenido;
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
    //  ACTUALIZAR TABLA Y SIDEBAR (llamado después de CRUD)
    // =========================================================
    public void actualizarTabla() {
        datosTabla.setAll(gestor.obtenerTodos());
        labelTotalProductos.setText("Total: " + gestor.totalProductos() + " productos");
        labelHistorial.setText(obtenerResumenHistorial());
    }

    // =========================================================
    //  HELPERS DE UI
    // =========================================================
    private VBox crearStatCard(String titulo, String valor) {
        Label lbl1 = new Label(titulo);
        lbl1.setFont(Font.font("Segoe UI", 11));
        lbl1.setTextFill(Color.GRAY);

        Label lbl2 = new Label(valor);
        lbl2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));

        VBox box = new VBox(2, lbl1, lbl2);
        box.setPadding(new Insets(12));
        box.setStyle(
            "-fx-background-color: #F5F5F5;" +
            "-fx-background-radius: 8;"
        );
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setPrefWidth(115);
        return box;
    }

    private Label crearEtiqueta(String texto) {
        Label lbl = new Label(texto);
        lbl.setFont(Font.font("Segoe UI", 12));
        lbl.setPadding(new Insets(4, 10, 4, 10));
        lbl.setStyle(
            "-fx-background-color: #EEEEEE;" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: #444444;"
        );
        return lbl;
    }

    private Button crearBoton(String texto, String bg, String fg) {
        Button btn = new Button(texto);
        btn.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-text-fill: " + fg + ";" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 9 18 9 18;" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;"
        );
        return btn;
    }

    @SuppressWarnings("unchecked")
    private <T> TableColumn<Producto, T> crearColumna(
            String titulo, String propiedad, double ancho) {
        TableColumn<Producto, T> col = new TableColumn<>(titulo);
        col.setCellValueFactory(new PropertyValueFactory<>(propiedad));
        col.setPrefWidth(ancho);
        col.setStyle("-fx-alignment: CENTER-LEFT;");
        return col;
    }

    private String obtenerResumenHistorial() {
        java.util.List<String> h = gestor.obtenerHistorial();
        if (h.isEmpty()) return "Sin acciones aún.";
        StringBuilder sb = new StringBuilder();
        int max = Math.min(5, h.size());
        for (int i = 0; i < max; i++) {
            sb.append(h.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}


