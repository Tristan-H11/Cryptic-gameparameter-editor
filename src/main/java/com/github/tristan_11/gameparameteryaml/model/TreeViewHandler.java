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
        ArrayList<TreeItem> itemList = new ArrayList<>();

        resultmap.entrySet().forEach(entry -> {
            itemList.add(new TreeItem(entry.getKey()));
        });

        //Childs level 1 ins Root Item
//        itemList.forEach(item -> {
//            rootItem.getChildren().add(item);
//        });

        generateItems(resultmap, rootItem);
        //Setzen des RootItems im View
        treeView.setRoot(rootItem);

    }

    private void generateItems(Map<String, Object> map, TreeItem item){
        map.forEach((s, o) -> {
            TreeItem treeitem = new TreeItem(s);
            if (o instanceof Map) {
                this.generateItems((Map<String, Object>) o, treeitem);
            }else{
                treeitem.getChildren().add(new TreeItem<>(o.toString())); //Hier schauen, dass man nur den Namen ins Objekt haut, nicht aber die Map
                //Die Map muss rüber ins Value feld
            }
            item.getChildren().add(treeitem);

        });

    }
}
