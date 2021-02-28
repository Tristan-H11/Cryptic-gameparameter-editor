package com.github.tristan_11.gameparameteryaml.model;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * Geht mit den YAML-Dateien um und lädt sie. Stellt die geladenen Daten zur Verfügung.
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("DM_DEFAULT_ENCODING")
public class YamlHandler {

    private static final Yaml YAML;

    static {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        YAML = new Yaml(dumperOptions);
    }

    Map<String, Object> obj;


    /**
     *  Konstruktor. Lädt die Datei mit einem InputStream.
     */
    public YamlHandler(InputStream is) throws IOException {
        obj = YAML.load(is);
        is.close();
    }

    /**
     * Konstruktor. Lädt die Datei mit einem Path.
     */
    public YamlHandler(String path) throws IOException {
        try (InputStream is = new FileInputStream(path)) {
            obj = YAML.load(is);
        }
    }

    /**
     * Gibt die geladenen Daten zurück.
     */
    public Map<String, Object> getResultsAsMap() {
        return obj;
    }

    /**
     * Schreibt die übergebenen Daten in die Datei.
     *
     * @param obj  MapToWriteToFile
     * @param path PathToFile
     */
    public void writeMapToYaml(Map<String, Object> obj, String path) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            YAML.dump(obj, writer);
        }
    }

}
