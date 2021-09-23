package com.polovyi.ivan.springpageableresponseproducer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@JsonPropertyOrder({ "totalElements", "totalPages", "size", "numberOfElements", "number", "first", "last", "empty", "content" })
public class CustomPageDTO implements Page {

    private Page page;

    @Override public int getTotalPages() {
        return page.getTotalPages();
    }

    @Override public long getTotalElements() {
        return page.getTotalElements();
    }

    @JsonIgnore
    @Override public Page map(Function function) {
        return page.map(function);
    }

    @Override public int getNumber() {
        return page.getNumber();
    }

    @Override public int getSize() {
        return page.getSize();
    }

    @Override public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    @Override public List getContent() {
        return page.getContent();
    }

    @Override public boolean hasContent() {
        return page.hasContent();
    }

    @JsonIgnore
    @Override public Sort getSort() {
        return page.getSort();
    }

    @Override public boolean isFirst() {
        return page.isFirst();
    }

    @Override public boolean isLast() {
        return page.isLast();
    }

    @JsonIgnore
    @Override public boolean hasNext() {
        return page.hasNext();
    }

    @Override public boolean hasPrevious() {
        return page.hasPrevious();
    }

    @JsonIgnore
    @Override public Pageable nextPageable() {
        return page.nextPageable();
    }

    @JsonIgnore
    @Override public Pageable previousPageable() {
        return page.previousPageable();
    }

    @JsonIgnore
    @Override public Iterator iterator() {
        return page.iterator();
    }

    @JsonIgnore
    @Override public Pageable getPageable() {
        return Page.super.getPageable();
    }
}
