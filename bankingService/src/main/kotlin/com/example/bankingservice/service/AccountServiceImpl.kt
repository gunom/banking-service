package com.example.bankingservice.service

import com.example.bankingservice.domain.account.Account
import com.example.bankingservice.domain.account.AccountRepository
import com.example.bankingservice.domain.user.User
import com.example.bankingservice.service.dto.AccountDto
import com.example.bankingservice.utils.generateAccountNumber
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.util.concurrent.TimeUnit
import javax.transaction.Transactional

@Service
class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val redissonClient: RedissonClient,
//    private val transactionManager: PlatformTransactionManager,
) : AccountService {
    override fun makeAccount(user: User, asset: Long): AccountDto {
        val accountList = accountRepository.findAll().map { it.accountNumber }.toSet()
        var newAccountNumber: String
        do {
            newAccountNumber = generateAccountNumber()
        } while (accountList.contains(newAccountNumber))

        val entity = accountRepository.save(
            Account(
                user = user,
                accountNumber = newAccountNumber,
                asset = asset
            )
        )
        return AccountDto(
            entity.accountNumber,
            entity.asset,
            entity.createdAt
        )

    }

    override fun getAccount(userId: Long): AccountDto? {
        val account = accountRepository.findByUserId(userId)
        return account?.let {
            AccountDto(
                accountNumber = it.accountNumber,
                asset = it.asset,
                createdAt = it.createdAt
            )
        }
    }

    override fun findByAccountNumber(accountNumber: String): Account {
        return accountRepository.findByAccountNumber(accountNumber) ?: throw Exception()
    }

    override fun findById(userAccountId: Int): Account {
        return accountRepository.findById(userAccountId).orElseGet { throw Exception() }
    }

    @Transactional
    override fun transferMoney(userAccountId: Int, accountNumber: String, amount: Long) {
        val fromAccount = accountRepository.findById(userAccountId).orElseGet { throw Exception() }
        val toAccount = accountRepository.findByAccountNumber(accountNumber) ?: throw Exception()

//        val status = transactionManager.getTransaction(DefaultTransactionDefinition())
                if (fromAccount.asset < amount) {
                    throw IllegalArgumentException("Insufficient balance")
                }

                fromAccount.asset -= amount
                toAccount.asset += amount

                accountRepository.save(fromAccount)
                accountRepository.save(toAccount)

//                transactionManager.commit(status)

    }
}