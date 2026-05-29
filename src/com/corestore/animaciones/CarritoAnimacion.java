package com.corestore.animaciones;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class CarritoAnimacion {

    // ===== ANIMACION BOTON =====
    public static void animarBoton(Button boton) {

        ScaleTransition escala =
                new ScaleTransition(
                        Duration.millis(180),
                        boton
                );

        escala.setFromX(1);

        escala.setFromY(1);

        escala.setToX(1.15);

        escala.setToY(1.15);

        escala.setCycleCount(2);

        escala.setAutoReverse(true);

        escala.play();
    }

    // ===== ANIMACION CARRITO =====
    public static void animarCarrito(Button carritoBtn) {

        ScaleTransition escala =
                new ScaleTransition(
                        Duration.millis(200),
                        carritoBtn
                );

        escala.setFromX(1);

        escala.setFromY(1);

        escala.setToX(1.2);

        escala.setToY(1.2);

        escala.setCycleCount(2);

        escala.setAutoReverse(true);

        escala.play();
    }
}
