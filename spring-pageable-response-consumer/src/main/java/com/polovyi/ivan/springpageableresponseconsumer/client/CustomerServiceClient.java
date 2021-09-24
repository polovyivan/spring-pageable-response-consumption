package com.polovyi.ivan.springpageableresponseconsumer.client;

import com.polovyi.ivan.springpageableresponseconsumer.configuration.ApplicationConfiguration;
import com.polovyi.ivan.springpageableresponseconsumer.dto.CustomerClientPageableResponse;
import com.polovyi.ivan.springpageableresponseconsumer.dto.CustomerClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public record CustomerServiceClient(ApplicationConfiguration applicationConfiguration) {

    public CustomerClientPageableResponse getCustomers(Integer page, Integer size) {
        return WebClient.create().get()
                .uri(builder -> builder.path(applicationConfiguration.getExternalServiceCustomerGetCustomersUrl())
                        .queryParam("page", page)
                        .queryParam("size", size).build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(CustomerClientPageableResponse.class)
                .block();
    }

    public ResponseEntity<List<CustomerClientResponse>> getCustomerListWithTotalPagesHeader(Integer page, Integer size) {
        return WebClient.create().get()
                .uri(builder -> builder.path(
                                applicationConfiguration.getExternalServiceCustomerGetCustomersUrlWithHeader())
                        .queryParam("page", page)
                        .queryParam("size", size).build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<CustomerClientResponse>>() {
                }).block();
    }
}
