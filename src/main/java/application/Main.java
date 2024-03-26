package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 325);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Projeto de integração");
            Image icon = new Image(getClass().getResourceAsStream("/icons/icon_logo_eletra.png"));
            primaryStage.getIcons().add(icon);
            String cssPath = getClass().getResource("/view/styles.css").toExternalForm();
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
