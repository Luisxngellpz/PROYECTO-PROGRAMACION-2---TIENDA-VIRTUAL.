package com.corestore;

// ===== IMPORTACIONES =====
import com.corestore.admin.PanelAdmin;
import com.corestore.animaciones.CarritoAnimacion;
import com.corestore.ventanas.VentanaCarrito;
import com.corestore.ventanas.VentanaOfertas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.stage.Stage;

import java.net.URL;

public class Catalogo extends Application {

    public static VBox favoritosBox = new VBox(20);
    public static TilePane favoritosPane = new TilePane();

    // ===== VARIABLES =====
    public static int carritoCantidad = 0;

    private int favoritosCantidad = 0;

    public static Button carritoBtn;

    private Button favoritosBtn;

    private TilePane productos;

    @Override
    public void start(Stage stage) {

        // ===== ROOT =====
        BorderPane root = new BorderPane();

        root.setStyle("-fx-background-color:#f5f5f5;");

        // ===== LOGO =====
        Label logo = new Label("🏬 CoreStore");

        logo.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        // ===== MENU =====
        Button inicio = crearBotonMenu("Inicio");

        Button catalogo = crearBotonMenu("Catálogo");

        Button ofertas = crearBotonMenu("Ofertas");

        Button soporte = crearBotonMenu("Soporte");

        ofertas.setOnAction(e ->
                VentanaOfertas.mostrar(this)
        );

        HBox menu = new HBox(
                20,
                inicio,
                catalogo,
                ofertas,
                soporte
        );

        menu.setAlignment(Pos.CENTER_LEFT);

        // ===== BUSCADOR =====
        TextField buscador = new TextField();

        buscador.setPromptText("Buscar productos...");

        buscador.setPrefSize(420, 42);

        buscador.setStyle(
                "-fx-background-color:#f0f0f0;" +
                "-fx-background-radius:18;" +
                "-fx-padding:0 15 0 15;"
        );

        Button buscar = new Button("Buscar");

        buscar.setStyle(
                "-fx-background-color:black;" +
                "-fx-text-fill:white;"
        );

        // ===== FAVORITOS =====
        favoritosBtn = new Button("❤ 0");

        favoritosBtn.setStyle(
                "-fx-background-color:transparent;" +
                "-fx-font-size:15px;" +
                "-fx-font-weight:bold;"
        );

        favoritosBtn.setOnAction(e -> {

            ListaDeseos lista = new ListaDeseos();

            lista.mostrar();

            favoritosBtn.setText(
                    "❤ " + ListaDeseos.favoritos.size()
            );
        });

        // ===== CARRITO =====
        carritoBtn = new Button("🛒 0");

        carritoBtn.setStyle(
                "-fx-background-color:transparent;" +
                "-fx-font-size:15px;" +
                "-fx-font-weight:bold;"
        );

        carritoBtn.setOnAction(e -> {

            VentanaCarrito ventana =
                    new VentanaCarrito();

            ventana.mostrar();
        });

        // ===== BOTON ADMIN =====
        Button adminBtn = new Button("🔧 Admin");

        adminBtn.setStyle(
                "-fx-background-color: #111111;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 7 14 7 14;" +
                "-fx-font-size: 13px;" +
                "-fx-cursor: hand;"
        );

        adminBtn.setOnAction(e -> {
            PanelAdmin panel = new PanelAdmin();
            panel.mostrar();
        });

        HBox iconos = new HBox(
                15,
                favoritosBtn,
                carritoBtn,
                adminBtn
        );

        iconos.setAlignment(Pos.CENTER_RIGHT);

        // ===== NAVBAR =====
        BorderPane navbar = new BorderPane();

        navbar.setLeft(logo);

        navbar.setCenter(menu);

        HBox derecha = new HBox(
                15,
                buscador,
                buscar,
                iconos
        );

        derecha.setAlignment(Pos.CENTER_LEFT);

        navbar.setRight(derecha);

        navbar.setPadding(new Insets(15));

        navbar.setStyle(
                "-fx-background-color:white;" +
                "-fx-border-color:#dddddd;"
        );

        // ===== SIDEBAR =====
        VBox sidebar = new VBox(18);

        sidebar.setPadding(new Insets(20));

        sidebar.setPrefWidth(240);

        sidebar.setStyle(
                "-fx-background-color:white;" +
                "-fx-background-radius:15;"
        );

        CheckBox iphone = new CheckBox("iPhone");

        CheckBox macbook = new CheckBox("MacBook");

        CheckBox ipad = new CheckBox("iPad");

        RadioButton nuevo = new RadioButton("Nuevo");

        RadioButton usado = new RadioButton("Usado");

        RadioButton reacondicionado =
                new RadioButton("Reacondicionado");

        ToggleGroup condiciones = new ToggleGroup();

        nuevo.setToggleGroup(condiciones);

        usado.setToggleGroup(condiciones);

        reacondicionado.setToggleGroup(condiciones);

        Slider slider = new Slider(0, 2000, 2000);

        Label precio = new Label("Máximo: €2000");

        slider.valueProperty().addListener((a, b, c) ->
                precio.setText("Máximo: €" + c.intValue())
        );

        Button aplicar = new Button("Aplicar");

        Button limpiar = new Button("Limpiar");

        sidebar.getChildren().addAll(

                new Label("Filtros"),

                new Separator(),

                iphone,
                macbook,
                ipad,

                new Separator(),

                nuevo,
                usado,
                reacondicionado,

                new Separator(),

                precio,

                slider,

                new HBox(10, aplicar, limpiar)
        );

        // ===== TITULO =====
        Label titulo = new Label("Catálogo");

        titulo.setFont(
                Font.font("Arial", FontWeight.BOLD, 34)
        );

        Label subtitulo =
                new Label("12 productos disponibles");

        subtitulo.setTextFill(Color.GRAY);

        VBox header = new VBox(
                5,
                titulo,
                subtitulo
        );

        // ===== PRODUCTOS =====
        productos = new TilePane();

        productos.setPrefColumns(3);

        productos.setPrefTileWidth(360);

        productos.setPrefTileHeight(450);

        productos.setPadding(new Insets(20));

        productos.setHgap(25);

        productos.setVgap(25);

        productos.setPrefHeight(
                Region.USE_COMPUTED_SIZE
        );

        productos.getChildren().addAll(

                crearProducto(
                        "/com/corestore/imag/01_iphone14.png",
                        "iPhone 14 Pro Max 256GB",
                        "€1399",
                        "⭐ 4.8",
                        "Nuevo",
                        true
                ),

                crearProducto(
                        "/com/corestore/imag/02_macbook.png",
                        "MacBook Pro M2 512GB",
                        "€2199",
                        "⭐ 4.9",
                        "Nuevo",
                        false
                ),

                crearProducto(
                        "/com/corestore/imag/03_ipad.png",
                        "iPad Pro 11",
                        "€899",
                        "⭐ 4.7",
                        "Usado",
                        false
                ),

                crearProducto(
                        "/com/corestore/imag/04_airpods.png",
                        "AirPods Pro 2",
                        "€249",
                        "⭐ 4.6",
                        "Nuevo",
                        true
                ),

                crearProducto(
                        "/com/corestore/imag/05_magsafe.png",
                        "Cargador MagSafe",
                        "€45",
                        "⭐ 4.5",
                        "Reacondicionado",
                        false
                ),

                crearProducto(
                        "/com/corestore/imag/06_iphone13.png",
                        "iPhone 13 Mini",
                        "€769",
                        "⭐ 4.4",
                        "Usado",
                        false
                ),

                crearProducto(
                        "/com/corestore/imag/07_keyboard.png",
                        "Smart Keyboard",
                        "€199",
                        "⭐ 4.3",
                        "Reacondicionado",
                        false
                ),

                crearProducto(
                        "/com/corestore/imag/08_battery.png",
                        "MagSafe Battery",
                        "€129",
                        "⭐ 4.2",
                        "Usado",
                        false
                ),

                crearProducto(
                        "/com/corestore/imag/09_watch.png",
                        "Apple Watch Series 8",
                        "€499",
                        "⭐ 4.7",
                        "Nuevo",
                        true
                ),

                crearProducto(
                        "/com/corestore/imag/10_quantum.png",
                        "Auriculares Quantum",
                        "€19",
                        "⭐ 4.5",
                        "Nuevo",
                        false
                ),

                crearProducto(
                        "/com/corestore/imag/11_funda.png",
                        "Funda iPhone",
                        "€79",
                        "⭐ 4.3",
                        "Reacondicionado",
                        false
                ),

                crearProducto(
                        "/com/corestore/imag/12_display.png",
                        "Studio Display 27",
                        "€1799",
                        "⭐ 4.9",
                        "Nuevo",
                        true
                )
        );

        // ===== BUSCADOR =====
        Runnable buscarProductos = () -> {

            String texto =
                    buscador.getText().toLowerCase();

            for (Node nodo : productos.getChildren()) {

                VBox card = (VBox) nodo;

                Label nombre =
                        (Label) card.getChildren().get(2);

                boolean mostrar =
                        nombre.getText()
                                .toLowerCase()
                                .contains(texto);

                card.setVisible(mostrar || texto.isEmpty());

                card.setManaged(mostrar || texto.isEmpty());
            }
        };

        buscar.setOnAction(e -> buscarProductos.run());

        buscador.setOnAction(e -> buscarProductos.run());

        // ===== FILTROS =====
        aplicar.setOnAction(e -> {

            for (Node nodo : productos.getChildren()) {

                VBox card = (VBox) nodo;

                Label nombre =
                        (Label) card.getChildren().get(2);

                Label precioLabel =
                        (Label) card.getChildren().get(4);

                Label condicionLabel =
                        (Label) card.getChildren().get(6);

                String texto =
                        nombre.getText().toLowerCase();

                String condicion =
                        condicionLabel.getText().toLowerCase();

                double valor =
                        Double.parseDouble(
                                precioLabel.getText()
                                        .replace("€", "")
                        );

                boolean mostrar = true;

                if (iphone.isSelected()
                        && !texto.contains("iphone"))
                    mostrar = false;

                if (macbook.isSelected()
                        && !texto.contains("macbook"))
                    mostrar = false;

                if (ipad.isSelected()
                        && !texto.contains("ipad"))
                    mostrar = false;

                if (valor > slider.getValue())
                    mostrar = false;

                if (nuevo.isSelected()
                        && !condicion.contains("nuevo"))
                    mostrar = false;

                if (usado.isSelected()
                        && !condicion.contains("usado"))
                    mostrar = false;

                if (reacondicionado.isSelected()
                        && !condicion.contains("reacondicionado"))
                    mostrar = false;

                card.setVisible(mostrar);

                card.setManaged(mostrar);
            }
        });

        // ===== LIMPIAR =====
        limpiar.setOnAction(e -> {

            iphone.setSelected(false);

            macbook.setSelected(false);

            ipad.setSelected(false);

            condiciones.selectToggle(null);

            slider.setValue(2000);

            buscador.clear();

            for (Node nodo : productos.getChildren()) {

                nodo.setVisible(true);

                nodo.setManaged(true);
            }
        });

        // ===== SCROLL =====
        ScrollPane scroll =
                new ScrollPane(productos);

        scroll.setFitToWidth(true);

        VBox.setVgrow(scroll, Priority.ALWAYS);

        VBox contenido = new VBox(
                25,
                header,
                scroll
        );

        contenido.setPadding(new Insets(20));

        // ===== CENTRO =====
        HBox centro = new HBox(
                30,
                sidebar,
                contenido
        );

        HBox.setHgrow(
                contenido,
                Priority.ALWAYS
        );

        centro.setPadding(new Insets(20));

        // ===== ROOT =====
        root.setTop(navbar);

        root.setCenter(centro);

        // ===== SCENE =====
        Scene scene =
                new Scene(root, 1400, 900);

        stage.setTitle("CoreStore");

        stage.setScene(scene);

        stage.setMaximized(true);

        stage.show();
    }

