package com.adeolu.Payment.Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanDto {

//what does JsonProperty annotation do?
    @JsonProperty("name")
    private String name;


    @JsonProperty("interval")
    private String interval;


    @JsonProperty("amount")
    private Integer amount;
}
