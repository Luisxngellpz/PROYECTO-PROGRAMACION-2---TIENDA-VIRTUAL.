package com.corestore.ventanas;

import com.corestore.ProductoCarrito;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaCarrito {

    public static ArrayList<ProductoCarrito> carrito =
            new ArrayList<>();

    public void mostrar() {

        Stage stage = new Stage();

        VBox root = new VBox(10);

        root.setPadding(new Insets(20));

        Label titulo =
                new Label("Carrito de Compras");

        root.getChildren().add(titulo);

        for (ProductoCarrito p : carrito) {

            Label item = new Label(

                    p.getNombre()
                    + " - "
                    + p.getPrecio()

            );

            root.getChildren().add(item);
        }

        Scene scene =
                new Scene(root, 400, 500);

        stage.setTitle("Carrito");

        stage.setScene(scene);

        stage.show();
    }
}
