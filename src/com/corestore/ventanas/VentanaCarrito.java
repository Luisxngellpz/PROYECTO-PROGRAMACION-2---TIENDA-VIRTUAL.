package com.corestore.ventanas;

import com.corestore.Catalogo;
import com.corestore.ProductoCarrito;

import java.net.URL;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VentanaCarrito {

    // ===== LISTA ESTÁTICA COMPARTIDA CON Catalogo =====
    public static ArrayList<ProductoCarrito> carrito =
            new ArrayList<>();

    // ===== MOSTRAR VENTANA =====
    public void mostrar() {

        Stage stage = new Stage();
        stage.setTitle("Carrito de compras");
        stage.setWidth(520);
        stage.setHeight(620);
        stage.setResizable(false);

        VBox root = new VBox(0);
        root.setStyle("-fx-background-color: #f5f5f5;");

        // ===== HEADER =====
        HBox header = new HBox();
        header.setPadding(new Insets(18, 22, 18, 22));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #dddddd;" +
                "-fx-border-width: 0 0 1 0;"
        );

        Label titulo = new Label("Carrito de compras");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Region espacioHeader = new Region();
        HBox.setHgrow(espacioHeader, Priority.ALWAYS);

        Label lblContador = new Label(carrito.size() + " producto(s)");
        lblContador.setFont(Font.font("Arial", 13));
        lblContador.setTextFill(Color.GRAY);

        header.getChildren().addAll(titulo, espacioHeader, lblContador);

        // ===== LISTA DE PRODUCTOS =====
        VBox listaBox = new VBox(12);
        listaBox.setPadding(new Insets(18, 22, 18, 22));

        for (ProductoCarrito p : carrito) {
            listaBox.getChildren().add(
                    crearFilaProducto(p, listaBox, lblContador)
            );
        }

        if (carrito.isEmpty()) {
            Label vacio = new Label("Tu carrito esta vacio.");
            vacio.setTextFill(Color.GRAY);
            vacio.setFont(Font.font("Arial", 14));
            listaBox.getChildren().add(vacio);
        }

        ScrollPane scroll = new ScrollPane(listaBox);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        // ===== FOOTER =====
        VBox footer = new VBox(12);
        footer.setPadding(new Insets(16, 22, 16, 22));
        footer.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #dddddd;" +
                "-fx-border-width: 1 0 0 0;"
        );

        Label lblTotal = new Label(calcularTotal());
        lblTotal.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Button btnVaciar = new Button("Vaciar carrito");
        btnVaciar.setStyle(
                "-fx-background-color: white;" +
                "-fx-text-fill: #cc2222;" +
                "-fx-border-color: #cc2222;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 9 18 9 18;" +
                "-fx-font-size: 13px;" +
                "-fx-cursor: hand;"
        );
        btnVaciar.setOnAction(e -> {
            carrito.clear();
            listaBox.getChildren().clear();
            Label vacio = new Label("Tu carrito esta vacio.");
            vacio.setTextFill(Color.GRAY);
            listaBox.getChildren().add(vacio);
            Catalogo.carritoCantidad = 0;
            Catalogo.carritoBtn.setText("🛒 0");
            lblTotal.setText(calcularTotal());
            lblContador.setText("0 producto(s)");
        });

        Button btnComprar = new Button("Finalizar compra");
        btnComprar.setStyle(
                "-fx-background-color: black;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 9 18 9 18;" +
                "-fx-font-size: 13px;" +
                "-fx-cursor: hand;"
        );
        btnComprar.setOnAction(e -> {

            if (carrito.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Carrito vacio");
                alert.setHeaderText(null);
                alert.setContentText("Agrega productos antes de finalizar.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra realizada");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Gracias por tu compra.\n" +
                    "Tu pedido esta en camino.\n\n" +
                    lblTotal.getText()
            );
            alert.showAndWait();

            carrito.clear();
            listaBox.getChildren().clear();
            Label vacio = new Label("Tu carrito esta vacio.");
            vacio.setTextFill(Color.GRAY);
            listaBox.getChildren().add(vacio);
            Catalogo.carritoCantidad = 0;
            Catalogo.carritoBtn.setText("🛒 0");
            lblTotal.setText(calcularTotal());
            lblContador.setText("0 producto(s)");
            stage.close();
        });

        HBox btnRow = new HBox(10, btnVaciar, btnComprar);
        btnRow.setAlignment(Pos.CENTER_RIGHT);

        footer.getChildren().addAll(lblTotal, btnRow);

        root.getChildren().addAll(header, scroll, footer);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // ===== FILA DE PRODUCTO =====
    private HBox crearFilaProducto(
            ProductoCarrito p,
            VBox listaBox,
            Label lblContador
    ) {
        HBox fila = new HBox(14);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setPadding(new Insets(10, 14, 10, 14));
        fila.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #eeeeee;" +
                "-fx-border-radius: 10;"
        );

        ImageView img = new ImageView();
        img.setFitWidth(55);
        img.setFitHeight(55);
        img.setPreserveRatio(true);
        try {
            URL url = getClass().getResource(p.getRuta());
            if (url != null) {
                img.setImage(new Image(url.toExternalForm()));
            }
        } catch (Exception ex) { /* sin imagen */ }

        Label lblNombre = new Label(p.getNombre());
        lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblNombre.setWrapText(true);
        lblNombre.setMaxWidth(270);

        Label lblPrecio = new Label(p.getPrecio());
        lblPrecio.setFont(Font.font("Arial", 13));
        lblPrecio.setTextFill(Color.GRAY);

        VBox info = new VBox(4, lblNombre, lblPrecio);
        HBox.setHgrow(info, Priority.ALWAYS);

        Button btnQuitar = new Button("X");
        btnQuitar.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #cc2222;" +
                "-fx-font-size: 15px;" +
                "-fx-cursor: hand;"
        );
        btnQuitar.setOnAction(e -> {
            carrito.remove(p);
            listaBox.getChildren().remove(fila);
            Catalogo.carritoCantidad--;
            if (Catalogo.carritoCantidad < 0)
                Catalogo.carritoCantidad = 0;
            Catalogo.carritoBtn.setText(
                    String.valueOf(Catalogo.carritoCantidad)
            );
            lblContador.setText(carrito.size() + " producto(s)");
            if (carrito.isEmpty()) {
                Label vacio = new Label("Tu carrito esta vacio.");
                vacio.setTextFill(Color.GRAY);
                listaBox.getChildren().add(vacio);
            }
        });

        fila.getChildren().addAll(img, info, btnQuitar);
        return fila;
    }

    // ===== CALCULAR TOTAL =====
    private String calcularTotal() {
        double total = 0;
        for (ProductoCarrito p : carrito) {
            try {
                String val = p.getPrecio()
                        .replace("EUR", "")
                        .replace("€", "")
                        .replace(",", ".")
                        .trim();
                total += Double.parseDouble(val) * p.getCantidad();
            } catch (NumberFormatException ex) { /* ignorar */ }
        }
        return String.format("Total: EUR %.2f", total);
    }
}