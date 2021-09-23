package com.polovyi.ivan.springpageableresponseproducer.controller;

import com.polovyi.ivan.springpageableresponseproducer.dto.CustomPageDTO;
import com.polovyi.ivan.springpageableresponseproducer.dto.CustomerResponse;
import com.polovyi.ivan.springpageableresponseproducer.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public record CustomerController(CustomerService customerService) {

    @GetMapping(path = "/v1/customers")
    @ResponseStatus(HttpStatus.OK)
    public Page<CustomerResponse> getAllPageable(@RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return new CustomPageDTO(customerService.getAllCustomers(page, size));
    }

    @GetMapping(path = "/v2/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllWithTotalPagesHeader(@RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            HttpServletResponse response) {
        Page<CustomerResponse> allCustomers = customerService.getAllCustomers(page, size);
        response.setHeader("total-pages", String.valueOf(allCustomers.getTotalPages()));
        return allCustomers.getContent();
    }
}
