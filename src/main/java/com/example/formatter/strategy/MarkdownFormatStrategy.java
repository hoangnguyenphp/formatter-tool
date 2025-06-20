package com.example.formatter.strategy;

import org.springframework.stereotype.Component;

@Component
public class MarkdownFormatStrategy implements FormatStrategy {
	/*
	 Would you like to extend this with a real Markdown parser (like commonmark-java) or add .markdown support as a type alias?
	 * */
    @Override
    public String getType() {
        return "md";  // accept "md" as the type
    }

    @Override
    public String verify(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Markdown content is empty.");
        }

        boolean hasMarkdownSyntax = input.contains("#") || input.contains("**") ||
                                    input.contains("*") || input.contains("- ") ||
                                    input.contains("```");

        if (!hasMarkdownSyntax) {
            throw new IllegalArgumentException("Not valid Markdown syntax.");
        }

        return "Valid Markdown content";
    }

    @Override
    public String minify(String input) {
        if (input == null) return "";
        // Remove excessive newlines and trailing spaces
        return input
            .replaceAll("(?m)^[ \\t]+", "")         // remove leading spaces
            .replaceAll("[ \\t]+$", "")             // remove trailing spaces
            .replaceAll("\\n{2,}", "\n")            // collapse multiple blank lines
            .trim();
    }

    @Override
    public String beautify(String input) {
        if (input == null) return "";

        // Normalize heading spacing and list indentations
        return input
            .replaceAll("(?m)^#{1,6}[^#\\s]", "# ") // ensure space after heading marker
            .replaceAll("(?m)^(-|\\*|\\+)\\s+", "- ") // normalize list bullets
            .replaceAll("\\r\\n?", "\n")            // normalize line endings
            .replaceAll("(?m)^[ \\t]+", "")          // remove leading spaces
            .trim();
    }
}
