package com.example.formatter.strategy;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class HtmlFormatStrategy implements FormatStrategy {

    @Override
    public String getType() {
        return "html";
    }

    @Override
    public String verify(String input) {
        Jsoup.parse(input); // nếu parse không lỗi thì hợp lệ
        return "Valid HTML";
    }

    @Override
    public String minify(String input) {
        HtmlCompressor compressor = new HtmlCompressor();
        compressor.setRemoveComments(true);
        compressor.setRemoveMultiSpaces(true);
        compressor.setRemoveIntertagSpaces(true);
        compressor.setRemoveQuotes(true);

        // Không nén JavaScript/CSS để tránh lỗi thiếu Rhino
        compressor.setCompressCss(false);
        compressor.setCompressJavaScript(false);

        return compressor.compress(input);
    }


    @Override
    public String beautify(String input) {
        return Jsoup.parse(input).outputSettings(
                new org.jsoup.nodes.Document.OutputSettings().indentAmount(2).prettyPrint(true)
        ).outerHtml();
    }
}
