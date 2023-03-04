package com.example.bankingservice.domain.accountTransactionHistory;

import org.springframework.data.jpa.repository.JpaRepository

interface AccountTransactionHistoryRepository : JpaRepository<AccountTransactionHistory, Int> {
}