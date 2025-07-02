package com.example.formatter.controller.pagevisitcounter;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.formatter.service.pagevisitcounter.PageVisitCounterService;

@RestController
@RequestMapping("/api/page-visit-counter")
public class PageVisitCounterController {
	private final PageVisitCounterService pageVisitCounterService;

    public PageVisitCounterController(PageVisitCounterService pageVisitCounterService) {
        this.pageVisitCounterService = pageVisitCounterService;
    }

    @GetMapping
    public synchronized int getVisitCount() throws IOException {
        return pageVisitCounterService.getVisitCount();
    }
}
