package com.example.bankingservice.controller

import com.example.bankingservice.controller.dto.ApiResponse
import com.example.bankingservice.controller.dto.SignUpRequestDto
import com.example.bankingservice.global.enum.ExceptionMessage
import com.example.bankingservice.global.exception.UserDuplicateException
import com.example.bankingservice.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpRequestDto: SignUpRequestDto): ResponseEntity<ApiResponse<Nothing>> {
        return try {
            userService.saveUser(signUpRequestDto.email, signUpRequestDto.password)
            return ResponseEntity.ok().body(ApiResponse.success(null,"SignUp Success"))
        } catch (e: UserDuplicateException){
            ResponseEntity.badRequest().body(ApiResponse.error(ExceptionMessage.USER_DUPLICATE_EXCEPTION.message))
        }
    }
}