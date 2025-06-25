package org.example.Views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.Controller.usuarioController;
import org.example.Models.JDBCUtil;
import org.example.Models.usuarioModel;


import javafx.scene.control.TextField;

import javax.imageio.IIOException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class crearCuentaView {

    @FXML
    private TextField txtNombre, txtApellido, txtPassword, txtEmail, txtTelefono;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnVolver;

    public void crear() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || password.isEmpty() || email.isEmpty() || telefono.isEmpty()){
            lblMensaje.setText("Completar todos los campos");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            usuarioModel usuario = new usuarioModel(
                    nombre,
                    apellido,
                    password,
                    email,
                    telefono
            );
            usuarioController usuarioController = new usuarioController();
            usuarioController.crear(usuario);

            Stage stage = (Stage) lblMensaje.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void volver() {
        btnVolver.setOnAction(e -> {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.close();
        });
    }
}
