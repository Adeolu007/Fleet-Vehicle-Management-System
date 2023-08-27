package com.adeolu.Payment.Service.service;

import com.adeolu.Payment.Service.dto.*;

public interface PaymentService {
    CreatePlanResponse createPlan(CreatePlanDto createPlanDto) throws Exception;
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse paymentVerification(String reference) throws Exception;
}
