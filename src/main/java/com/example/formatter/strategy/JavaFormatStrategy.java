package com.example.formatter.strategy;

import com.google.googlejavaformat.java.Formatter;
import org.springframework.stereotype.Component;

@Component
public class JavaFormatStrategy implements FormatStrategy {

    private final Formatter formatter = new Formatter();

    @Override
    public String getType() { return "java"; }

    @Override
    public String verify(String input) {
        // Basic verification: attempt to format
        try {
            formatter.formatSource(input);
            return "Valid Java source";
        } catch (Exception e) {
            return "Invalid Java source: " + e.getMessage();
        }
    }

    @Override
    public String minify(String input) throws Exception {
        return formatter.formatSource(input).replaceAll("\s+", " ");
    }

    @Override
    public String beautify(String input) throws Exception {
        return formatter.formatSource(input);
    }
}