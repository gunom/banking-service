package com.example.bankingservice.service.dto

import java.time.LocalDateTime

data class UserDto(
    val id: Long,
    val email: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isFriend: Boolean,
)
