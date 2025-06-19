package com.example.formatter.controller;

import com.example.formatter.dto.FormatRequest;
import com.example.formatter.dto.FormatResponse;
import com.example.formatter.service.FormatService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/format")
public class FormatController {

    private final FormatService formatService;

    public FormatController(FormatService formatService) {
        this.formatService = formatService;
    }

    @Operation(summary = "Verify content syntax")
    @PostMapping("/verify")
    public ResponseEntity<FormatResponse> verify(@RequestBody FormatRequest request) {
        return ResponseEntity.ok(formatService.handle("verify", request));
    }

    @Operation(summary = "Minify content")
    @PostMapping("/minify")
    public ResponseEntity<FormatResponse> minify(@RequestBody FormatRequest request) {
        return ResponseEntity.ok(formatService.handle("minify", request));
    }

    @Operation(summary = "Beautify content")
    @PostMapping("/beautify")
    public ResponseEntity<FormatResponse> beautify(@RequestBody FormatRequest request) {
        return ResponseEntity.ok(formatService.handle("beautify", request));
    }
}