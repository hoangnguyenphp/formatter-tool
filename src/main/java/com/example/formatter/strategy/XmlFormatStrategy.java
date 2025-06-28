package com.example.formatter.strategy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.StringReader;
import java.io.StringWriter;

import org.xml.sax.InputSource;

@Component
public class XmlFormatStrategy implements FormatStrategy {

    @Override
    public String getType() {
        return "xml";
    }

    @Override
    public String verify(String input) throws Exception {
        parseXmlDom(input); // will throw exception if invalid
        return "Valid XML";
    }

    @Override
    public String minify(String input) throws Exception {
        Document doc = parseXmlDom(input);
        String rawXml = transformXml(doc, false);
        return cleanMinifiedXml(rawXml);
    }

    @Override
    public String beautify(String input) throws Exception {
        // Clean up whitespace before beautify to avoid extra line breaks
        String cleaned = Jsoup.parse(input, "", Parser.xmlParser())
                .outerHtml()
                .replaceAll(">\\s+<", "><"); // remove extra whitespace between tags

        Document doc = parseXmlDom(cleaned);
        return transformXml(doc, true);
    }

    // Parses XML string into DOM Document
    private Document parseXmlDom(String input) throws Exception {
        var factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        var builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(input)));
    }

    // Transforms DOM Document to formatted XML string
    private String transformXml(Document doc, boolean prettyPrint) throws Exception {
        var transformerFactory = TransformerFactory.newInstance();
        var transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, prettyPrint ? "yes" : "no");

        if (prettyPrint) {
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        }

        var writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    // Further minify by removing redundant whitespace between tags
    private String cleanMinifiedXml(String xml) {
        var jsoupDoc = Jsoup.parse(xml, "", Parser.xmlParser());
        jsoupDoc.outputSettings(new OutputSettings()
                .prettyPrint(false)
                .outline(false)
                .indentAmount(0));
        return jsoupDoc.outerHtml().replaceAll(">\\s+<", "><");
    }
}
