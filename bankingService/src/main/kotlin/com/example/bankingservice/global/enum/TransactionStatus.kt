package com.example.bankingservice.global.enum

enum class TransactionStatus(val code: String) {
    PENDING(code = "P"),
    DONE(code = "D"),
    ERROR(code = "E"),
}