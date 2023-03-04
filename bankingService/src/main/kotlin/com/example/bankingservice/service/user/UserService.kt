package com.example.bankingservice.service

import com.example.bankingservice.domain.user.User
import com.example.bankingservice.service.dto.UserDto

interface UserService {
    fun saveUser(email: String, password: String)
    fun searchUser(userId: Long, email: String): List<UserDto>
    fun findById(id: Long): User
}