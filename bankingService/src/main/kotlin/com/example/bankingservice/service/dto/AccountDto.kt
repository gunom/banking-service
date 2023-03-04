package com.example.bankingservice.service.dto

import java.time.LocalDateTime

data class AccountDto(
    val accountNumber: String,
    val asset: Long,
    val createdAt: LocalDateTime
)
