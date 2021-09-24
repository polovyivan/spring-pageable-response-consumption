package com.polovyi.ivan.springpageableresponseconsumer.service;

import com.polovyi.ivan.springpageableresponseconsumer.client.CustomerServiceClient;
import com.polovyi.ivan.springpageableresponseconsumer.configuration.ApplicationConfiguration;
import com.polovyi.ivan.springpageableresponseconsumer.dto.CreatePromotionalCodeRequest;
import com.polovyi.ivan.springpageableresponseconsumer.dto.CustomerClientPageableResponse;
import com.polovyi.ivan.springpageableresponseconsumer.dto.CustomerClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public record PromotionalCodeService(CustomerServiceClient customerServiceClient, ApplicationConfiguration applicationConfiguration) {

    private static int LIMIT = 50;

    public void createPromotionalCode(CreatePromotionalCodeRequest createPromotionalCodeRequest) {
        log.info("Creating promotional code...");
        int currentPage = 0;
        List<CustomerClientResponse> customersResponseCompleteCollection = new ArrayList<>();
        CustomerClientPageableResponse customerClientPageableResponse;
        do {
            log.info("Getting customers from a page {} with a limit {}", currentPage, LIMIT);
            customerClientPageableResponse = customerServiceClient.getCustomers(currentPage++, LIMIT);

            List<CustomerClientResponse> customerClientResponseFromSinglePage = customerClientPageableResponse.getContent();

            if (!CollectionUtils.isEmpty(customerClientResponseFromSinglePage)) {
                customersResponseCompleteCollection.addAll(customerClientResponseFromSinglePage);
            }
        } while (customerClientPageableResponse.hasNext());

        log.info("Received all customers from total of {} page(s)", customerClientPageableResponse.getTotalPages());
        sendMessage(customersResponseCompleteCollection, createPromotionalCodeRequest);
    }

    public void createPromotionalCodeByGettingCustomersWithTotalPagesHeader(CreatePromotionalCodeRequest createPromotionalCodeRequest) {
        log.info("Creating promotional code by getting customers with total pages header...");

        int currentPage = 0;
        int limit = 50;
        int totalPages;
        List<CustomerClientResponse> customersResponseCompleteCollection = new ArrayList<>();
        ResponseEntity<List<CustomerClientResponse>> responseEntity;
        do {
            log.info("Getting customers from a page {} with a limit {}", currentPage, limit);
            responseEntity = customerServiceClient.getCustomerListWithTotalPagesHeader(currentPage++, limit);

            List<CustomerClientResponse> customerClientResponseFromSinglePage = responseEntity.getBody();

            if (!CollectionUtils.isEmpty(customerClientResponseFromSinglePage)) {
                customersResponseCompleteCollection.addAll(customerClientResponseFromSinglePage);
            }
            totalPages = responseEntity.getHeaders().get("total-pages").stream().findFirst()
                    .map(Integer::parseInt).orElse(0);
        } while (currentPage < totalPages);

        log.info("Received all customers from total of {} page(s)", currentPage);
        sendMessage(customersResponseCompleteCollection, createPromotionalCodeRequest);
    }

    private void sendMessage(List<CustomerClientResponse> customersEligibleForDiscount,
            CreatePromotionalCodeRequest createPromotionalCodeRequest) {
        log.info("Sending messages...");
        customersEligibleForDiscount.stream()
                .filter(customer -> (LocalDate.now().getYear() - customer.getCreatedAt().getYear())
                        >= createPromotionalCodeRequest.getYearsOfLoyalty()).forEach(
                        c -> System.out.println(String.format(applicationConfiguration.getPromotionalSMS(),
                                LocalDate.now().getYear() - c.getCreatedAt().getYear(),
                                createPromotionalCodeRequest.getPromoCode(),
                                createPromotionalCodeRequest.getDiscountPercentage(), c.getPhoneNumber())));
    }
}
