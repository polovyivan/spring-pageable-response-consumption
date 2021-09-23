package com.polovyi.ivan.springpageableresponseproducer.dto;

import com.polovyi.ivan.springpageableresponseproducer.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private String id;

    private LocalDate createdAt;

    private String gender;

    private String phoneNumber;

    public static CustomerResponse valueOf(CustomerEntity customer) {
        return builder()
                .id(customer.getId())
                .createdAt(customer.getCreatedAt())
                .gender(customer.getGender())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}
