package com.retailer.rewards_service.service;

import org.springframework.stereotype.Service;

import com.retailer.rewards_service.exception.CustomerNotFoundException;
import com.retailer.rewards_service.model.CustomerRewardResponse;
import com.retailer.rewards_service.model.MonthlyReward;
import com.retailer.rewards_service.model.Transaction;
import com.retailer.rewards_service.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardServiceImpl implements RewardService {

    private final TransactionRepository transactionRepository;

    public RewardServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public CustomerRewardResponse getRewardsByCustomerId(String customerId) {
        List<Transaction> customerTransactions = transactionRepository.findByCustomerId(customerId);

        if (customerTransactions.isEmpty()) {
            throw new CustomerNotFoundException("No transactions found for customer ID: " + customerId);
        }

        return buildRewardResponse(customerId, customerTransactions);
    }

    @Override
    public List<CustomerRewardResponse> getAllCustomerRewards() {
        List<Transaction> allTransactions = transactionRepository.findAll();

        Map<String, List<Transaction>> transactionsByCustomer = allTransactions.stream()
                .collect(Collectors.groupingBy(Transaction::customerId));

        return transactionsByCustomer.entrySet().stream()
                .map(entry -> buildRewardResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public long calculatePoints(double amount) {
        long points = 0;
        if (amount > 100) {
            points += Math.round((amount - 100) * 2);
            amount = 100;
        }
        if (amount > 50) {
            points += Math.round(amount - 50);
        }
        return points;
    }

    private CustomerRewardResponse buildRewardResponse(String customerId, List<Transaction> transactions) {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        List<Transaction> recentTransactions = transactions.stream()
                .filter(t -> !t.transactionDate().isBefore(threeMonthsAgo))
                .toList();

        Map<YearMonth, Long> pointsByMonth = recentTransactions.stream()
                .collect(Collectors.groupingBy(
                        t -> YearMonth.from(t.transactionDate()),
                        Collectors.summingLong(t -> calculatePoints(t.amount()))
                ));

        List<MonthlyReward> monthlyRewards = pointsByMonth.entrySet().stream()
                .map(entry -> new MonthlyReward(
                        entry.getKey().getMonth(),
                        entry.getKey().getYear(),
                        entry.getValue()
                ))
                .sorted((a, b) -> a.month().compareTo(b.month()))
                .collect(Collectors.toList());

        long totalPoints = monthlyRewards.stream()
                .mapToLong(MonthlyReward::points)
                .sum();

        return new CustomerRewardResponse(customerId, monthlyRewards, totalPoints);
    }
}