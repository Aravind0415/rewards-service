# Retailer Rewards Service

A Spring Boot RESTful Web Service that calculates reward points for retail customers based on recorded purchase transactions over a 3-month period.

---

## Reward Point Rules

- **2 points** for every dollar spent over **$100** in a single transaction.
- **1 point** for every dollar spent between **$50 and $100** in a single transaction.
- **0 points** for purchases of **$50 or less**.

### Calculation Examples:
- **$120 purchase:** 2 × ($120 - $100) + 1 × $50 = **90 points**
- **$100 purchase:** 1 × ($100 - $50) = **50 points**
- **$50 purchase:** **0 points**

---

## Tech Stack & Features

- **Java:** 21 / 17
- **Framework:** Spring Boot 3.x
- **Build Tool:** Maven (Wrapper included)
- **Database:** In-Memory H2 / Mock Dataset

---

## Getting Started

### Prerequisites
- JDK 17 or JDK 21 installed (`java -version`)
- Git

### Build the Application
```bash
./mvnw clean install

The application will start on `http://localhost:8081`.

---

## API Endpoints & Expected Outputs

### 1. Get Rewards Summary for All Customers
- **HTTP Method:** `GET`
- **URL:** `http://localhost:8081/api/v1/rewards`
- **Description:** Returns total reward points and monthly breakdown for every customer in the dataset.
- **Response (`200 OK`):**
```json
[
  {
    "customerId": "C101",
    "monthlyRewards": [
      {
        "month": 7,
        "year": 2026,
        "points": 250
      },
      {
        "month": 8,
        "year": 2026,
        "points": 25
      },
      {
        "month": 9,
        "year": 2026,
        "points": 140
      }
    ],
    "totalRewardPoints": 415
  },
  {
    "customerId": "C102",
    "monthlyRewards": [
      {
        "month": 8,
        "year": 2026,
        "points": 150
      },
      {
        "month": 9,
        "year": 2026,
        "points": 30
      }
    ],
    "totalRewardPoints": 180
  }
]

### 2. Get Rewards Summary for a Specific Customer
- **HTTP Method:** `GET`
- **URL:** `http://localhost:8081/api/v1/rewards/C101`
- **Description:** Returns total reward points and monthly breakdown for a single customer ID.
- **Response (`200 OK`):**
```json
{
  "customerId": "C101",
  "monthlyRewards": [
    {
      "month": 7,
      "year": 2026,
      "points": 250
    },
    {
      "month": 8,
      "year": 2026,
      "points": 25
    },
    {
      "month": 9,
      "year": 2026,
      "points": 140
    }
  ],
  "totalRewardPoints": 415
}

### 3. Get All Recorded Transactions
- **HTTP Method:** `GET`
- **URL:** `http://localhost:8081/api/v1/transactions`
- **Description:** Retrieves all raw purchase transactions used for point calculations.
- **Response (`200 OK`):**
```json
[
  {
    "id": 1,
    "customerId": "C101",
    "amount": 120,
    "transactionDate": "2026-09-27"
  },
  {
    "id": 2,
    "customerId": "C101",
    "amount": 100,
    "transactionDate": "2026-09-17"
  },
  {
    "id": 3,
    "customerId": "C101",
    "amount": 50,
    "transactionDate": "2026-08-23"
  },
  {
    "id": 4,
    "customerId": "C101",
    "amount": 75,
    "transactionDate": "2026-08-18"
  },
  {
    "id": 5,
    "customerId": "C101",
    "amount": 200,
    "transactionDate": "2026-07-19"
  },
  {
    "id": 6,
    "customerId": "C102",
    "amount": 80,
    "transactionDate": "2026-09-22"
  },
  {
    "id": 7,
    "customerId": "C102",
    "amount": 150,
    "transactionDate": "2026-08-28"
  }
]