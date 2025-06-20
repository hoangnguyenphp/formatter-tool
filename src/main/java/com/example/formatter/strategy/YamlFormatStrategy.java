package com.example.formatter.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Component;

@Component
public class YamlFormatStrategy implements FormatStrategy {

    private final ObjectMapper yamlMapper;

    public YamlFormatStrategy() {
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
    }

    @Override
    public String getType() {
        return "yaml";
    }

    @Override
    public String verify(String input) throws Exception {
        yamlMapper.readTree(input); // throws on invalid YAML
        return "Valid YAML";
    }

    @Override
    public String minify(String input) throws Exception {
        JsonNode node = yamlMapper.readTree(input);
        return yamlMapper.writeValueAsString(node)
                         .replaceAll("[\\r\\n]+", "\n")  // normalize newlines
                         .replaceAll("(?m)^\\s+", "");  // remove leading spaces
    }

    @Override
    public String beautify(String input) throws Exception {
        JsonNode node = yamlMapper.readTree(input);
        return yamlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }
}
