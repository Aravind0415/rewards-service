package com.retailer.rewards_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.retailer.rewards_service.model.CustomerRewardResponse;
import com.retailer.rewards_service.service.RewardService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerRewardResponse>> getAllCustomerRewards() {
        return ResponseEntity.ok(rewardService.getAllCustomerRewards());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerRewardResponse> getRewardsByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(rewardService.getRewardsByCustomerId(customerId));
    }
}