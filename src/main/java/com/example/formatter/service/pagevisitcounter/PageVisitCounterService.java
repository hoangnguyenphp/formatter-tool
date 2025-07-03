package com.example.formatter.service.pagevisitcounter;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.formatter.entity.pagevisitcounter.PageVisitCounter;
import com.example.formatter.repository.pagevisitcounter.PageVisitCounterRepository;

@Service
public class PageVisitCounterService {
	private final PageVisitCounterRepository pageVisitCounterRepository;

	public PageVisitCounterService(PageVisitCounterRepository pageVisitCounterRepository) {
		this.pageVisitCounterRepository = pageVisitCounterRepository;
	}

	public synchronized int getVisitCount() throws IOException {
		PageVisitCounter counter = pageVisitCounterRepository.findById(1L).orElseGet(() -> {
			PageVisitCounter newCounter = new PageVisitCounter(0);
			return pageVisitCounterRepository.save(newCounter);
		});

		counter.setCount(counter.getCount() + 1);
		pageVisitCounterRepository.save(counter);
		return counter.getCount();
	}

}
