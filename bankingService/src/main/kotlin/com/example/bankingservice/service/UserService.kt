package com.example.bankingservice.service

interface UserService {
    fun saveUser(email: String, password: String)
}