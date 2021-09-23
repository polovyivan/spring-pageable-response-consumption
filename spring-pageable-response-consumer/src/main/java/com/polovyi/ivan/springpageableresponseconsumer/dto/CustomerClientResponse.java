package com.polovyi.ivan.springpageableresponseconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerClientResponse {

    private String id;

    private LocalDate createdAt;

    private String gender;

    private String phoneNumber;

}
