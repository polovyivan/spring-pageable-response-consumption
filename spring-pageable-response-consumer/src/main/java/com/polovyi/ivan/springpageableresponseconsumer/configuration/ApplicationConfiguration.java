package com.polovyi.ivan.springpageableresponseconsumer.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationConfiguration {

    @Value("${external.service.customer.get-customers-url}")
    private String externalServiceCustomerGetCustomersUrl;

    @Value("${external.service.customer.get-customers-url-with-header}")
    private String externalServiceCustomerGetCustomersUrlWithHeader;

    @Value("${message.sms}")
    private String promotionalSMS;
}
