package org.example.Views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.PathElement;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.Controller.envioController;
import org.example.Controller.usuarioController;
import org.example.Models.envioModel;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class envioView implements Initializable {

    @FXML
    private ImageView menuView;

    @FXML
    private Label  lblMensaje,lblInicio, lblInfo, lblMensaje2;

    @FXML
    private TextField txtNombreEnvia, txtTelefonoEnvia, txtDireccionEnvia, txtOtrosEnvia, txtEmailEnvia, txtNombreRecibe, txtTelefonoRecibe, txtDireccionRecibe, txtOtrosRecibe, txtEmailRecibe;

    @FXML
    private AnchorPane AnchoPaneContenedor, AnchoPaneContenedor2;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuView.setImage(new Image(getClass().getResource("/imagen/home.png").toExternalForm()));

        int desplazamientoX = 100;  // positivo: derecha, negativo: izquierda
        int desplazamientoY = -50; // positivo: abajo, negativo: arriba

        int forms2X = 600;
        int forms2Y = -50;

        Platform.runLater(() -> {
            Scene scene = menuView.getScene();

            menuView.layoutXProperty().bind(
                    scene.widthProperty().subtract(menuView.fitWidthProperty()).subtract(20)
            );

            menuView.setLayoutY(20);

            lblInicio.layoutXProperty().bind(
                    scene.widthProperty().subtract(lblInicio.widthProperty()).subtract(49)
            );
            lblInicio.setLayoutY(20);

            //izquierda
            lblInfo.setLayoutX(300);
            //abajo
            lblInfo.setLayoutY(60);


                // Primer form
                AnchoPaneContenedor.layoutXProperty().bind(
                        scene.widthProperty()
                                .subtract(AnchoPaneContenedor.widthProperty())
                                .divide(2)
                                .add(desplazamientoX)
                );
                AnchoPaneContenedor.layoutYProperty().bind(
                        scene.heightProperty()
                                .subtract(AnchoPaneContenedor.heightProperty())
                                .divide(2)
                                .add(desplazamientoY)
                );
                AnchoPaneContenedor.prefWidthProperty().bind(scene.widthProperty().multiply(0.7));
                AnchoPaneContenedor.prefHeightProperty().bind(scene.heightProperty().multiply(0.6));

                // Segundo form
                AnchoPaneContenedor2.layoutXProperty().bind(
                        scene.widthProperty()
                                .subtract(AnchoPaneContenedor2.widthProperty())
                                .divide(2)
                                .add(forms2X)
                );
                AnchoPaneContenedor2.layoutYProperty().bind(
                        scene.heightProperty()
                                .subtract(AnchoPaneContenedor2.heightProperty())
                                .divide(2)
                                .add(forms2Y)
                );
                AnchoPaneContenedor2.prefWidthProperty().bind(scene.widthProperty().multiply(0.7));
                AnchoPaneContenedor2.prefHeightProperty().bind(scene.heightProperty().multiply(0.6));
        });
    }

    @FXML
    private void siguiente() {
        String nombreEnvia = txtNombreEnvia.getText();
        String telefonoEnvia = txtTelefonoEnvia.getText();
        String direccionEnvia = txtDireccionEnvia.getText();
        String otrosEnvia = txtOtrosEnvia.getText();
        String emailEnvia = txtEmailEnvia.getText();


        //Recibe
        String nombreRecibe = txtNombreRecibe.getText();
        String telefonoRecibe = txtTelefonoRecibe.getText();
        String direccionRecibe = txtDireccionRecibe.getText();
        String otrosRecibe = txtOtrosRecibe.getText();
        String emailRecibe = txtEmailRecibe.getText();

        if (nombreEnvia.isEmpty() || telefonoEnvia.isEmpty() || direccionEnvia.isEmpty() || emailEnvia.isEmpty() || otrosEnvia.isEmpty() || nombreRecibe.isEmpty() || telefonoRecibe.isEmpty() || direccionRecibe.isEmpty() || emailRecibe.isEmpty() || otrosRecibe.isEmpty()) {
            lblMensaje.setText("Completar todos los campos");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        usuarioController usuarioCtrl = new usuarioController();
        int envia_id = usuarioCtrl.buscaroCrearCliente(nombreEnvia, "N/A", emailEnvia, telefonoEnvia, "N/A");
        int recibe_id = usuarioCtrl.buscaroCrearCliente(nombreRecibe, "N/A", emailRecibe, telefonoRecibe, "N/A");



        envioModel envio = new envioModel();
        envio.setDireccionEnvia(direccionEnvia);
        envio.setOtrosEnvia(otrosEnvia);
        envio.setDireccionRecibe(direccionRecibe);
        envio.setOtrosRecibe(otrosRecibe);

        String numeroRastreo = "ENV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        envio.setNumeroRastreo(numeroRastreo);

        if (envia_id == -1 || recibe_id == -1){
            lblMensaje.setText("Error al guardar usuario");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }


        envioController envioCtrl = new envioController();
        envioCtrl.crearEnvio(envia_id, recibe_id, envio);

        txtNombreEnvia.clear();
        txtTelefonoEnvia.clear();
        txtDireccionEnvia.clear();
        txtOtrosEnvia.clear();
        txtEmailEnvia.clear();

        txtNombreRecibe.clear();
        txtTelefonoRecibe.clear();
        txtDireccionRecibe.clear();
        txtOtrosRecibe.clear();
        txtEmailRecibe.clear();

        lblMensaje.setText("Envío registrado correctamente");
        lblMensaje2.setText("numero de rastreo " +numeroRastreo);
        lblMensaje.setStyle("-fx-text-fill: green;");
    }

    @FXML
    private void volver(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/Views/menuView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
