package com.example.bankingservice.domain.account;

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Int> {
    fun findByUserId(userId: Long): Account?
    fun findByAccountNumber(accountNumber: String): Account?
}