package com.retailer.rewards_service;

import com.retailer.rewards_service.model.CustomerRewardResponse;
import com.retailer.rewards_service.model.Transaction;
import com.retailer.rewards_service.repository.TransactionRepository;
import com.retailer.rewards_service.service.RewardServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardServiceImpl rewardService;

    // --- Boundary Value Tests ---

    @Test
    @DisplayName("Purchase under $50 should earn 0 points")
    void testCalculatePoints_UnderFiftyDollars() {
        long points = rewardService.calculatePoints(49.99);
        assertEquals(0, points);
    }

    @Test
    @DisplayName("Boundary Test: Exactly $50 should earn 0 points")
    void testCalculatePoints_ExactlyFiftyDollars() {
        long points = rewardService.calculatePoints(50.00);
        assertEquals(0, points);
    }

    @Test
    @DisplayName("Purchase between $50 and $100 ($75) should earn 25 points")
    void testCalculatePoints_SeventyFiveDollars() {
        long points = rewardService.calculatePoints(75.00);
        assertEquals(25, points);
    }

    @Test
    @DisplayName("Boundary Test: Exactly $100 should earn 50 points")
    void testCalculatePoints_ExactlyOneHundredDollars() {
        long points = rewardService.calculatePoints(100.00);
        assertEquals(50, points);
    }

    @Test
    @DisplayName("Boundary Test: $120 purchase should earn 90 points")
    void testCalculatePoints_OneHundredTwentyDollars() {
        long points = rewardService.calculatePoints(120.00);
        assertEquals(90, points);
    }

    // --- Multi-Month Aggregation Test ---

    @Test
    @DisplayName("Calculate total and monthly breakdown for customer C101")
    void testGetRewardsByCustomerId_Success() {
        LocalDate today = LocalDate.now();
        
        // Mock transactions within the 3-month window
        List<Transaction> mockTransactions = List.of(
            new Transaction(1L, "C101", 120.0, today),                      // 90 points
            new Transaction(2L, "C101", 100.0, today.minusDays(10)),         // 50 points
            new Transaction(3L, "C101", 50.0,  today.minusMonths(1)),        // 0 points
            new Transaction(4L, "C101", 75.0,  today.minusMonths(1)),        // 25 points
            new Transaction(5L, "C101", 200.0, today.minusMonths(2))         // 250 points
        );

        when(transactionRepository.findByCustomerId("C101")).thenReturn(mockTransactions);

        CustomerRewardResponse response = rewardService.getRewardsByCustomerId("C101");

        assertNotNull(response);
        assertEquals("C101", response.customerId());
        assertEquals(415, response.totalRewardPoints());
        assertTrue(response.monthlyRewards().size() > 0);
    }
}