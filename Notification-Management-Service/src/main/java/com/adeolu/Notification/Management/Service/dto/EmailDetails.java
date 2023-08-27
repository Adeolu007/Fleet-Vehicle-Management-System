package com.adeolu.Notification.Management.Service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmailDetails {
    private String recipient;
    private String message;
    private String subject;
    private String attachment;
}
