package com.polovyi.ivan.springpageableresponseconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionalCodeRequest {

    private Long discountPercentage;

    private Integer yearsOfLoyalty;

    private String promoCode;
}
