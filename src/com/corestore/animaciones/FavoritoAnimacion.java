package com.corestore.animaciones;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class FavoritoAnimacion {

    // ================= ANIMACION FAVORITO =================

    public static void animarFavorito(Button boton) {

        ScaleTransition escala =
                new ScaleTransition(Duration.millis(200), boton);

        escala.setFromX(1);

        escala.setFromY(1);

        escala.setToX(1.2);

        escala.setToY(1.2);

        escala.setCycleCount(2);

        escala.setAutoReverse(true);

        escala.play();
    }
}