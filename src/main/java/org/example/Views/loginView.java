package org.example.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.Controller.usuarioController;
import org.example.Models.GoogleUser;
import org.example.Models.usuarioModel;
import org.example.service.GoogleAuthService;

import javax.imageio.IIOException;


import java.io.IOException;

public class loginView {

    @FXML
    private TextField txtEmail, txtPassword;

    @FXML
    private Button btnCrear, btnSiguiente;

    @FXML
    private Label lblMensaje;

    @FXML
    public void initialize() {
        btnCrear.setOnAction(e -> VentanaNueva("/org/example/Views/crearCuenta.fxml"));

    }

    @FXML
    public void siguiente() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (email.isEmpty() || password.isEmpty()){
            lblMensaje.setText("Completar los campos");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        usuarioModel usuario = new usuarioController().login(email, password);

        if (usuario != null) {
            lblMensaje.setText("! Bienvenido");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/Views/menuView.fxml"));
                Parent root = fxmlLoader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Menu");
                stage.show();
                stage.setResizable(true);


                Stage stage1 = (Stage) btnSiguiente.getScene().getWindow();
                stage1.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            lblMensaje.setText("Email o contraseña invalida");
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
    }


    public void VentanaNueva(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Crear Cuenta");
            stage.show();

        }catch (IIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onGoogleLoginClick(ActionEvent event) {
        try {
            GoogleAuthService googleService = new GoogleAuthService();
            GoogleUser gUser = googleService.login();

            if (gUser == null || gUser.getEmail() == null) {
                System.out.println("No se obtuvo información del usuario Google.");
                return;
            }

            usuarioController uc = new usuarioController();
            usuarioModel usuario = uc.loginConGoogle(gUser);

            if (usuario != null) {
                System.out.println("Sesión iniciada: " + usuario.getEmail());

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/Views/menuView.fxml"));
                Parent root = fxmlLoader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Menu");
                stage.show();

                Stage loginStage = (Stage) txtEmail.getScene().getWindow();
                loginStage.close();

            } else {
                System.out.println("Error al crear/iniciar sesión con Google.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
