package com.retailer.rewards_service.service;

import java.util.List;
import com.retailer.rewards_service.model.CustomerRewardResponse;

public interface RewardService {
    CustomerRewardResponse getRewardsByCustomerId(String customerId);
    List<CustomerRewardResponse> getAllCustomerRewards();
    long calculatePoints(double amount);
}