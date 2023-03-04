package com.example.bankingservice

import com.example.bankingservice.domain.user.User
import com.example.bankingservice.domain.user.repository.UserRepository
import com.example.bankingservice.service.user.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(SpringExtension::class)
@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun testSaveUser() {
        // Given
        val email = "test@example.com"
        val password = "password"

        // When
        userRepository.save(User(email = email, password = password))

        // Then
        val user = userService.findById(1L)
        assertEquals(email, user.email)
    }

    @Test
    fun testSearchUser() {
        // Given
        val user1 = User(email = "test1@example.com", password = "password1")
        val user2 = User(email = "test2@example.com", password = "password2")
        val user3 = User(email = "another@example.com", password = "password3")
        user1.friends = mutableSetOf(user2)
        user2.friends = mutableSetOf(user1)
        val users = listOf(user1, user2, user3)
        users.forEach { userService.saveUser(it.email, it.password) }

        // When
        val result = userService.searchUser(1L, "test")

        // Then
        assertEquals(2, result.size)
        assertTrue(result.any { it.email == user1.email })
        assertTrue(result.any { it.email == user2.email })
        assertFalse(result.any { it.email == user3.email })
        assertEquals(user2.id, result.find { it.email == user1.email }!!.id)
        assertEquals(user1.id, result.find { it.email == user2.email }!!.id)
        assertTrue(result.all { it.isFriend })
    }
}