package com.github.tristan_11.gameparameteryaml.model;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

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

    public void loadValues(List<String> path) {
        System.out.println(valueMap);
        System.out.println(descriptionMap);
        System.out.println("LoadValues ist triggert");
        System.out.println(path);
        if (!path.isEmpty()) getDescriptionFromMap(descriptionMap, path, descriptionTextArea);
    }

    private void getDescriptionFromMap(Map<String, Object> map, List<String> path, JFXTextArea description) {
        System.out.println("getDescriptionFromMap ist triggert");

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
}
