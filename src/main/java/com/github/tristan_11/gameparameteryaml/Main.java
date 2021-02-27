package com.github.tristan_11.gameparameteryaml;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main. Punkt.
 */
public class Main extends Application {
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.primaryStage = primaryStage;
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Scene scene = new Scene(root, 900, 470);
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("YAML-Editor");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

// TODO: Translate everything into english
