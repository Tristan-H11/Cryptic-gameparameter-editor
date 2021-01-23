package com.github.tristan_11.gameparameteryaml.controller;

import com.github.tristan_11.gameparameteryaml.model.TreeViewHandler;
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

    @FXML
    void initialLoad(ActionEvent event) throws IOException {
        YamlHandler yamlHandler = new YamlHandler("\\data.yaml");
        TreeViewHandler treeViewHandler = new TreeViewHandler(yamlHandler, this.treeView);
        treeViewHandler.setData();
    }

    @FXML
    void itemInTreeViewClicked(MouseEvent event) {

    }

    @FXML
    void valueChanges(ActionEvent event) {
        System.out.println("Valuefiel changed: " + valueTextField.getText());
    }

}
