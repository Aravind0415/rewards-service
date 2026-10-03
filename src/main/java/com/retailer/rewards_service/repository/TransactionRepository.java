package com.retailer.rewards_service.repository;

import org.springframework.stereotype.Repository;

import com.retailer.rewards_service.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionRepository() {
        LocalDate today = LocalDate.now();

        // Customer C101 Transactions
        transactions.add(new Transaction(1L, "C101", 120.0, today.minusDays(5)));   // 90 pts
        transactions.add(new Transaction(2L, "C101", 100.0, today.minusDays(15)));  // 50 pts
        transactions.add(new Transaction(3L, "C101", 50.0,  today.minusDays(40)));  // 0 pts
        transactions.add(new Transaction(4L, "C101", 75.0,  today.minusDays(45)));  // 25 pts
        transactions.add(new Transaction(5L, "C101", 200.0, today.minusDays(75)));  // 250 pts

        // Customer C102 Transactions
        transactions.add(new Transaction(6L, "C102", 80.0,  today.minusDays(10)));  // 30 pts
        transactions.add(new Transaction(7L, "C102", 150.0, today.minusDays(35)));  // 150 pts
    }

    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    public List<Transaction> findByCustomerId(String customerId) {
        return transactions.stream()
                .filter(t -> t.customerId().equalsIgnoreCase(customerId))
                .toList();
    }
}