package com.github.tristan_11.gameparameteryaml.model;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TreeViewHandler {
    YamlHandler yamlHandler;
    JFXTreeView treeView;
    JFXTextField filterField;

    public TreeViewHandler(YamlHandler yamlHandler, JFXTreeView<?> treeView, JFXTextField filterField) {
        this.yamlHandler = yamlHandler;
        this.treeView = treeView;
        this.filterField = filterField;
    }


    public void setData(String filter) {
        // Create the Root TreeItem
        FilterableTreeItem rootItem = new FilterableTreeItem("Categories");
        Map<String, Object> resultmap = yamlHandler.getResultsAsMap();


        generateItems(resultmap, rootItem, filter);
        //Setzen des RootItems im View
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);
    }

    /**
     * Bekommt die map aus der yaml übergeben und baut rekusriv bis auf variablen level das TreeItem runter.
     *
     * @param map
     * @param item
     */
    private void generateItems(Map<String, Object> map, FilterableTreeItem item, String filter) {
        if (filter.isEmpty()) {
            map.forEach((s, o) -> {
                FilterableTreeItem treeitem = new FilterableTreeItem(s);
                if (o instanceof Map) {
                    this.generateItems((Map<String, Object>) o, treeitem, filter);
                }
                item.getChildren().add(treeitem);
            });
        } else { //ToDo: Parents merken / Pfad speichern / iwie sowas
            List<String> searchList = new LinkedList<>();
            search(map, filter, searchList);
            searchList.forEach(searchEntry -> {
                item.getChildren().add(new FilterableTreeItem<>(searchEntry));
            });
        }
    }

    private void search(Map<String, Object> map, String filter, List<String> searchList) {

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                System.out.println("Aktueller Entry: " + entry.getKey());
                search((Map<String, Object>) entry.getValue(), filter, searchList);
            } else {
                if (entry.getKey().contains(filter.toLowerCase()))
                    searchList.add(entry.getKey());
            }
        }
    }

    public List<String> getPathToItem() {
        List<String> pathElements = new ArrayList();
        for (FilterableTreeItem item = (FilterableTreeItem) this.treeView.getSelectionModel().getSelectedItem();
             item != null; item = (FilterableTreeItem) item.getParent()) {
            pathElements.add(0, item.getValue().toString());
        }
        pathElements.remove("Categories"); //needed to get the correct yaml path
        return pathElements;
    }
}
