package com.example.bankingservice.utils

fun generateAccountNumber(): String {
    return (1..12).map { (0..9).random() }.joinToString("")
}