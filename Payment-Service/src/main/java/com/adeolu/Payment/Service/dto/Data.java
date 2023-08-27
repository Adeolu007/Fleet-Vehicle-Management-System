package com.adeolu.Payment.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Data {
    private String authorization_url;
    private String access_code;
    private String reference;
}
