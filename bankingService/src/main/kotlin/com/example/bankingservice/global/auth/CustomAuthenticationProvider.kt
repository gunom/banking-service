package com.example.bankingservice.global.auth

import com.example.bankingservice.service.auth.CustomUserDetailsService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val passwordEncoder: BCryptPasswordEncoder,
    private val customUserDetailsService: CustomUserDetailsService,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val email: String = authentication.name
        val password: String = authentication.credentials.toString()
        val user = customUserDetailsService.loadUserByUsername(email)
        val authenticated = checkPassword(password, user)
        authenticated.details = user
        return authenticated
    }

    private fun checkPassword(
        password: String,
        user: UserDetails
    ): UsernamePasswordAuthenticationToken {
        if (passwordEncoder.matches(password, user.password)) {
            return UsernamePasswordAuthenticationToken(
                user.username,
                user.password,
                user.authorities,
            )
        } else throw BadCredentialsException("Bad credentials!")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}