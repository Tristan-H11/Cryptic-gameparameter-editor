package com.github.tristan_11.gameparameteryaml;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main. Punkt.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Scene scene = new Scene(root, 950, 470);
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("Cryptic gameparemter editor");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// ToDo: Config für die Config. Auf Typen prüfen. Descriptionleave in eine Map ändern
// ToDo: checkInput Function vorm SaveToMap mit Meldung an den Nutzer
// ToDo: Tests?
