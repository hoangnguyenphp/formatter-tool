package com.example.formatter.entity.pagevisitcounter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "page_visit_counter")
public class PageVisitCounter {
    @Id
    private Long id = 1L;  // singleton row

    private int count;

    public PageVisitCounter() {}

    public PageVisitCounter(int count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
