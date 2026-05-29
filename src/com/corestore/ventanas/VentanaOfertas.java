package com.corestore.ventanas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import com.corestore.Catalogo;

public class VentanaOfertas {

    // ===== MOSTRAR VENTANA =====
    public static void mostrar(Catalogo catalogo) {

        Stage ventana = new Stage();

        // ===== PANEL PRODUCTOS =====
        TilePane productos = new TilePane();

        productos.setPrefColumns(3);

        productos.setHgap(20);

        productos.setVgap(20);

        productos.setPadding(new Insets(20));

        productos.setPrefWidth(1100);

        // ===== PRODUCTOS EN OFERTA =====
        productos.getChildren().addAll(

                catalogo.crearProducto(
                        "/com/corestore/imag/01_iphone14.png",
                        "iPhone 14 Pro Max 256GB",
                        "€1399",
                        "⭐ 4.8",
                        "Nuevo",
                        true
                ),

                catalogo.crearProducto(
                        "/com/corestore/imag/04_airpods.png",
                        "AirPods Pro 2",
                        "€249",
                        "⭐ 4.6",
                        "Nuevo",
                        true
                ),

                catalogo.crearProducto(
                        "/com/corestore/imag/09_watch.png",
                        "Apple Watch Series 8",
                        "€499",
                        "⭐ 4.7",
                        "Nuevo",
                        true
                ),

                catalogo.crearProducto(
                        "/com/corestore/imag/12_display.png",
                        "Studio Display 27",
                        "€1799",
                        "⭐ 4.9",
                        "Nuevo",
                        true
                )
        );

        // ===== SCROLL =====
        ScrollPane scroll = new ScrollPane(productos);

        scroll.setFitToWidth(true);

        // ===== MOSTRAR BARRA VERTICAL =====
        scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        // ===== OCULTAR BARRA HORIZONTAL =====
        scroll.setHbarPolicy(ScrollBarPolicy.NEVER);

        scroll.setStyle(
                "-fx-background:white;"
        );

        // ===== ESCENA =====
        Scene scene = new Scene(scroll, 1200, 700);

        ventana.setTitle("Productos en Oferta");

        ventana.setScene(scene);

        ventana.show();
    }
}