package com.github.tristan_11.gameparameteryaml.controller;

import com.github.tristan_11.gameparameteryaml.Main;
import com.github.tristan_11.gameparameteryaml.model.TreeViewHandler;
import com.github.tristan_11.gameparameteryaml.model.ValueHandler;
import com.github.tristan_11.gameparameteryaml.model.YamlHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller für den MainView. Fängt alles ab, was in der GUI passiert.
 */
public class MainViewController implements Initializable {

    public static String datafilePath = null;
    public static final String DESCRIPTIONFILE_PATH = "description.yaml";

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Label savedToFileLabel;

    @FXML
    private Label leaveCountLabel;

    @FXML
    private TextField valueTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField filterField;

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
     */
    @FXML
    void saveDataToFile() {
        Platform.runLater(
                () -> {
                    try {
                        this.yamlDataHandler.writeMapToYaml(this.valueHandler.getValueMap(), datafilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    savedToFileLabel.setText("Saved!");
                });

        Timeline fiveSecondTimer = new Timeline(
                new KeyFrame(Duration.seconds(5),
                        event -> savedToFileLabel.setText("Please save!")));
        fiveSecondTimer.setCycleCount(1);
        fiveSecondTimer.play();
    }

    /**
     * Wird aufgerufen, wenn ein Item im TreeView geklickt wird und lädt und setzt dann die Werte aus der Map(/Datei).
     */
    @FXML
    void itemInTreeViewClicked() {
        valueHandler.loadValues(treeViewHandler);
        setPath();
    }

    /**
     * Setzt in der GUI den Pfad des Items in das Label.
     * Bekommt den Pfad aus dem TreeView und appended die Parents.
     */
    private void setPath() {
        StringBuilder sb = new StringBuilder();
        treeViewHandler.getPathToItem().forEach(e -> sb.append("/").append(e));
        pathTextArea.setText(sb.toString());
    }

    /**
     * Initialize Methode.
     * Setzt die {@link YamlHandler}, sowie andere Handler und setzt den Listener für das Filterfeld.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        setGUIComponents();
        createListeners();

    }

    @FXML
    private void loadFileButtonAction(){
        openFileChooser();
        valueTextField.setDisable(false);
        filterField.setDisable(false);
        treeView.setDisable(false);
        loadFiles();
        initializeTreeView();
        valueHandler = new ValueHandler(yamlDataHandler, yamlDescriptionHandler, valueTextField, descriptionTextArea);

    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        datafilePath = fileChooser.showOpenDialog(Main.getPrimaryStage()).getAbsolutePath();
    }

    private void initializeTreeView() {
        treeViewHandler = new TreeViewHandler(yamlDataHandler.getResultsAsMap(), this.treeView);
        treeViewHandler.setData("");
        leaveCountLabel.setText("Leaves: " + treeViewHandler.getLeaveCount());
        pathTextArea.setText("Waiting for path...");
    }

    private void createListeners() {
        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null && newValue != oldValue){
                valueTextField.setEditable(newValue.getChildren().isEmpty());
            }
        });

        filterField.textProperty().addListener((obs, oldText, newText) -> {
            treeViewHandler.setExpandEverything(!oldText.isEmpty() && !newText.isEmpty());
            treeViewHandler.setData(filterField.getText());
        });

        // Listener for changes in value-textfield
        valueTextField.textProperty().addListener((obs, oldText, newText) -> {
            if(treeView.getSelectionModel().getSelectedItem() != null) {
                if (!(treeView.getSelectionModel().getSelectedItem().getChildren() instanceof Map)) {
                    if (!oldText.isEmpty()) {
                        valueHandler.setNewValue(treeViewHandler.getPathToItem(), newText);
                    }
                }
            }
        });
    }

    private void setGUIComponents() {
        descriptionTextArea.setDisable(false);
        descriptionTextArea.setWrapText(true);

        if(datafilePath == null){
            valueTextField.setDisable(true);
            filterField.setDisable(true);
            treeView.setDisable(true);
        }
    }

    private void loadFiles() {
        try {
            yamlDataHandler = new YamlHandler(datafilePath);
            yamlDescriptionHandler = new YamlHandler(DESCRIPTIONFILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
