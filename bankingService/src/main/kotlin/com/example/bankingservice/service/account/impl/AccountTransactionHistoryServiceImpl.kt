package com.example.bankingservice.service

import com.example.bankingservice.domain.accountTransactionHistory.AccountTransactionHistory
import com.example.bankingservice.domain.accountTransactionHistory.AccountTransactionHistoryRepository
import com.example.bankingservice.domain.user.User
import com.example.bankingservice.global.enum.TransactionStatus
import com.example.bankingservice.global.enum.TransactionStatus.PENDING
import org.springframework.stereotype.Service

@Service
class AccountTransactionHistoryServiceImpl(
    private val accountTransactionHistoryRepository: AccountTransactionHistoryRepository,
) : AccountTransactionHistoryService {
    override fun addHistory(sender: User, receiver: User, amount: Long): AccountTransactionHistory {
        return accountTransactionHistoryRepository.save(
            AccountTransactionHistory(
                sender = sender,
                recipient = receiver,
                amount = amount,
                status = PENDING.code
            )
        )
    }

    override fun doneHistory(id: Int) {
        val history = accountTransactionHistoryRepository.findById(id).get()
        history.status = TransactionStatus.DONE.code
        accountTransactionHistoryRepository.save(history)
    }
}