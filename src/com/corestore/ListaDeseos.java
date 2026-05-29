/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.corestore;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
/**
 *
 * @author User
 */
public class ListaDeseos{
    
    public static ArrayList<String> favoritos =
        new ArrayList<>();
    

 public void mostrar() {

    Stage stage = new Stage();
        // ===== ROOT =====
        BorderPane root = new BorderPane();

        root.setStyle("-fx-background-color:#f5f5f5;");

        // ===== LOGO =====
        Label logo = new Label("🏬 CoreStore");

        logo.setStyle(
                "-fx-text-fill:white;" +
                "-fx-font-size:28px;" +
                "-fx-font-weight:bold;"
        );

        // ===== BOTONES =====
        Button catalogo = new Button("Catálogo");
        catalogo.setOnAction(e -> {

    try {

        Stage nuevaVentana = new Stage();
        new Catalogo().start(nuevaVentana);

        stage.close();

    } catch (Exception ex) {

        ex.printStackTrace();
    }
});

        Button carrito = new Button("Carrito");

        Button historial = new Button("Historial");

        catalogo.setStyle(
                "-fx-background-color:#222;" +
                "-fx-text-fill:white;"
        );

        carrito.setStyle(
                "-fx-background-color:#222;" +
                "-fx-text-fill:white;"
        );

        historial.setStyle(
                "-fx-background-color:#222;" +
                "-fx-text-fill:white;"
        );

        // ===== BUSCADOR =====
        TextField buscador = new TextField();

        buscador.setPromptText("Buscar en CoreStore...");

        buscador.setPrefWidth(400);

        // ===== MENU =====
        HBox menu = new HBox(
                20,
                catalogo,
                carrito,
                historial
        );

        menu.setAlignment(Pos.CENTER);

        // ===== NAVBAR =====
        HBox navbar = new HBox(
                25,
                logo,
                menu,
                buscador
        );

        navbar.setAlignment(Pos.CENTER_LEFT);

        navbar.setStyle(
                "-fx-background-color:black;" +
                "-fx-padding:20px;"
        );
       // ===== TITULO =====
Label titulo = new Label("❤ Lista de Deseos");

titulo.setStyle( 
        "-fx-font-size:30px;" +
        "-fx-font-weight:bold;"
);

// ===== PANEL PRODUCTOS =====
TilePane productos = Catalogo.favoritosPane;

productos.setHgap(20);

productos.setVgap(20);

productos.setPadding(new Insets(20));

productos.setPrefColumns(4);

// ===== SCROLL =====
ScrollPane scroll = new ScrollPane(productos);

scroll.setFitToWidth(true);

// ===== CONTENIDO =====
VBox contenido = new VBox(
        20,
        titulo,
        scroll
);

contenido.setPadding(new Insets(20));
        // ===== ROOT =====
        root.setTop(navbar);

        root.setCenter(contenido);

        // ===== ESCENA =====
        Scene scene = new Scene(root, 1400, 900);

        stage.setTitle("Lista de Deseos");

        stage.setScene(scene);
        stage.setWidth(1400);
        stage.setHeight(900);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }
 // ===== CREAR PRODUCTO FAVORITO =====
public static VBox crearProductoFavorito(
        String ruta,
        String nombre,
        String precio,
        TilePane productos
) {

    URL url = ListaDeseos.class.getResource(ruta);

    if (url == null) {
        System.out.println("NO SE ENCONTRO: " + ruta);
        return new VBox();
    }

    ImageView imagen = new ImageView(new Image(url.toExternalForm()));
    imagen.setFitWidth(150);
    imagen.setFitHeight(150);
    imagen.setPreserveRatio(true);

    Label titulo = new Label(nombre);
    titulo.setStyle("-fx-font-size:18px;-fx-font-weight:bold;");

    Label valor = new Label(precio);
    valor.setStyle("-fx-font-size:22px;-fx-font-weight:bold;");

    Button eliminar = new Button("Eliminar");

    HBox botones = new HBox(10, eliminar);

    VBox card = new VBox(
            15,
            imagen,
            titulo,
            valor,
            botones
    );

    eliminar.setOnAction(e -> {
        productos.getChildren().remove(card);
    });

    card.setAlignment(Pos.CENTER);
    card.setPadding(new Insets(20));
    card.setPrefWidth(300);
    card.setStyle(
            "-fx-background-color:white;" +
            "-fx-background-radius:15;" +
            "-fx-border-radius:15;" +
            "-fx-border-color:#dddddd;"
    );

    return card;
}
}