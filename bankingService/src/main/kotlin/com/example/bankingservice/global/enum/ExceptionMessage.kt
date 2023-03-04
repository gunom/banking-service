package com.example.bankingservice.global.enum

enum class ExceptionMessage(val message: String) {
    USER_DUPLICATE_EXCEPTION("User is already exist"),
    INTERNAL_SERVER_ERROR("Internal server error")
}