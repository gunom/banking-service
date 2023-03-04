package com.example.bankingservice.service

import com.example.bankingservice.domain.accountTransactionHistory.AccountTransactionHistory
import com.example.bankingservice.domain.user.User

interface AccountTransactionHistoryService {
    fun addHistory(sender: User, receiver: User, amount: Long): AccountTransactionHistory
    fun doneHistory(id: Int)
}