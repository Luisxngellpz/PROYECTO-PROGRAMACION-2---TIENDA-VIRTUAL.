/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.corestore;
import java.io.*;
import javafx.scene.control.Alert;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;



/**
 *
 * @author User
 */

public class Main extends Application {

    @Override
    public void start(Stage stage) {

     
        // CONTENEDOR PRINCIPAL
    Label tituloGeneral = new Label("🏬CoreStore");

       tituloGeneral.setStyle(
       "-fx-font-size: 32px;" +
       "-fx-font-weight: bold;" +
       "-fx-text-fill: black;"
);

       HBox topBar = new HBox(tituloGeneral);

       topBar.setAlignment(Pos.CENTER);

       topBar.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(topBar);

        root.setStyle("-fx-background-color: #f5f5f5;");
        

       
        // CONTENIDO CENTRAL
        
        // =========================
        // PANEL IZQUIERDO
        // =========================

        VBox leftPanel = new VBox(15);

        leftPanel.setPadding(new Insets(30));
        leftPanel.setPrefWidth(420);

        leftPanel.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 10;
                """);

        // TITULO

        Label title = new Label("Bienvenido a CoreStore");

        title.setFont(new Font(28));

        // SUBTITULO

        Label subtitle = new Label(
         "Accede a tu cuenta para continuar con tus compras"
                
        );
                        

        // EMAIL

        Label emailLabel = new Label("Correo electrónico");

        TextField emailField = new TextField();

        emailField.setPromptText("usuario@ejemplo.com");
        emailField.setPrefHeight(40);

        // PASSWORD

        Label passLabel = new Label("Contraseña");

        PasswordField passwordField = new PasswordField();
        

        passwordField.setPromptText("Min. 8 caracteres");
        passwordField.setPrefHeight(40);

        // LINK

        Hyperlink forgotLink = new Hyperlink("¿Olvidaste tu contraseña?");
         forgotLink.setStyle("-fx-text-fill: black;");
         forgotLink.setOnAction(e -> {
Label subtituloCorreo =
        new Label(
        "Ingresa el correo de tu cuenta"
);

subtituloCorreo.setStyle(
        "-fx-font-size: 14px;" +
        "-fx-text-fill: black;"
);
HBox subtituloBox =
        new HBox(subtituloCorreo);

subtituloBox.setAlignment(
        Pos.CENTER_LEFT
);
    TextField correoRecuperar =
            new TextField();

    correoRecuperar.setPromptText(
            "Ingresa tu correo"
    );

    PasswordField nuevaPassword =
            new PasswordField();

    nuevaPassword.setPromptText(
            "Nueva contraseña"
    );

    Button actualizar =
            new Button("Actualizar");

    VBox layout = new VBox(15);

    layout.setAlignment(Pos.CENTER);
    Label subtituloPassword =
        new Label(
        "Ingresa tu nueva contraseña"
);

subtituloPassword.setStyle(
        "-fx-font-size: 14px;" +
        "-fx-text-fill: black;"
);

HBox passwordBox =
        new HBox(subtituloPassword);

passwordBox.setAlignment(
        Pos.CENTER_LEFT
);

    layout.getChildren().addAll(

        subtituloBox,
        correoRecuperar,

        passwordBox,
        nuevaPassword,

        actualizar
);

    Scene scene = new Scene(layout, 350, 250);

    Stage ventana = new Stage();

    ventana.setTitle(
            "Cambiar contraseña"
    );

    ventana.setScene(scene);

    ventana.show();

    actualizar.setOnAction(ev -> {

        String correoBuscar =
                correoRecuperar.getText();

        String nuevaContra =
                nuevaPassword.getText();

        if(nuevaContra.length() < 8){

            Alert alerta = new Alert(
                    Alert.AlertType.ERROR
            );

            alerta.setTitle("Error");

            alerta.setHeaderText(null);

            alerta.setContentText(
                    "La contraseña debe tener mínimo 8 caracteres"
            );

            alerta.showAndWait();

            return;
        }

        try {

            BufferedReader br =
                    new BufferedReader(
                    new FileReader("usuarios.txt")
            );

            ArrayList<String> lineas =
                    new ArrayList<>();

            String linea;

            boolean encontrado = false;

            while((linea = br.readLine()) != null){

    String[] datos =
            linea.split(",");

    if(datos.length < 2){

        lineas.add(linea);

        continue;
    }

    String correo =
            datos[0].trim();

    if(correo.equalsIgnoreCase(
            correoBuscar.trim()
    )){

        lineas.add(
                correo + "," +
                nuevaContra
        );

        encontrado = true;

    } else {

        lineas.add(linea);

    }
}

            br.close();

            FileWriter fw =
                    new FileWriter(
                    "usuarios.txt",
                    false
            );

            for(String l : lineas){

                fw.write(l + "\n");

            }

            fw.close();

            if(encontrado){

                Alert alerta =
                        new Alert(
                        Alert.AlertType.INFORMATION
                );

                alerta.setTitle("Correcto");

                alerta.setHeaderText(null);

                alerta.setContentText(
                        "Contraseña actualizada"
                );

                alerta.showAndWait();

            } else {

                Alert alerta =
                        new Alert(
                        Alert.AlertType.ERROR
                );

                alerta.setTitle("Error");

                alerta.setHeaderText(null);

                alerta.setContentText(
                        "Correo no encontrado"
                );

                alerta.showAndWait();

            }

        } catch(Exception ex){

            ex.printStackTrace();

        }

    });

});
         HBox forgotBox = new HBox(forgotLink);
         forgotBox.setAlignment(Pos.CENTER_RIGHT);
         forgotBox.setMaxWidth(Double.MAX_VALUE);
        // BOTON LOGIN

        Button loginButton = new Button("Iniciar sesión");

        loginButton.setPrefWidth(350);
        loginButton.setPrefHeight(45);

        loginButton.setStyle("""
                             
                -fx-background-color: black;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-background-radius: 8;
                """);
        
// BOTON LOGIN

loginButton.setOnAction(e -> {

    String correo = emailField.getText();
    String contraseña = passwordField.getText();

    boolean encontrado = false;

    try {

        BufferedReader br =
                new BufferedReader(
                        new FileReader("usuarios.txt")
                );

        String linea;

        while((linea = br.readLine()) != null) {

            String[] datos = linea.split(",");

            String emailGuardado = datos[0].trim();
            String passGuardada = datos[1].trim();

            if(correo.equals(emailGuardado)
                    && contraseña.equals(passGuardada)) {

                encontrado = true;

                break;
            }
        }

        br.close();

        if(encontrado) {

            Alert alerta =
                    new Alert(Alert.AlertType.INFORMATION);

            alerta.setTitle("Bienvenido");
            alerta.setHeaderText(null);
            alerta.setContentText("Login correcto");

            alerta.showAndWait();

        } else {

            Alert alerta =
                    new Alert(Alert.AlertType.ERROR);

            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Datos incorrectos");

            alerta.showAndWait();
        }

    } catch(Exception ex) {

        ex.printStackTrace();

    }

});

        // TEXTO DIVISOR

        Label divider = new Label("o iniciar sesión con");
        divider.setMaxWidth(Double.MAX_VALUE);
        divider.setAlignment(Pos.CENTER);
        // BOTON APPLE

        Button appleButton = new Button("🍎Continuar con Apple");
        appleButton.setOnAction(e -> {

    ComboBox<String> cuentas = new ComboBox<>();
        
    PasswordField applePassword = new PasswordField();

    applePassword.setPromptText("Contraseña");

    cuentas.getItems().addAll(
            "angela@icloud.com",
            "luisLopez@icloud.com",
            "juanLugo@gmail.com",
            "EverF@gmail.com"
    );

    cuentas.setPromptText("Selecciona una cuenta");

    Button continuar = new Button("Continuar");

    VBox layout = new VBox(20);

    layout.setAlignment(Pos.CENTER);

   layout.getChildren().addAll(
        cuentas,
        applePassword,
        continuar
);

    Scene sceneApple = new Scene(layout, 350, 200);

    Stage ventanaApple = new Stage();

    ventanaApple.setTitle("Iniciar con Apple");

    ventanaApple.setScene(sceneApple);

    ventanaApple.show();

    continuar.setOnAction(ev -> {

        String cuentaSeleccionada = cuentas.getValue();
        String contraseñaApple = applePassword.getText();

        if(cuentaSeleccionada != null){
            if(contraseñaApple.length() < 8){

    Alert alerta = new Alert(Alert.AlertType.ERROR);

    alerta.setTitle("Error");

    alerta.setHeaderText(null);

    alerta.setContentText(
        "La contraseña debe tener mínimo 8 caracteres"
    );

    alerta.showAndWait();

    return;
}
try {

    FileWriter fw = new FileWriter(
        "usuarios.txt",
        true
    );

    fw.write(
    cuentaSeleccionada + "," +
    contraseñaApple + "\n"
);

    fw.close();

} catch(Exception ex){

    ex.printStackTrace();

}
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);

            alerta.setTitle("Bienvenido");

            alerta.setHeaderText(null);

            alerta.setContentText(
                "Bienvenido " + cuentaSeleccionada
            );

            alerta.showAndWait();

        }

    });

});
        appleButton.setPrefWidth(350);
        appleButton.setPrefHeight(45);

        appleButton.setStyle("""
                -fx-background-color: #111111;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-background-radius: 8;
                """);

        // CREAR CUENTA

        Button createButton = new Button("Crear cuenta");

        createButton.setPrefWidth(350);
        createButton.setPrefHeight(45);

        createButton.setStyle("""
                -fx-background-color: #e5e5e5;
                -fx-font-size: 14px;
                -fx-background-radius: 8;
                """);
      
// BOTON CREAR CUENTA


createButton.setOnAction(e -> {

    String correo = emailField.getText();
    String contraseña = passwordField.getText();

    if(correo.isEmpty() || contraseña.isEmpty()) {

        Alert alerta = new Alert(Alert.AlertType.ERROR);

        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText("Complete todos los campos");

        alerta.showAndWait();

        return;
    }
    if(contraseña.length() < 8) {

    Alert alerta = new Alert(Alert.AlertType.ERROR);

    alerta.setTitle("Error");
    alerta.setHeaderText(null);
    alerta.setContentText("La contraseña debe tener mínimo 8 caracteres");

    alerta.showAndWait();

    return;
}

    try {

        FileWriter fw = new FileWriter("usuarios.txt", true);

        fw.write(correo + "," + contraseña + "\n");

        fw.close();

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Correcto");
        alerta.setHeaderText(null);
        alerta.setContentText("Usuario registrado");

        alerta.showAndWait();

        emailField.clear();
        passwordField.clear();

    } catch(Exception ex) {

        ex.printStackTrace();

    }

});
        // LINKS ABAJO

        HBox linksBox = new HBox(40);

        Hyperlink helpLink = new Hyperlink("Ayuda");
        Hyperlink contactLink = new Hyperlink("Contacto");
        helpLink.setStyle("-fx-text-fill: black;");
        contactLink.setStyle("-fx-text-fill: black;");

        linksBox.getChildren().addAll(helpLink, contactLink);
        
       

        // AGREGAR COMPONENTES

        leftPanel.getChildren().addAll(
                title,
                subtitle,
                emailLabel,
                emailField,
                passLabel,
                passwordField,
                forgotBox,
                loginButton,
                divider,
                appleButton,
                createButton,
                linksBox
        );

        // PANEL DERECHO

        StackPane rightPanel = new StackPane();

        rightPanel.setPrefWidth(500);

        rightPanel.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 10;
                """);

        Label logo = new Label("🏬CoreStore");

        logo.setFont(new Font(50));

        rightPanel.getChildren().add(logo);
       

        
// AGREGAR PANELES
       
        HBox container = new HBox();
        container.setSpacing(60);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(leftPanel, rightPanel);

        root.setCenter(container);

        // =========================
        // FOOTER
        // =========================

        HBox footer = new HBox(20);

        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));

        Label footerText = new Label(
        "© 2026 CoreStore. Todos los derechos reservados."
        );

        Hyperlink terms = new Hyperlink("Términos");
        Hyperlink privacy = new Hyperlink("Políticas de privacidad");
        terms.setStyle("-fx-text-fill: black;");
        privacy.setStyle("-fx-text-fill: black;");

        footer.getChildren().addAll(
                footerText,
                terms,
                privacy
        );

        root.setBottom(footer);

        // =========================
        // ESCENA
        // =========================

        Scene scene = new Scene(root, 1200, 700);

        stage.setTitle("CoreStore");

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}