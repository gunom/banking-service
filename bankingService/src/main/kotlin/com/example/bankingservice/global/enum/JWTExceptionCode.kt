package com.example.bankingservice.global.enum

enum class JWTExceptionCode(val message: String) {
    EXPIRED_TOKEN("this token expired!"),
    MALFORMED_TOKEN("this token malformed!"),
    INVALID_SIGNATURE_TOKEN("this is invalid signature!"),
    UNKNOWN_ERROR("unknown error"),
    MISSING_TOKEN("token is missing")
}