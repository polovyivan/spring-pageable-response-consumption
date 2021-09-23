package com.polovyi.ivan.springpageableresponseconsumer.controller;

import com.polovyi.ivan.springpageableresponseconsumer.dto.CreatePromotionalCodeRequest;
import com.polovyi.ivan.springpageableresponseconsumer.service.PromotionalCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public record PromotionalCodeController(PromotionalCodeService promotionalCodeService) {

    @PostMapping("/v1/promotional-code")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPromotionalCode(@RequestBody CreatePromotionalCodeRequest createPromotionalCodeRequest) {
        promotionalCodeService.createPromotionalCode(createPromotionalCodeRequest);
    }

    @PostMapping("/v2/promotional-code")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPromotionalCodeGettingCustomersWithTotalPagesHeader(@RequestBody CreatePromotionalCodeRequest createPromotionalCodeRequest) {
        promotionalCodeService.createPromotionalCodeByGettingCustomersWithTotalPagesHeader(createPromotionalCodeRequest);
    }
}
