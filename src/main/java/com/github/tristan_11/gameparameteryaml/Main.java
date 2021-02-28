package com.github.tristan_11.gameparameteryaml;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main. Punkt.
 */
public class Main extends Application {
    public static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static InputStream getDescriptionFileStream() {
        return Main.class.getResourceAsStream("/description.yaml");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        setPrimaryStage(primaryStage);
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));


        Scene scene = new Scene(root, 900, 470);
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("YAML-Editor");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

// TODO: Nur alles leaves öffnen, falls der count <5 ist
// TODO: Translate everything into english
