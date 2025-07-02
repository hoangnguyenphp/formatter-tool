package com.example.formatter.service.pagevisitcounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class PageVisitCounterService {
    private static final Path COUNTER_FILE = Paths.get("src/main/resources/visit-page-counter/counter.txt");
    private static final AtomicInteger counter = new AtomicInteger(loadInitialCount());
    
    public PageVisitCounterService() throws IOException {
        ensureCounterFileExists();
    }
    
    public synchronized int getVisitCount() throws IOException {
        int newCount = counter.incrementAndGet();
        saveCounter(newCount);
        return newCount;
    }

    private static void ensureCounterFileExists() throws IOException {
        if (Files.notExists(COUNTER_FILE.getParent())) {
            Files.createDirectories(COUNTER_FILE.getParent());
        }
        if (Files.notExists(COUNTER_FILE)) {
            Files.writeString(COUNTER_FILE, "0");
        }
    }

    private static int loadInitialCount() {
        try {
            ensureCounterFileExists();
            String content = Files.readString(COUNTER_FILE).trim();
            return Integer.parseInt(content);
        } catch (Exception e) {
            return 0;
        }
    }

    private static void saveCounter(int count) throws IOException {
        Files.writeString(COUNTER_FILE, String.valueOf(count), StandardOpenOption.TRUNCATE_EXISTING);
    }
}
