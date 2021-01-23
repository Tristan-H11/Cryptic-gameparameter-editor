package com.github.tristan_11.gameparameteryaml.model;

import com.jfoenix.controls.JFXTreeView;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.Map;

public class TreeViewHandler {
    YamlHandler yamlHandler;
    JFXTreeView treeView;

    public TreeViewHandler(YamlHandler yamlHandler, JFXTreeView<?> treeView) {
        this.yamlHandler = yamlHandler;
        this.treeView = treeView;
    }


    public void setData() {
        // Create the Root TreeItem
        TreeItem rootItem = new TreeItem("Kategorien");
        Map<String, Object> resultmap = yamlHandler.getResultsAsMap();

        generateItems(resultmap, rootItem);
        //Setzen des RootItems im View
        treeView.setRoot(rootItem);

    }

    /**
     * Bekommt die map aus der yaml übergeben und baut rekusriv bis auf variablen level das TreeItem runter.
     * @param map
     * @param item
     */
    private void generateItems(Map<String, Object> map, TreeItem item){
        map.forEach((s, o) -> {
            TreeItem treeitem = new TreeItem(s);
            if (o instanceof Map) {
                this.generateItems((Map<String, Object>) o, treeitem);
            }
            item.getChildren().add(treeitem);

        });

    }
}
