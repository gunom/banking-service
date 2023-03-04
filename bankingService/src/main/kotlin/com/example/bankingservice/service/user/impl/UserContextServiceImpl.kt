package com.example.bankingservice.service.user.impl

import com.example.bankingservice.domain.user.SecurityUser
import com.example.bankingservice.service.user.UserContextService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserContextServiceImpl: UserContextService {
    override fun getUserId(): Long {
        val user = SecurityContextHolder.getContext().authentication.details as SecurityUser
        return user.getUserId()
    }
}