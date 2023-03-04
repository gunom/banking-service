package com.example.bankingservice.service

import org.springframework.security.core.Authentication

interface AuthService {
    fun authenticate(username: String, password: String): Authentication
    fun getUserId(): Long
}