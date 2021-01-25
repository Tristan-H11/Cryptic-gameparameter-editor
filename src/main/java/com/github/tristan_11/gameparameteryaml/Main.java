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
        Scene scene = new Scene(root, 850, 470);
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("Cryptic gameparemter editor");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//ToDo: JavaDocs schreiben!!! ALS ERSTES MACHEN ^^
//ToDo: Irgendwie kenntlich machen, dass man Enter drücken muss, dass der Wert gespeichert wird. Alternaiv knopf "Wert setzen"?
//ToDo: Rückmeldung vom SafeToFile-Button oben einbauen
//ToDo: Descriptions einbauen? Vorher sortieren?
//ToDo: Allg verschönern
