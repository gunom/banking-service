package com.example.bankingservice.controller.dto

data class RemitRequestDto(
    val senderAccountId: Int,
    val accountNumber: String,
    val amount: Long,
)
