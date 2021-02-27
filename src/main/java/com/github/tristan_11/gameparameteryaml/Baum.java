package com.github.tristan_11.gameparameteryaml;

import java.util.ArrayList;

/**
 * Implementation einer Baumstruktur.
 */
public class Baum {

    final ArrayList<Baum> childList;
    String name = "";

    public Baum() {
        childList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Baum> getChildren() {
        return childList;
    }

    public void addChildren(ArrayList<Baum> children) {
        this.childList.addAll(children);
    }

    public int getLeavesBelow() {
        return getLeavesBelow(this);
    }

    /**
     * Gibt die Anzahl an Leaves ab eingeschlossen dieser Node zurück.
     */
    public int getLeavesBelow(Baum baum) {
        if (baum == null) {
            return 0;
        }
        if (baum.getChildren().isEmpty()) {
            return 1;
        } else {
            return baum.getChildren().stream().mapToInt(this::getLeavesBelow).sum();
        }
    }

}
