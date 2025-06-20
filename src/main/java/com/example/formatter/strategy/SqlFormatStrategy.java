package com.example.formatter.strategy;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class SqlFormatStrategy implements FormatStrategy {

    @Override
    public String getType() {
        return "sql";
    }

    @Override
    public String verify(String input) {
        // Basic validation: non-empty, ends with semicolon, contains SQL keywords
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL input is empty");
        }
        if (!input.trim().endsWith(";")) {
            throw new IllegalArgumentException("SQL statement should end with a semicolon.");
        }
        String upper = input.toUpperCase();
        if (!(upper.contains("SELECT") || upper.contains("INSERT") ||
              upper.contains("UPDATE") || upper.contains("DELETE"))) {
            throw new IllegalArgumentException("Not a recognizable SQL statement.");
        }
        return "Valid SQL statement";
    }

    @Override
    public String minify(String input) {
        // Strip newlines and collapse multiple spaces
        return input.replaceAll("\\s+", " ").trim();
    }

    @Override
    public String beautify(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }

        // Break SQL by keywords
        String[] keywords = {
            "SELECT", "FROM", "WHERE", "GROUP BY", "ORDER BY", "HAVING",
            "INSERT INTO", "VALUES", "UPDATE", "SET", "DELETE", "JOIN", "LEFT JOIN", "RIGHT JOIN", "INNER JOIN", "ON"
        };

        String result = input;
        for (String keyword : keywords) {
            result = result.replaceAll("(?i)\\b" + keyword + "\\b", "\n" + keyword);
        }

        // Cleanup extra newlines and indentation
        return Arrays.stream(result.strip().split("\n"))
                     .map(String::trim)
                     .filter(line -> !line.isEmpty())
                     .map(line -> "  " + line)
                     .collect(Collectors.joining("\n"));
    }
}
