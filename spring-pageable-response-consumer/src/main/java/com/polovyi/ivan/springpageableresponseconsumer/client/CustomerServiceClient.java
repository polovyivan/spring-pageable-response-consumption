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
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
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

    public ResponseEntity<List<CustomerClientResponse>> getCustomerListWithTotalPageHeader(Integer page, Integer size) {
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

    public List<CustomerClientResponse> getCustomersFromAllPages() {
        int currentPage = 0;
        int limit = 50;
        List<CustomerClientResponse> customersResponseCompleteCollection = new ArrayList<>();
        CustomerClientPageableResponse customerClientPageableResponse;
        do {
            log.info("Getting customers from a page {} with a limit {}", currentPage, limit);
            customerClientPageableResponse = getCustomers(currentPage++, limit);

            List<CustomerClientResponse> customerClientResponseFromSinglePage = customerClientPageableResponse.getContent();

            if (!CollectionUtils.isEmpty(customerClientResponseFromSinglePage)) {
                customersResponseCompleteCollection.addAll(customerClientResponseFromSinglePage);
            }
        } while (customerClientPageableResponse.hasNext());

        log.info("Received all customers from total of {} page(s)", customerClientPageableResponse.getTotalPages());
        return customersResponseCompleteCollection;
    }

    public List<CustomerClientResponse> getCustomersFromAllPagesWithHeader() {
        int currentPage = 0;
        int limit = 50;
        int totalPages;
        List<CustomerClientResponse> customersResponseCompleteCollection = new ArrayList<>();
        ResponseEntity<List<CustomerClientResponse>> responseEntity;
        do {
            log.info("Getting customers from a page {} with a limit {}", currentPage, limit);
            responseEntity = getCustomerListWithTotalPageHeader(currentPage++, limit);

            List<CustomerClientResponse> customerClientResponseFromSinglePage = responseEntity.getBody();

            if (!CollectionUtils.isEmpty(customerClientResponseFromSinglePage)) {
                customersResponseCompleteCollection.addAll(customerClientResponseFromSinglePage);
            }
            totalPages = responseEntity.getHeaders().get("total-pages").stream().findFirst()
                    .map(Integer::parseInt).orElse(0);
        } while (currentPage < totalPages);

        log.info("Received all customers from total of {} page(s)", currentPage);
        return customersResponseCompleteCollection;
    }

}
