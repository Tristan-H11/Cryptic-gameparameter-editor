package com.github.tristan_11.gameparameteryaml.model;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class YamlHandler {

    Map<String, Object> obj;
    private static final Yaml YAML;
    static{
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        YAML=new Yaml(dumperOptions);
    }


    public YamlHandler(String filepath) throws IOException {
        try (InputStream inputStream =new FileInputStream(filepath)){
            obj = YAML.load(inputStream);
        }
    }

    public Map<String, Object> getResultsAsMap() {
        return obj;
    }

    public void writeMapToYaml(Map<String, Object> obj, String path) throws IOException {
        try(FileWriter writer = new FileWriter(path)){
            YAML.dump(obj, writer);
        }
    }

}
