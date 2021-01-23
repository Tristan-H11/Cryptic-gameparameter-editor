package com.github.tristan_11.gameparameteryaml.model;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YamlHandler {

    Object obj;
    Yaml yaml;
    InputStream inputStream;

    public YamlHandler(String filepath) throws IOException {
        yaml = new Yaml();
        try (InputStream inputStream =YamlHandler.class.getClassLoader().getResourceAsStream(filepath)){
            obj = yaml.load(inputStream);
        }
    }

    public Map<String, Object> getResultsAsMap() {
        return (Map<String, Object>) obj;
    }

}
