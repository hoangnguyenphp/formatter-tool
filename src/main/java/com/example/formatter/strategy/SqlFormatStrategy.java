package com.example.formatter.strategy;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.languages.Dialect;
import org.springframework.stereotype.Component;

@Component
public class SqlFormatStrategy implements FormatStrategy {

    @Override
    public String getType() {
        return "sql";
    }

    @Override
    public String verify(String input) {
        // Very basic SQL verification (no parsing)
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL input is empty");
        }
        if (!input.trim().endsWith(";")) {
            throw new IllegalArgumentException("SQL should end with a semicolon.");
        }
        String upper = input.toUpperCase();
        if (!(upper.contains("SELECT") || upper.contains("INSERT") ||
              upper.contains("UPDATE") || upper.contains("DELETE"))) {
            throw new IllegalArgumentException("Unrecognized SQL statement.");
        }
        return "Valid SQL statement";
    }

    @Override
    public String minify(String input) {
        if (input == null) return "";

        // Remove block comments: /* ... */
        String noBlockComments = input.replaceAll("(?s)/\\*.*?\\*/", "");

        // Remove inline comments: -- ... to end of line
        String noInlineComments = noBlockComments.replaceAll("(?m)--.*?$", "");

        // Collapse all whitespace (spaces, tabs, newlines) into a single space
        return noInlineComments.replaceAll("\\s+", " ").trim();
    }

    @Override
    public String beautify(String input) {
        if (input == null || input.isBlank()) return input;

        // Use sql-formatter with Standard SQL dialect
        return SqlFormatter.of(Dialect.StandardSql).format(input);
    }
}
