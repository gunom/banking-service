package com.example.bankingservice.service.account

import com.example.bankingservice.domain.account.Account
import com.example.bankingservice.domain.user.User
import com.example.bankingservice.service.dto.AccountDto

interface AccountService {
    fun getAccount(userId: Long): AccountDto?
    fun findByAccountNumber(accountNumber: String): Account
    fun findById(userAccountId: Int): Account
    fun transferMoney(userAccountId: Int, accountNumber: String, amount: Long)
    fun makeAccount(user: User, asset: Long): AccountDto
}