package com.example.bankingservice.service

import com.example.bankingservice.domain.user.SecurityUser
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service


@Service
class AuthServiceImpl(
    private val authenticationManager: AuthenticationManager,
) : AuthService{

    @Throws(AuthenticationException::class)
    override fun authenticate(username: String, password: String): Authentication {
        val authentication: Authentication = UsernamePasswordAuthenticationToken(username, password)
        return authenticationManager.authenticate(authentication)
    }

    override fun getUserId(): Long {
        val user = SecurityContextHolder.getContext().authentication.details as SecurityUser
        return user.getUserId()
    }
}