package com.example.formatter.repository.pagevisitcounter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.formatter.entity.pagevisitcounter.PageVisitCounter;

@Repository
public interface PageVisitCounterRepository extends JpaRepository<PageVisitCounter, Long> {

}
