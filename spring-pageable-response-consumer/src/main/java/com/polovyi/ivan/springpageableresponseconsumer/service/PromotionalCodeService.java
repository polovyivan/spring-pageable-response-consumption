package com.polovyi.ivan.springpageableresponseconsumer.service;

import com.polovyi.ivan.springpageableresponseconsumer.client.CustomerServiceClient;
import com.polovyi.ivan.springpageableresponseconsumer.configuration.ApplicationConfiguration;
import com.polovyi.ivan.springpageableresponseconsumer.dto.CreatePromotionalCodeRequest;
import com.polovyi.ivan.springpageableresponseconsumer.dto.CustomerClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public record PromotionalCodeService(CustomerServiceClient customerServiceClient,
                                     ApplicationConfiguration applicationConfiguration) {

    public void createPromotionalCode(CreatePromotionalCodeRequest createPromotionalCodeRequest) {
        log.info("Creating promotional code...");
        List<CustomerClientResponse> customersEligibleForDiscount = customerServiceClient.getCustomersFromAllPages();
        sendMessage(customersEligibleForDiscount, createPromotionalCodeRequest);

    }

    public void createPromotionalCodeByGettingCustomersWithTotalPagesHeader(
            CreatePromotionalCodeRequest createPromotionalCodeRequest) {
        log.info("Creating promotional code by getting customers with total pages header...");
        List<CustomerClientResponse> customersEligibleForDiscount = customerServiceClient.getCustomersFromAllPagesWithHeader();
        sendMessage(customersEligibleForDiscount, createPromotionalCodeRequest);
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
