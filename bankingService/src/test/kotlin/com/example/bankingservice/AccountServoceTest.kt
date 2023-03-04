package com.example.bankingservice

import com.example.bankingservice.domain.account.Account
import com.example.bankingservice.domain.account.AccountRepository
import com.example.bankingservice.domain.user.User
import com.example.bankingservice.domain.user.repository.UserRepository
import com.example.bankingservice.facade.AccountFacade
import com.example.bankingservice.service.account.impl.AccountServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.stream.IntStream
import kotlin.test.assertEquals


@ExtendWith(MockitoExtension::class)
@SpringBootTest
class AccountServiceTest {

    @Autowired
    private lateinit var accountService: AccountServiceImpl

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var accountFacade: AccountFacade

    val user1 = User(id = 1, email = "test1@test.com", password = "12341234")
    val user2 = User(id = 2, email = "test2@test.com", password = "12341234")

    @BeforeEach
    fun setup() {
        userRepository.save(user1)
        userRepository.save(user2)
    }
    @Test
    fun testTransferMoneyConcurrently() {
        val user1 = userRepository.findById(1).get()
        val user2 = userRepository.findById(2).get()
        val account1 = Account(user = user1, accountNumber = "1111-1111-1111", asset = 10000)
        val account2 = Account(user = user2, accountNumber = "1111-1111-1112", asset = 0)
        accountRepository.save(account1)
        accountRepository.save(account2)

        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(threadCount);
        val countDownLatch = CountDownLatch(threadCount);
        IntStream.range(0, threadCount).forEach { e: Int ->
            executorService.submit {
                try {
                    accountFacade.transferMoney(1, 1,"1111-1111-1112", 100)
                } catch (ex: InterruptedException) {
                    throw RuntimeException(ex)
                } finally {
                    countDownLatch.countDown()
                }
            }
        }
        countDownLatch.await()

        val finalFromAccount = accountRepository.findByAccountNumber(account1.accountNumber)
        val finalToAccount = accountRepository.findByAccountNumber(account2.accountNumber)

        assertEquals(0L, finalFromAccount!!.asset)
        assertEquals(10000L, finalToAccount!!.asset)
    }
}
