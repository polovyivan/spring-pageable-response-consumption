package com.polovyi.ivan.springpageableresponseconsumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class CustomerClientPageableResponse extends PageImpl<CustomerClientResponse> {

    public CustomerClientPageableResponse(@JsonProperty("content") List<CustomerClientResponse> content,
            @JsonProperty("number") int number, @JsonProperty("size") int size,
            @JsonProperty("totalElements") Long totalElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }
}
