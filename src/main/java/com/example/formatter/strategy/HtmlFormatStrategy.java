package com.example.formatter.strategy;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class HtmlFormatStrategy implements FormatStrategy {

    @Override
    public String getType() { return "html"; }

    @Override
    public String verify(String input) {
        Jsoup.parse(input);
        return "Valid HTML";
    }

    @Override
    public String minify(String input) {
        return Jsoup.parse(input).html().replaceAll("\s+", " ");
    }

    @Override
    public String beautify(String input) {
        return Jsoup.parse(input).outerHtml();
    }
}