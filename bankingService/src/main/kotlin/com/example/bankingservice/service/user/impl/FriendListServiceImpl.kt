package com.example.bankingservice.service.user.impl

import com.example.bankingservice.domain.friendList.FriendList
import com.example.bankingservice.domain.friendList.FriendListRepository
import com.example.bankingservice.domain.user.User
import com.example.bankingservice.service.user.FriendListService
import org.springframework.stereotype.Service

@Service
class FriendListServiceImpl(
    private val friendListRepository: FriendListRepository,
) : FriendListService {

    override fun addFriend(user: User, friend: User) {
        friendListRepository.save(
            FriendList(
                user = user,
                friend = user,
            )
        )
    }
}