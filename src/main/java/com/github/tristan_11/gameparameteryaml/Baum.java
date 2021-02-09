package com.github.tristan_11.gameparameteryaml;

import java.util.ArrayList;
import java.util.HashSet;

public class Baum {

    String name = "";
    ArrayList<Baum> children;

    public Baum(){
        children = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Baum> getChildren() {
        return children;
    }

    public void addChildren(HashSet<Baum> children) {
        this.children.addAll(children);
    }
}
