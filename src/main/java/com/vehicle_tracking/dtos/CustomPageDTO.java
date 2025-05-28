package com.vehicle_tracking.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

// Example Custom Page DTO
@Getter
@Setter
public class CustomPageDTO<T> {
    private List<T> content;
    private int number; // page number
    private int size;   // page size
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;


    // Constructor to map from Spring Data Page
    public CustomPageDTO(Page<T> page) {
        this.content = page.getContent();
        this.number = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
        this.first = page.isFirst();
    }
}