package com.github.tristan_11.gameparameteryaml.model;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.util.List;
import java.util.Map;

/**
 * Behandelt alles, was außer {@link TreeViewHandler} und {@link YamlHandler} anfällt.
 * Darunter fällt das Auslesen der Beschreibungen und Werte aus den Datei-Maps und schreiben an die geforderte Stelle.
 * Außerdem ist der {@link ValueHandler} dafür verantwortlich, die in der GUI geänderten Werte in die valueMap zu schreiben
 * und bei Knopfdruck zum speichern an den {@link YamlHandler} zu geben.
 */
public class ValueHandler {

    YamlHandler yamlDataHandler;
    YamlHandler yamlDescriptionHandler;
    JFXTextField valueTextField;
    JFXTextArea descriptionTextArea;

    public Map<String, Object> getValueMap() {
        return valueMap;
    }

    Map<String, Object> valueMap;
    Map<String, Object> descriptionMap;

    /**
     * Konstruktor. Setzt nur alle Werte in dem ValueHandler, um sie später richtig nutzen zu können.
     *
     * @param dataHandler         {@link YamlHandler} für die Werte der Spielparameter.
     * @param descriptionHandler  {@link YamlHandler} für die Werte der Spielparameter.
     * @param valueTextField
     * @param descriptionTextArea
     */
    public ValueHandler(YamlHandler dataHandler, YamlHandler descriptionHandler, JFXTextField valueTextField, JFXTextArea descriptionTextArea) {
        this.yamlDataHandler = dataHandler;
        this.yamlDescriptionHandler = descriptionHandler;
        this.valueTextField = valueTextField;
        this.descriptionTextArea = descriptionTextArea;
        valueMap = yamlDataHandler.getResultsAsMap();
        descriptionMap = yamlDescriptionHandler.getResultsAsMap();
    }

    //ToDo: Javadox schreiben
    public void loadValues(TreeViewHandler treeViewHandler) {
        List<String> path = treeViewHandler.getPathToItem();
        if (!path.isEmpty()) {
            getDescriptionFromMap(descriptionMap, treeViewHandler.getPathToItem(), descriptionTextArea);
            getValuesFromMap(valueMap, treeViewHandler.getPathToItem(), valueTextField); //dunno why, but first call seems
            //to manipulate path variable in second call, so is just get a new one
        }
    }

    private void getDescriptionFromMap(Map<String, Object> map, List<String> path, JFXTextArea description) {
        if (map.get(path.get(0)) instanceof Map) {
            map = (Map<String, Object>) map.get(path.get(0));
            if (path.size() > 1) path.remove(0);
            getDescriptionFromMap(map, path, description);
        } else {
            if (map.containsKey(path.get(0)) && !(map.get(path.get(0)) instanceof Map))
                description.setText(map.get(path.get(0)).toString());
        }
    }

    private void getValuesFromMap(Map<String, Object> map, List<String> path, JFXTextField valueField) {
        if (map.get(path.get(0)) instanceof Map) {
            map = (Map<String, Object>) map.get(path.get(0));
            if (path.size() > 1) path.remove(0);
            getValuesFromMap(map, path, valueField);
        } else {
            if (map.containsKey(path.get(0)) && !(map.get(path.get(0)) instanceof Map)) {
                valueField.setText(map.get(path.get(0)).toString());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void setNewValue(List<String> path, String newValue) {
        Map<String, Object> tempMap = this.valueMap;
        for (int i = 0; i < path.size() - 1; i++) {
            tempMap = (Map<String, Object>) tempMap.get(path.get(i));
        }
        tempMap.put(path.get(path.size() - 1), newValue);
    }

    @SuppressWarnings("unchecked")
    public void setDescription(List<String> path, String newValue) {
        Map<String, Object> tempMap = this.descriptionMap;
        for (int i = 0; i < path.size() - 1; i++) {
            tempMap = (Map<String, Object>) tempMap.get(path.get(i));
        }
        tempMap.put(path.get(path.size() - 1), newValue);
    }



}
