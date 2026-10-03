package com.retailer.rewards_service.model;

import java.time.LocalDate;

public record Transaction(
    Long id,
    String customerId,
    double amount,
    LocalDate transactionDate
) {}