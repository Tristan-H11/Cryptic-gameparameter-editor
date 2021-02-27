package com.github.tristan_11.gameparameteryaml.model;

import com.github.tristan_11.gameparameteryaml.Baum;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.*;

/**
 * Handler für den TreeView. Bastelt alles um den TreeView und holt sich oder bekommt die entsprechenden Sachen.
 */
public class TreeViewHandler {
    Baum rootBaum;
    TreeView<String> treeView;
    boolean expandEverything;


    /**
     * Konstruktor. Setzt die gegebenen Werte im Objekt.
     *
     * @param treeView Der TreeView, der gehandled wird.
     */
    public TreeViewHandler(Map<String, Object> map, TreeView<String> treeView) {
        this.treeView = treeView;

        this.rootBaum = new Baum();
        mapToBaum(map, rootBaum);
    }

    public void setExpandEverything(boolean expandEverything) {
        this.expandEverything = expandEverything;
    }

    /**
     * Setzt den Tree. Bekommt den Filter übergeben und holt sich die Daten aus der Yaml.
     *
     * @param filter Filtertext, nach dem im TreeView gesucht wird.
     */
    public void setData(String filter) {

        TreeItem<String> rootItem;
        Baum filteredBaum = applyFilter(rootBaum, filter);
        if (filteredBaum==null) {
            rootItem = new TreeItem<>("");
        }
        else {
            rootItem = baumToTreeItem(filteredBaum);
        }

        //Setzt den Filter für die leaves
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);
    }

    private TreeItem<String> baumToTreeItem(Baum baum){
        TreeItem<String> item = new TreeItem<>(baum.getName());
        for (Baum child : baum.getChildren()) {
            if(expandEverything) item.setExpanded(true);
            item.getChildren().add(baumToTreeItem(child));
        }
        return item;
    }

    private Baum applyFilter(Baum baum, String filter) {

        if(baum.getName().contains(filter)){
            return baum;
        }

        ArrayList<Baum> appliedChildren = new ArrayList<>();
        Baum returner;
        for (Baum child : baum.getChildren()) {
            returner = applyFilter(child, filter);
            if (returner !=null) {
                appliedChildren.add(returner);
            }
        }
        if(appliedChildren.isEmpty()){
            return null;
        }
        returner = new Baum();
        returner.setName(baum.getName());
        returner.addChildren(appliedChildren);
        return returner;
    }


    /**
     * Bekommt die map aus der yaml übergeben und baut rekursiv bis auf variablen level das TreeItem runter.
     */
    @SuppressWarnings("unchecked")
    private void mapToBaum(Map<String, Object> map, Baum baum) {
        map.forEach((s, o) -> {
            Baum treeItem = new Baum();
            treeItem.setName(s);
            if (o instanceof Map )
                this.mapToBaum((Map<String, Object>) o, treeItem);

            baum.getChildren().add(treeItem);
        });
    }

    /**
     * Gibt eine Liste<String> mit den Parents von 1stLevel->leave zurück. (Exklusive Root)
     *
     * @return {@link List} pathElements
     */
    public ArrayList<String> getPathToItem() {
        ArrayList<String> pathElements = new ArrayList<>();
        for (TreeItem<String> item = this.treeView.getSelectionModel().getSelectedItem();
             item != null; item = item.getParent()) {
            pathElements.add(0, item.getValue());
        }
        if(!pathElements.isEmpty()) {
            pathElements.remove(0);
        }
        return pathElements;
    }

    public int getLeaveCount() {
       return rootBaum.getLeavesBelow();
    }
}

