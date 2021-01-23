package com.github.tristan_11.gameparameteryaml.model;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValueHandler {

    YamlHandler yamlDataHandler;
    YamlHandler yamlDescriptionHandler;
    JFXTextField valueTextField;
    JFXTextArea descriptionTextArea;
    Map<String, Object> valueMap;
    Map<String, Object> descriptionMap;

    public ValueHandler(YamlHandler dataHandler, YamlHandler descriptionHandler, JFXTextField valueTextField, JFXTextArea descriptionTextArea) {
        this.yamlDataHandler = dataHandler;
        this.yamlDescriptionHandler = descriptionHandler;
        this.valueTextField = valueTextField;
        this.descriptionTextArea = descriptionTextArea;
        valueMap = yamlDataHandler.getResultsAsMap();
        descriptionMap = yamlDescriptionHandler.getResultsAsMap();
    }

    public void loadValues(TreeViewHandler treeViewHandler) {
        List<String> path = treeViewHandler.getPathToItem();
        if (!path.isEmpty()){
            getDescriptionFromMap(descriptionMap, treeViewHandler.getPathToItem(), descriptionTextArea);
            getValuesFromMap(valueMap, treeViewHandler.getPathToItem(), valueTextField); //dunno why, but first call seems
            //to manipulate path variable in second call, so is just get a new one
        }
    }
    //works
    private void getDescriptionFromMap(Map<String, Object> map, List<String> path, JFXTextArea description) {
        if (map.get(path.get(0)) instanceof Map) {
            map = (Map<String, Object>) map.get(path.get(0));
            if(path.size()>1)path.remove(0);
            getDescriptionFromMap(map, path, description);
        }
        else {
            if(map.containsKey(path.get(0)) && !(map.get(path.get(0)) instanceof Map))
            description.setText(map.get(path.get(0)).toString());
        }
    }

    //broken
    private void getValuesFromMap(Map<String, Object> map, List<String> path, JFXTextField valueField) {
        System.out.println("[Log] getValuesFromMap ist triggert");

        if (map.get(path.get(0)) instanceof Map) {
            map = (Map<String, Object>) map.get(path.get(0));
            if(path.size()>1)path.remove(0);
            System.out.println("[ValueLog] Im If drin");
            getValuesFromMap(map, path, valueField);
        }
        else {
            System.out.println("[ValueLog] Im else drin");
            if(map.containsKey(path.get(0)) && !(map.get(path.get(0)) instanceof Map)) {
                System.out.println("[ValueLog] Im valueSetting drin");
                valueField.setText(map.get(path.get(0)).toString());
                System.out.println("[ValueLog] Wert des Textfeldes: " + valueField.getText());
            }
        }
    }
}
