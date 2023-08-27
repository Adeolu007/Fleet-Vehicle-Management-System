package com.adeolu.Payment.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomAttendeePaymentResponse {
    private String statusCode;
    private String statusMessage;
    private AttendeePaymentResponse attendeePaymentResponse;

}
