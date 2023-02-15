package com.example.bankingservice.service

import com.example.bankingservice.domain.user.SecurityUser
import com.example.bankingservice.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class SecurityUserService(
    private val userRepository: UserRepository,
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
        if (user != null) return SecurityUser(user)
        else throw UsernameNotFoundException("user not found!")
    }
}