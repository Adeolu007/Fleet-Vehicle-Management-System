package com.adeolu.Payment.Service.repository;

import com.adeolu.Payment.Service.enity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}