package org.example.Views;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.IIOException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class menuView implements Initializable {
    @FXML
    private ImageView imageView, imageEnvio, imageFondo, imageRastrear, imageInfo ;

    @FXML
    private Label lblEnvio, lblRastrear, lblInfoEnvios, lblLogout;

    private final List<Image> images = Arrays.asList(
            new Image(getClass().getResource("/imagen/imagen.jpg").toString()),
            new Image(getClass().getResource("/imagen/img2.jpg").toString()),
            new Image(getClass().getResource("/imagen/img3.jpg").toString())
    );

    private int img = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageFondo.setImage(new Image(getClass().getResource("/imagen/fondo.jpg").toString()));
        imageRastrear.setImage(new Image(getClass().getResource("/imagen/tracking_6491547.png").toString()));
        imageInfo.setImage(new Image(getClass().getResource("/imagen/info.png").toString()));

        imageView.setImage(images.get(img));
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);
        imageView.setCache(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            transicion();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Platform.runLater(() -> {
            Scene scene = imageView.getScene();
            imageView.fitWidthProperty().bind(scene.widthProperty());
            imageView.fitHeightProperty().bind(scene.heightProperty());

            imageFondo.fitWidthProperty().bind(scene.widthProperty());
            imageFondo.fitHeightProperty().unbind(); // Importante si ya estaba ligado
            imageFondo.setFitHeight(0); // Altura fija de 200 px
            imageFondo.layoutYProperty().bind(
                    scene.heightProperty().subtract(imageEnvio.fitHeightProperty()).subtract(10)
            );


            // Centrar horizontalmente
            imageEnvio.layoutXProperty().bind(
                    scene.widthProperty().subtract(imageEnvio.fitWidthProperty()).divide(1.52)
            );

            // Posicionar arriba del borde inferior
            imageEnvio.layoutYProperty().bind(
                    scene.heightProperty().subtract(imageEnvio.fitHeightProperty()).subtract(100)
            );


            // Centrar horizontalmente
            lblEnvio.layoutXProperty().bind(
                    scene.widthProperty().subtract(lblEnvio.widthProperty()).divide(1.5)
            );

            // Posicionar arriba del borde inferior
            lblEnvio.layoutYProperty().bind(
                    scene.heightProperty().subtract(lblEnvio.heightProperty()).subtract(50)
            );


            // Centrar horizontalmente
            imageRastrear.layoutXProperty().bind(
                    scene.widthProperty().subtract(imageEnvio.fitWidthProperty()).divide(3.55)
            );

            // Posicionar arriba del borde inferior
            imageRastrear.layoutYProperty().bind(
                    scene.heightProperty().subtract(imageEnvio.fitHeightProperty()).subtract(100)
            );

            // Centrar horizontalmente
            lblRastrear.layoutXProperty().bind(
                    scene.widthProperty().subtract(lblEnvio.widthProperty()).divide(3.5)
            );

            // Posicionar arriba del borde inferior
            lblRastrear.layoutYProperty().bind(
                    scene.heightProperty().subtract(lblEnvio.heightProperty()).subtract(50)
            );

            // Centrar horizontalmente
            imageInfo.layoutXProperty().bind(
                    scene.widthProperty().subtract(imageEnvio.fitWidthProperty()).divide(2.06)
            );

            // Posicionar arriba del borde inferior
            imageInfo.layoutYProperty().bind(
                    scene.heightProperty().subtract(imageEnvio.fitHeightProperty()).subtract(100)
            );

            // Centrar horizontalmente
            lblInfoEnvios.layoutXProperty().bind(
                    scene.widthProperty().subtract(lblEnvio.widthProperty()).divide(2)
            );

            // Posicionar arriba del borde inferior
            lblInfoEnvios.layoutYProperty().bind(
                    scene.heightProperty().subtract(lblEnvio.heightProperty()).subtract(50)
            );

            lblLogout.layoutXProperty().bind(
                    scene.widthProperty().subtract(lblLogout.widthProperty()).subtract(10)
            );
            lblLogout.setLayoutY(20);
        });
    }

    public void transicion() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), imageView);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        fadeTransition.setOnFinished(e -> {
            img = (img + 1) % images.size();
            imageView.setImage(images.get(img));
            imageView.setImage(images.get(img));
            imageView.setPreserveRatio(false);
            imageView.setSmooth(true);
            imageView.setCache(false);


            FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(500), imageView);
            fadeTransition1.setFromValue(0.0);
            fadeTransition1.setToValue(1.0);
            fadeTransition1.play();
        });
        fadeTransition.play();

    }

    public void envio(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/Views/envioView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Realizar Envio");
            stage.show();

            Stage stage1 = (Stage) imageView.getScene().getWindow();
            stage1.close();
        }catch (IIOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void infoEnvio(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/Views/infoEnviosView.fxml"));
            Parent escena = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(escena));
            stage.setTitle("Informacion Envios");
            stage.show();

            Stage stage1 = (Stage) imageInfo.getScene().getWindow();
            stage1.close();
        }catch (IIOException e) {
            e.printStackTrace();
        }catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    public void Rastrear(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/Views/rastrearView.fxml"));
            Parent escena = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(escena));
            stage.setTitle("Rastrear");
            stage.show();

            Stage stage1 = (Stage) imageInfo.getScene().getWindow();
            stage1.close();
        }catch (IIOException e) {
            e.printStackTrace();
        }catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    @FXML
    private void volver(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/Views/loginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Iniciar Sesion");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
