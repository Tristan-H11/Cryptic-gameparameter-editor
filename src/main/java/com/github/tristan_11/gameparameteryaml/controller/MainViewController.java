package com.github.tristan_11.gameparameteryaml.controller;

import com.github.tristan_11.gameparameteryaml.model.TreeViewHandler;
import com.github.tristan_11.gameparameteryaml.model.ValueHandler;
import com.github.tristan_11.gameparameteryaml.model.YamlHandler;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainViewController {

    @FXML
    private JFXTreeView<?> treeView;

    @FXML
    private JFXTextField valueTextField;

    @FXML
    private JFXTextArea descriptionTextArea;

    @FXML
    private Button initialLoadButton;

    TreeViewHandler treeViewHandler;
    ValueHandler valueHandler;

    @FXML
    void initialLoad(ActionEvent event) throws IOException {
        YamlHandler yamlDataHandler = new YamlHandler("\\data.yaml");
        YamlHandler yamlDescriptionHandler = new YamlHandler("\\description.yaml");
        treeViewHandler = new TreeViewHandler(yamlDataHandler, this.treeView);
        treeViewHandler.setData();
        valueHandler = new ValueHandler(yamlDataHandler, yamlDescriptionHandler, valueTextField, descriptionTextArea);
    }

    @FXML
    void itemInTreeViewClicked(MouseEvent event) {
        valueHandler.loadValues(treeViewHandler.getPathToItem());
    }

    @FXML
    void valueChanges(ActionEvent event) {
        System.out.println("Valuefiel changed: " + valueTextField.getText());
    }

}
