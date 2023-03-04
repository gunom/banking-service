package com.example.bankingservice.controller

import com.example.bankingservice.controller.dto.ApiResponse
import com.example.bankingservice.controller.dto.SignUpRequestDto
import com.example.bankingservice.facade.UserFacade
import com.example.bankingservice.service.user.UserContextService
import com.example.bankingservice.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService,
    private val userContextService: UserContextService,
    private val userFacade: UserFacade,
) {
    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpRequestDto: SignUpRequestDto): ResponseEntity<ApiResponse<Nothing>> {
        userService.saveUser(signUpRequestDto.email, signUpRequestDto.password)
        return ResponseEntity.ok().body(ApiResponse.success(null, "SignUp Success"))
    }

    @GetMapping("/search")
    fun searchUser(@RequestParam("q") email: String): ResponseEntity<Any> {
        val userId = userContextService.getUserId()
        val result = userService.searchUser(userId, email)
        return ResponseEntity.ok().body(ApiResponse.success(result, "Search Success"))
    }

    @PostMapping("/add/{id}")
    fun addUser(@PathVariable("id") friendId: Long): ResponseEntity<ApiResponse<Nothing>> {
        val userId = userContextService.getUserId()
        userFacade.addFriend(userId, friendId)
        return ResponseEntity.ok().body(ApiResponse.success(null, "Add Friend Success"))
    }
}