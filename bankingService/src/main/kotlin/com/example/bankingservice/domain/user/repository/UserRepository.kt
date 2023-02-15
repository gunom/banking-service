package com.example.bankingservice.domain.user.repository

import com.example.bankingservice.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}