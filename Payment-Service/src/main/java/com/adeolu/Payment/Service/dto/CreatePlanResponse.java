package com.adeolu.Payment.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanResponse {

    private Boolean status;
    private String message;
   // private com.tobi.paymentService.dto.Data data;
    //which instance variable is this?
   private Data data;



}
