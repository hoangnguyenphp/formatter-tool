package com.example.formatter.service;

import com.example.formatter.dto.FormatRequest;
import com.example.formatter.dto.FormatResponse;
import com.example.formatter.strategy.FormatStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FormatService {

    private final Map<String, FormatStrategy> strategies;

    public FormatService(List<FormatStrategy> strategyList) {
        strategies = strategyList.stream()
            .collect(Collectors.toMap(FormatStrategy::getType, s -> s));
    }

    public FormatResponse handle(String action, FormatRequest request) {
        FormatStrategy strategy = strategies.get(request.getType().toLowerCase());
        if (strategy == null) {
            return new FormatResponse(false, "Unsupported type", null);
        }
        try {
            String result = switch (action.toLowerCase()) {
                case "verify" -> strategy.verify(request.getContent());
                case "minify" -> strategy.minify(request.getContent());
                case "beautify" -> strategy.beautify(request.getContent());
                default -> throw new IllegalArgumentException("Invalid action");
            };
            return new FormatResponse(true, "Success", result);
        } catch (Exception e) {
            return new FormatResponse(false, e.getMessage(), null);
        }
    }
}