package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import model.Models;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Models modelsInitializer = new Models();
            modelsInitializer.initializeModels();

            Parent root = FXMLLoader.load(getClass().getResource("/mainView.fxml"));

            Scene scene = new Scene(root, 600, 325);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Projeto de integração");

            String iconPath = "file:src/main/resources/icon_logo_eletra.png";
            Image icon = new Image(iconPath);
            primaryStage.getIcons().add(icon);

            String cssPath = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
            scene.getStylesheets().add(cssPath);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
