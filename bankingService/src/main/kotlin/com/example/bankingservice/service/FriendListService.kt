package com.example.bankingservice.service

import com.example.bankingservice.domain.user.User

interface FriendListService {
    fun addFriend(user: User, friend: User)
}