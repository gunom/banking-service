package com.example.bankingservice.facade

import com.example.bankingservice.service.account.AccountService
import com.example.bankingservice.service.account.AccountTransactionHistoryService
import com.example.bankingservice.service.user.UserService
import com.example.bankingservice.service.dto.AccountDto
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class AccountFacade(
    private val accountService: AccountService,
    private val userService: UserService,
    private val accountTransactionHistoryService: AccountTransactionHistoryService,
    private val redissonClient: RedissonClient,
) {
    fun makeAccount(userId: Long, amount: Long): AccountDto {
        val user = userService.findById(userId)
        return accountService.makeAccount(user, amount)
    }

    fun transferMoney(userId: Long, userAccountId: Int, accountNumber: String, amount: Long) {
        val sender = userService.findById(userId)
        val receiver = accountService.findByAccountNumber(accountNumber).user

        val lockKey = "transfer_lock_$userId"
        val lockKey2 = "transfer_lock_${receiver.id}"
        val lock = redissonClient.getLock(lockKey)
        val lock2 = redissonClient.getLock(lockKey2)

        val isLocked = lock.tryLock(10, 1, TimeUnit.SECONDS)

        try {
            if (!isLocked) {
                throw RuntimeException("failed to get RLock")
            }
            val history = accountTransactionHistoryService.addHistory(sender, receiver, amount)
            accountService.transferMoney(userAccountId, accountNumber, amount)
            accountTransactionHistoryService.doneHistory(history.id)
        } catch (e: InterruptedException) {
            throw Exception("Thread Interrupted")
        } catch (e: RuntimeException) {
            throw Exception(e.message.toString())
        } finally {
            if (lock.isLocked && lock.isHeldByCurrentThread) {
                lock.unlock()
                lock2.unlock()
            }
        }
    }
}