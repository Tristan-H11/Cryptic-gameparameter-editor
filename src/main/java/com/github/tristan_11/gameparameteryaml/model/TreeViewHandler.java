package com.github.tristan_11.gameparameteryaml.model;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handler für den TreeView. Bastelt alles um den TreeView und holt sich oder bekommt die entsprechenden Sachen.
 *
 */
public class TreeViewHandler {
    YamlHandler yamlHandler;
    JFXTreeView treeView;
    JFXTextField filterField;

    /**
     * Konstruktor. Setzt die gegebenen Werte im Objekt.
     * @param yamlHandler {@link YamlHandler} Um die YAML-Daten zu zerfleischen.
     * @param treeView Der TreeView, der gehandled wird.
     * @param filterField Das TextField, in dem der Filtertext für den TreeView steht.
     */
    public TreeViewHandler(YamlHandler yamlHandler, JFXTreeView<?> treeView, JFXTextField filterField) {
        this.yamlHandler = yamlHandler;
        this.treeView = treeView;
        this.filterField = filterField;
    }

    /**
     * Setzt den Tree. Bekommt den Filter übergeben und holt sich die Daten aus der Yaml.
     * @param filter Filtertext, nach dem im TreeView gesucht wird.
     */
    public void setData(String filter) {
        FilterableTreeItem<String> rootItem = new FilterableTreeItem<String>("Categories");
        Map<String, Object> resultsAsMap = yamlHandler.getResultsAsMap();

        generateItems(resultsAsMap, rootItem, filter);

        //Setzt den Filter für die leaves
        rootItem.predicateProperty().setValue(e -> e.contains(filter));
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
        map.forEach((s, o) -> {
            FilterableTreeItem treeitem = new FilterableTreeItem(s);
            if (o instanceof Map) {
                this.generateItems((Map<String, Object>) o, treeitem, filter);
            }
            item.getSourceChildren().add(treeitem);
        });
    }

    /**
     * Gibt eine Liste<String> mit den Parents von 1stLevel->leave zurück. (Exklusive Root)
     * @return {@link List} pathElements
     */
    public List<String> getPathToItem() {
        List<String> pathElements = new ArrayList();
        for (FilterableTreeItem item = (FilterableTreeItem) this.treeView.getSelectionModel().getSelectedItem();
             item != null; item = (FilterableTreeItem) item.getParent()) {
            pathElements.add(0, item.getValue().toString());
        }
        pathElements.remove("Categories"); //needed to get the correct yaml path. This is just the TreeViewRoot
        return pathElements;
    }
}
