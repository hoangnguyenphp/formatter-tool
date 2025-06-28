package com.example.formatter.strategy;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.PrettyPrinter;
import org.springframework.stereotype.Component;

@Component
public class JavaFormatStrategy implements FormatStrategy {

    private final JavaParser javaParser = new JavaParser();
    private final PrettyPrinter prettyPrinter = new PrettyPrinter();

    @Override
    public String getType() {
        return "java";
    }

    @Override
    public String verify(String input) {
        ParseResult<CompilationUnit> result = javaParser.parse(input);
        return result.isSuccessful() ? "Valid Java source" : "Invalid Java source: " + result.getProblems().toString();
    }

    @Override
    public String minify(String input) throws Exception {
    	return "Not support Java minifying.";
    }

    @Override
    public String beautify(String input) throws Exception {
        ParseResult<CompilationUnit> result = javaParser.parse(input);
        if (result.isSuccessful() && result.getResult().isPresent()) {
            return prettyPrinter.print(result.getResult().get());
        } else {
            throw new IllegalArgumentException("Invalid Java source: " + result.getProblems().toString());
        }
    }
}
