package com.example.bankingservice.global.handler

import com.example.bankingservice.controller.dto.ApiResponse
import com.example.bankingservice.global.enum.ExceptionMessage
import com.example.bankingservice.global.exception.UserDuplicateException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserControllerAdvice {
    @ExceptionHandler(UserDuplicateException::class)
    fun handlingUserDuplicate(e: UserDuplicateException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.badRequest()
            .body(ApiResponse.error(ExceptionMessage.USER_DUPLICATE_EXCEPTION.message))
    }
}