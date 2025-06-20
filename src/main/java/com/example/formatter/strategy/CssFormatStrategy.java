package com.example.formatter.strategy;

import org.springframework.stereotype.Component;

@Component
public class CssFormatStrategy implements FormatStrategy {

    @Override
    public String getType() {
        return "css";
    }

    @Override
    public String verify(String input) throws Exception {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("CSS content is empty.");
        }
        if (!input.contains("{") || !input.contains("}")) {
            throw new IllegalArgumentException("CSS does not contain valid blocks.");
        }
        return "Valid CSS";
    }

    @Override
    public String minify(String input) {
        if (input == null) return "";

        // Remove comments and collapse whitespace
        return input
                .replaceAll("/\\*.*?\\*/", "")   // remove comments
                .replaceAll("\\s+", " ")         // collapse spaces
                .replaceAll("\\s*([{};:,])\\s*", "$1") // trim around symbols
                .trim();
    }

    @Override
    public String beautify(String input) {
        if (input == null) return "";

        // Remove comments
        String noComments = input.replaceAll("/\\*.*?\\*/", "");

        String[] rules = noComments.split("}");
        StringBuilder formatted = new StringBuilder();

        for (String rule : rules) {
            if (rule.trim().isEmpty()) continue;

            String[] parts = rule.split("\\{", 2);
            if (parts.length < 2) continue;

            String selector = parts[0].trim();
            String declarations = parts[1].trim();

            formatted.append(selector).append(" {\n");
            for (String decl : declarations.split(";")) {
                decl = decl.trim();
                if (!decl.isEmpty()) {
                    formatted.append("  ").append(decl).append(";\n");
                }
            }
            formatted.append("}\n\n");
        }

        return formatted.toString().trim();
    }
}
