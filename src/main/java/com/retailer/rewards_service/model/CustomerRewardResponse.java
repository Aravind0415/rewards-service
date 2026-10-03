package com.retailer.rewards_service.model;

import java.util.List;

public record CustomerRewardResponse(
    String customerId,
    List<MonthlyReward> monthlyRewards,
    long totalRewardPoints
) {}