    // ===== CREAR PRODUCTO =====
    public VBox crearProducto(
            String ruta,
            String nombre,
            String precio,
            String rating,
            String condicion,
            boolean oferta
    ) {

        URL url =
                getClass().getResource(ruta);

        if (url == null) {

            System.out.println(
                    "ERROR imagen: " + ruta
            );

            return new VBox();
        }

        ImageView imagen = new ImageView(
                new Image(url.toExternalForm())
        );

        imagen.setFitWidth(145);

        imagen.setFitHeight(145);

        imagen.setPreserveRatio(true);

        Label ofertaLabel = new Label(
                oferta ? "🔥 EN OFERTA" : ""
        );

        if (oferta) {

            ofertaLabel.setStyle(
                    "-fx-background-color:#ff4d4d;" +
                    "-fx-text-fill:white;" +
                    "-fx-padding:5 10 5 10;"
            );

        } else {

            ofertaLabel.setManaged(false);

            ofertaLabel.setVisible(false);
        }

        Label titulo = new Label(nombre);

        titulo.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        14
                )
        );

        Label descripcion = new Label(
                "Producto original Apple"
        );

        descripcion.setTextFill(Color.GRAY);

        Label valor = new Label(precio);

        valor.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        18
                )
        );

        Label estrellas = new Label(rating);

        Label condicionLabel =
                new Label(condicion);

        // ===== BOTONES =====
        Button ver = new Button("Ver");

        Button favorito = new Button("❤");

        favorito.setOnAction(e -> {

            if (ListaDeseos.favoritos.contains(nombre)) {

                ListaDeseos.favoritos.remove(nombre);

                favoritosCantidad--;

            } else {

                ListaDeseos.favoritos.add(nombre);

                VBox favoritoCard =
                        ListaDeseos.crearProductoFavorito(
                                ruta,
                                nombre,
                                precio,
                                Catalogo.favoritosPane
                        );

                Catalogo.favoritosPane
                        .getChildren()
                        .add(favoritoCard);

                favoritosCantidad++;
            }

            favoritosBtn.setText(
                    "❤ " + favoritosCantidad
            );
        });

        Button carrito = new Button("Añadir");

        carrito.setStyle(
                "-fx-background-color:black;" +
                "-fx-text-fill:white;"
        );

        carrito.setOnAction(e -> {

            ProductoCarrito producto =
                    new ProductoCarrito(
                            ruta,
                            nombre,
                            precio,
                            descripcion.getText(),
                            1
                    );

            VentanaCarrito.carrito.add(producto);

            carritoCantidad++;

            carritoBtn.setText(
                    "🛒 " + carritoCantidad
            );

            CarritoAnimacion.animarBoton(carrito);

            CarritoAnimacion.animarCarrito(
                    carritoBtn
            );
        });

        HBox botones = new HBox(
                10,
                ver,
                favorito,
                carrito
        );

        botones.setAlignment(Pos.CENTER);

        // ===== CARD =====
        VBox card = new VBox(
                10,
                imagen,
                ofertaLabel,
                titulo,
                descripcion,
                valor,
                estrellas,
                condicionLabel,
                botones
        );

        card.setAlignment(Pos.TOP_CENTER);

        card.setPadding(new Insets(14));

        card.setPrefWidth(340);

        card.setMinHeight(470);

        card.setStyle(
                "-fx-background-color:white;" +
                "-fx-background-radius:15;" +
                "-fx-border-color:#dddddd;"
        );

        return card;
    }

    // ===== BOTON MENU =====
    private Button crearBotonMenu(String texto) {

        Button boton = new Button(texto);

        boton.setStyle(
                "-fx-background-color:transparent;"
        );

        return boton;
    }

    // ===== MAIN =====
    public static void main(String[] args) {

        launch(args);
    }
}