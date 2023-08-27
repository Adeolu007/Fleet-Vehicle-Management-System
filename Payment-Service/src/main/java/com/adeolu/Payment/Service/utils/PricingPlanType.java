package com.adeolu.Payment.Service.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
//@NoArgsConstructor
public enum PricingPlanType {

    BASIC("Basic"),
    STANDARD("Standard"),
    PREMIUM("Premium");

    private final String value;
//    PricingPlanType(String value) {
//        this.value = value;
//    }

}
