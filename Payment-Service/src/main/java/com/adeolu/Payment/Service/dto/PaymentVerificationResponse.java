package com.adeolu.Payment.Service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVerificationResponse {
    private String status;
    private String message;
    private PaymentData data;
}
