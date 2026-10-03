package com.retailer.rewards_service.model;

import java.time.Month;

public record MonthlyReward(
    Month month,
    int year,
    long points
) {}