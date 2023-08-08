package com.adeolu.Driver.Management.Service.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Driver {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String licenseNumber;
}
