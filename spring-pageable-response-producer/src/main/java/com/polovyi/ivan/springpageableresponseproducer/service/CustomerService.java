package com.polovyi.ivan.springpageableresponseproducer.service;

import com.polovyi.ivan.springpageableresponseproducer.dto.CustomerResponse;
import com.polovyi.ivan.springpageableresponseproducer.entity.CustomerEntity;
import com.polovyi.ivan.springpageableresponseproducer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository) {

    public Page<CustomerResponse> getAllCustomers(Integer pageNumber, Integer pageSize) {
        log.info("Getting all customers from pageNumber {} with pageNumber pageSize {}", pageNumber, pageSize);

        PageRequest page = PageRequest.of(pageNumber, pageSize);
        Page<CustomerEntity> customerEntityPage = customerRepository.findAll(page);

        Function<List<CustomerEntity>, List<CustomerResponse>> collectionTransform = x -> x.stream()
                .map(CustomerResponse::valueOf).collect(
                        Collectors.toList());

        Page<CustomerResponse> customerResponsePage = PageableExecutionUtils.getPage(
                collectionTransform.apply(customerEntityPage.getContent()),
                page,
                customerEntityPage::getTotalElements);

        return customerResponsePage;
    }
}

