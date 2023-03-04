package com.example.bankingservice.service.auth

import org.springframework.security.core.Authentication

interface AuthService {
    fun authenticate(username: String, password: String): Authentication
}