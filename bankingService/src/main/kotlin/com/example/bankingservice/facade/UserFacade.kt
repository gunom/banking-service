package com.example.bankingservice.facade

import com.example.bankingservice.service.FriendListService
import com.example.bankingservice.service.UserService
import org.springframework.stereotype.Service

@Service
class UserFacade(
    private val userService: UserService,
    private val friendListService: FriendListService,
) {
    fun addFriend(userId: Long, friendId: Long){
        val user = userService.findById(userId)
        val friend = userService.findById(friendId)

        friendListService.addFriend(user, friend)
    }
}