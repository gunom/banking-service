package com.example.bankingservice.controller.dto

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
) {
    companion object {
        fun <T> success(data: T?, message: String? = null): ApiResponse<T> {
            return ApiResponse(true, message, data)
        }

        fun error(message: String): ApiResponse<Nothing> {
            return ApiResponse(false, message, null)
        }
    }
}