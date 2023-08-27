package com.adeolu.Payment.Service.enity;

import com.adeolu.Payment.Service.utils.PricingPlanType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String reference;
    private BigDecimal amount;
    private String gatewayResponse;
    private String paidAt;
    private String createdAt;
    private String channel;
    private String currency;
    private String ipAddress;
    @Enumerated(EnumType.STRING)
    private PricingPlanType pricingPlanType = PricingPlanType.BASIC;

    @CreationTimestamp
    private Date createdOn;
}
