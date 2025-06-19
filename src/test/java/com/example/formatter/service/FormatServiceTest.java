package com.example.formatter.service;

import com.example.formatter.dto.FormatRequest;
import com.example.formatter.dto.FormatResponse;
import com.example.formatter.strategy.JsonFormatStrategy;
import com.example.formatter.strategy.HtmlFormatStrategy;
import com.example.formatter.strategy.JavaFormatStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FormatServiceTest {

    private FormatService service;

    @BeforeEach
    public void setUp() {
        service = new FormatService(List.of(
            new JsonFormatStrategy(),
            new HtmlFormatStrategy(),
            new JavaFormatStrategy()
        ));
    }

    /*
    @Test
    public void testJsonBeautify() {
        String json = "{\"name\": \"John\", \"age\": 30}";
        FormatRequest req = new FormatRequest();
        req.setType("json");
        req.setContent(json);
        FormatResponse resp = service.handle("beautify", req);
        assertTrue(resp.isSuccess());
        assertTrue(resp.getResult().contains("\"name\": \"John\""));
    }

    @Test
    public void testHtmlMinify() {
        String html = "<html>  <body> Hello </body> </html>";
        FormatRequest req = new FormatRequest();
        req.setType("html");
        req.setContent(html);
        FormatResponse resp = service.handle("minify", req);
        assertTrue(resp.isSuccess());
        assertFalse(resp.getResult().contains("\n"));
    }
    */
}