package com.example.formatter.strategy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonFormatStrategy implements FormatStrategy {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String getType() { return "json"; }

    @Override
    public String verify(String input) throws Exception {
        mapper.readTree(input);
        return "Valid JSON";
    }

    @Override
    public String minify(String input) throws Exception {
        JsonNode node = mapper.readTree(input);
        return mapper.writeValueAsString(node);
    }

    @Override
    public String beautify(String input) throws Exception {
        JsonNode node = mapper.readTree(input);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }
}