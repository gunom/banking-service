package com.example.bankingservice.controller

import com.example.bankingservice.controller.dto.ApiResponse
import com.example.bankingservice.controller.dto.RemitRequestDto
import com.example.bankingservice.facade.AccountFacade
import com.example.bankingservice.service.account.AccountService
import com.example.bankingservice.service.user.UserContextService
import com.example.bankingservice.service.dto.AccountDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account")
class AccountController(
    private val accountService: AccountService,
    private val accountFacade: AccountFacade,
    private val userContextService: UserContextService,
) {
    @PostMapping
    fun makeAccount(@RequestBody asset: Long): ResponseEntity<ApiResponse<AccountDto>>{
        val userId = userContextService.getUserId()
        val account = accountFacade.makeAccount(userId, asset)
        return ResponseEntity.ok().body(ApiResponse.success(account, "Make Account Success"))
    }

    @GetMapping
    fun viewAccount(): ResponseEntity<ApiResponse<AccountDto>> {
        val userId = userContextService.getUserId()
        val account = accountService.getAccount(userId)
        return ResponseEntity.ok().body(ApiResponse.success(account, "View Account Success"))
    }

    @PostMapping("/remittance")
    fun remitMoney(@RequestBody remitRequestDto: RemitRequestDto): ResponseEntity<ApiResponse<Nothing>> {
        val userId = userContextService.getUserId()
        accountFacade.transferMoney(userId, remitRequestDto.senderAccountId, remitRequestDto.accountNumber, remitRequestDto.amount)
        return ResponseEntity.ok().body(ApiResponse.success(null, "Remit Success"))
    }
}