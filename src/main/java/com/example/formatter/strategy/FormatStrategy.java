package com.example.formatter.strategy;

public interface FormatStrategy {
    String getType();
    String verify(String input) throws Exception;
    String minify(String input) throws Exception;
    String beautify(String input) throws Exception;
}