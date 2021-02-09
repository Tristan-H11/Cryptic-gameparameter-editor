package com.github.tristan_11.gameparameteryaml.controller;

import com.github.tristan_11.gameparameteryaml.model.TreeViewHandler;
import com.github.tristan_11.gameparameteryaml.model.ValueHandler;
import com.github.tristan_11.gameparameteryaml.model.YamlHandler;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller für den MainView. Fängt alles ab, was in der GUI passiert.
 */
public class MainViewController implements Initializable {

    public static final String DATAFILE_PATH = "data.yaml";
    public static final String DESCRIPTIONFILE_PATH = "description.yaml";

    @FXML
    private JFXTreeView<?> treeView;

    @FXML
    private JFXTextField valueTextField;

    @FXML
    private JFXTextArea descriptionTextArea;

    @FXML
    private JFXTextField filterField;

    @FXML
    private Label pathTextArea;

    TreeViewHandler treeViewHandler;
    ValueHandler valueHandler;
    YamlHandler yamlDataHandler;
    YamlHandler yamlDescriptionHandler;

    /**
     * OnAction Methode, die aufgerufen wird, wenn der "Speichern"-Button gedrückt wird.
     * Nimmt sich die ValueMap, in der alle Daten zur RunTime stehen und gibt sie dem {@link YamlHandler} um
     * in die Datei zu schreiben.
     * @param event onActionEvent
     * @throws IOException
     */
    @FXML
    void saveDataToFile(ActionEvent event) throws IOException {
        this.yamlDataHandler.writeMapToYaml(this.valueHandler.getValueMap(), DATAFILE_PATH);
    }

    /**
     * Wird augerufen, wenn ein Item im TreeView geklickt wird und lädt und setzt dann die Werte aus der Map(/Datei).
     * @param event
     */
    @FXML
    void itemInTreeViewClicked(MouseEvent event) {
        valueHandler.loadValues(treeViewHandler);
        setPath();
    }

    /**
     * Setzt in der GUI den Pfad des Items in das Label.
     * Bekommt den Pfad aus dem TreeView und appended die Parents.
     */
    private void setPath() {
        StringBuilder sb = new StringBuilder();
        treeViewHandler.getPathToItem().forEach(e -> {
            sb.append("/").append(e);
        });
        pathTextArea.setText(sb.toString());
    }

    /**
     * Initialize Methode.
     * Setzt die {@link YamlHandler}, sowie andere Handler und setzt den Listener für das Filterfeld.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            yamlDataHandler = new YamlHandler(DATAFILE_PATH);
            yamlDescriptionHandler = new YamlHandler(DESCRIPTIONFILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        treeViewHandler = new TreeViewHandler(yamlDataHandler.getResultsAsMap(), this.treeView);
        treeViewHandler.setData("");
        valueHandler = new ValueHandler(yamlDataHandler, yamlDescriptionHandler, valueTextField, descriptionTextArea);
        descriptionTextArea.setDisable(true);

        pathTextArea.setText("Waiting for path...");

        filterField.textProperty().addListener((obs, oldText, newText) -> {
            treeViewHandler.setData(filterField.getText());
        });

        valueTextField.textProperty().addListener((obs, oldText, newText) -> {
            if(!oldText.isEmpty()){
                valueHandler.setNewValue(treeViewHandler.getPathToItem(), newText);
            }
        });

    }
}
