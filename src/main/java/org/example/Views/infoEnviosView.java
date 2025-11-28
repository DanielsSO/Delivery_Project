package org.example.Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.Controller.JDBCUtil;
import org.example.Models.envioModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class infoEnviosView {

    @FXML
    private TableView<envioModel> tableView;

    @FXML
    private TableColumn<envioModel, String> colNombreEnvia;

    @FXML
    private TableColumn<envioModel, String > colDireccionEnvia;

    @FXML
    private TableColumn<envioModel, String> colOtrosEnvia;

    @FXML
    private TableColumn<envioModel, String> colNombreRecibe;

    @FXML
    private TableColumn<envioModel, String > colDireccionRecibe;

    @FXML
    private TableColumn<envioModel, String> colOtrosRecibe;

    @FXML
    private TableColumn<envioModel, String> colNumeroRastreo;

    @FXML
    private TableColumn<envioModel, LocalDate> colFechaEnvio;

    @FXML
    private ImageView menuView;

    @FXML
    private Label lblInicio;

    public void initialize() {
        menuView.setImage(new Image(getClass().getResource("/imagen/home.png").toExternalForm()));

        colNombreEnvia.setCellValueFactory(new PropertyValueFactory<>("nombreEnvia"));
        colDireccionEnvia.setCellValueFactory(new PropertyValueFactory<>("direccionEnvia"));
        colOtrosEnvia.setCellValueFactory(new PropertyValueFactory<>("otrosEnvia"));
        colNombreRecibe.setCellValueFactory(new PropertyValueFactory<>("nombreRecibe"));
        colDireccionRecibe.setCellValueFactory(new PropertyValueFactory<>("direccionRecibe"));
        colOtrosRecibe.setCellValueFactory(new PropertyValueFactory<>("otrosRecibe"));
        colNumeroRastreo.setCellValueFactory(new PropertyValueFactory<>("numeroRastreo"));
        colFechaEnvio.setCellValueFactory(new PropertyValueFactory<>("fechaEnvio"));

        cargarEnvios();


    }



    public void cargarEnvios() {
        ObservableList<envioModel> listaEnvios = FXCollections.observableArrayList();

        String cargar = "SELECT " +
                        "e.id, " +
                        "u1.nombre AS nombre_envia, " +
                        "e.direccionenvia, " +
                        "e.otros_datosEnvia, " +
                        "u2.nombre AS nombre_recibe, " +
                        "e.direccionRecibe, " +
                        "e.otros_datosRecibe, " +
                        "e.numero_rastreo, " +
                        "e.fecha_envio " +
                        "FROM Envios e " +
                        "JOIN Usuarios u1 ON e.envia_id = u1.id " +
                        "JOIN Usuarios u2 ON e.recibe_id = u2.id";

        try (Connection connection = JDBCUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(cargar)) {

            while (resultSet.next()) {
                envioModel infoEnvios = new envioModel(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre_envia"),
                        resultSet.getString("nombre_recibe"),
                        resultSet.getString("direccionEnvia"),
                        resultSet.getString("otros_datosEnvia"),
                        resultSet.getString("direccionRecibe"),
                        resultSet.getString("otros_datosRecibe"),
                        resultSet.getString("numero_rastreo"),
                        resultSet.getDate("fecha_envio").toLocalDate()
                );
                listaEnvios.add(infoEnvios);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(listaEnvios);
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
