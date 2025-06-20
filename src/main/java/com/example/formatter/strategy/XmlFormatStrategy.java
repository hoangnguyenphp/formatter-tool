package com.example.formatter.strategy;

import org.springframework.stereotype.Component;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

@Component
public class XmlFormatStrategy implements FormatStrategy {

    @Override
    public String getType() {
        return "xml";
    }

    @Override
    public String verify(String input) throws Exception {
        parseXml(input); // will throw if not valid
        return "Valid XML";
    }

    @Override
    public String minify(String input) throws Exception {
        Document doc = parseXml(input);
        return transform(doc, false);
    }

    @Override
    public String beautify(String input) throws Exception {
        Document doc = parseXml(input);
        return transform(doc, true);
    }

    private Document parseXml(String input) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(input)));
    }

    private String transform(Document doc, boolean prettyPrint) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        if (prettyPrint) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                "{http://xml.apache.org/xslt}indent-amount", "2"
            );
        } else {
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
        }

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }
}
