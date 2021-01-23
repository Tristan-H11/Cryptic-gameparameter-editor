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
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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



    TreeViewHandler treeViewHandler;
    ValueHandler valueHandler;
    YamlHandler yamlDataHandler;
    YamlHandler yamlDescriptionHandler;

    @FXML
    void saveDataToFile(ActionEvent event) throws IOException {
        this.yamlDataHandler.writeMapToYaml(this.valueHandler.getValueMap(), DATAFILE_PATH);
    }

    @FXML
    void initialLoad(ActionEvent event) {

    }

    @FXML
    void itemInTreeViewClicked(MouseEvent event) {
        valueHandler.loadValues(treeViewHandler);
    }

    @FXML
    void valueChanges(ActionEvent event) throws IOException {
        valueHandler.setNewValue(treeViewHandler.getPathToItem(), valueTextField.getText());
        System.out.println(treeViewHandler.getPathToItem() + " changed: New Value is " + valueTextField.getText());
    }

    public  JFXTextField getFilterField() {
        return filterField;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            yamlDataHandler = new YamlHandler(DATAFILE_PATH);
            yamlDescriptionHandler = new YamlHandler(DESCRIPTIONFILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        treeViewHandler = new TreeViewHandler(yamlDataHandler, this.treeView, this.filterField);
        treeViewHandler.setData("");
        valueHandler = new ValueHandler(yamlDataHandler, yamlDescriptionHandler, valueTextField, descriptionTextArea);
        descriptionTextArea.setDisable(true);

        filterField.textProperty().addListener((obs, oldText, newText) -> {
            // do what you need with newText here, e.g.
            System.out.println("Filter hat gewechselt: " + filterField.getText());
            treeViewHandler.setData(filterField.getText());
        });

    }
}
