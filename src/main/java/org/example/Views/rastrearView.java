package org.example.Views;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Models.JDBCUtil;
import org.example.Models.envioModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class rastrearView implements Initializable {

    @FXML
    private Label lblmensaje, lbltitulo, lblMensaje;

    @FXML
    private TextField txtRastrear;

    @FXML
    private ImageView menuView;

    @FXML
    private Label lblInicio;

    @FXML
    private TableView<envioModel> tableView;

    @FXML
    private TableColumn<envioModel, String> colNombreEnvia;
    @FXML
    private TableColumn<envioModel, String> colOtrosEnvia;
    @FXML
    private TableColumn<envioModel, String> colNombreRecibe;
    @FXML
    private TableColumn<envioModel, String> colOtrosRecibe;
    @FXML
    private TableColumn<envioModel, String> colNumeroRastreo;
    @FXML
    private TableColumn<envioModel, LocalDate> colFechaEnvio;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuView.setImage(new Image(getClass().getResource("/imagen/home.png").toExternalForm()));

        colNombreEnvia.setCellValueFactory(new PropertyValueFactory<>("nombreEnvia"));
        colOtrosEnvia.setCellValueFactory(new PropertyValueFactory<>("otrosEnvia"));
        colNombreRecibe.setCellValueFactory(new PropertyValueFactory<>("nombreRecibe"));
        colOtrosRecibe.setCellValueFactory(new PropertyValueFactory<>("otrosRecibe"));
        colNumeroRastreo.setCellValueFactory(new PropertyValueFactory<>("numeroRastreo"));
        colFechaEnvio.setCellValueFactory(new PropertyValueFactory<>("fechaEnvio"));


    }


    @FXML
    public void Rastrear() {
        String codigoIngresado = txtRastrear.getText();

        if (codigoIngresado.isEmpty()) {
            lblMensaje.setText("Ingrese un código válido");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        String sql = "SELECT " +
                "u1.nombre AS nombre_envia, " +
                "e.otros_datosEnvia, " +
                "u2.nombre AS nombre_recibe, " +
                "e.otros_datosRecibe, " +
                "e.numero_rastreo, " +
                "e.fecha_envio " +
                "FROM Envios e " +
                "JOIN Usuarios u1 ON e.envia_id = u1.id " +
                "JOIN Usuarios u2 ON e.recibe_id = u2.id " +
                "WHERE e.numero_rastreo = ?";

        ObservableList<envioModel> resultado = FXCollections.observableArrayList();

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, codigoIngresado);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                envioModel envio = new envioModel(
                        resultSet.getString("nombre_envia"),
                        resultSet.getString("nombre_recibe"),
                        null, // direccionEnvia
                        resultSet.getString("otros_datosEnvia"),
                        null, // direccionRecibe
                        resultSet.getString("otros_datosRecibe"),
                        resultSet.getString("numero_rastreo"),
                        resultSet.getDate("fecha_envio").toLocalDate()
                );
                resultado.add(envio);
                lblMensaje.setText("Resultado encontrado.");
                lblMensaje.setStyle("-fx-text-fill: green;");
            } else {
                lblMensaje.setText("No se encontró ese número de rastreo.");
                lblMensaje.setStyle("-fx-text-fill: red;");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            lblMensaje.setText("Error en la consulta.");
            lblMensaje.setStyle("-fx-text-fill: red;");
        }

        tableView.setItems(resultado);
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
