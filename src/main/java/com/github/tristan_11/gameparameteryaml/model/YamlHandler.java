package com.github.tristan_11.gameparameteryaml.model;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Geht mit den YAML-Dateien um und lädt sie. Stellt die geladenen Daten zur Verfügung.
 */
public class YamlHandler {

    Map<String, Object> obj;
    private static final Yaml YAML;


    static {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        YAML = new Yaml(dumperOptions);
    }


    /**
     * Konstruktor. Setzt den Filepath und lädt die Datei.
     * @param filepath
     * @throws IOException
     */
    public YamlHandler(String filepath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filepath)) {
            obj = YAML.load(inputStream);
        }
    }

    /**
     * Gibt die geladenen Daten zurück.
     * @return
     */
    public Map<String, Object> getResultsAsMap() {
        return obj;
    }

    /**
     * Schreibt die übergebenen Daten in die Datei.
     * @param obj MapToWriteToFile
     * @param path PathToFile
     * @throws IOException
     */
    public void writeMapToYaml(Map<String, Object> obj, String path) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            YAML.dump(obj, writer);
        }
    }

}