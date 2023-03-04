package com.example.bankingservice.service.user.impl

import com.example.bankingservice.domain.user.User
import com.example.bankingservice.domain.user.repository.UserRepository
import com.example.bankingservice.global.enum.ExceptionMessage
import com.example.bankingservice.global.exception.UserDuplicateException
import com.example.bankingservice.service.dto.UserDto
import com.example.bankingservice.service.user.UserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) : UserService {

    @Throws(UserDuplicateException::class)
    override fun saveUser(email: String, password: String) {
        val exUser = userRepository.findByEmail(email)
        if (exUser != null) throw UserDuplicateException(ExceptionMessage.USER_DUPLICATE_EXCEPTION.message)
        userRepository.save(User(email = email, password = passwordEncoder.encode(password)))
    }

    override fun searchUser(userId: Long, email: String): List<UserDto> {
        val userList = userRepository.findByEmailStartsWith(email)
        return userList.map {
            UserDto(
                id = it.id,
                email = it.email,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                isFriend = it.friends.map { friend -> friend.id }.contains(userId)
            )
        }
    }

    override fun findById(id: Long): User {
        return userRepository.findById(id).orElseGet { throw java.util.NoSuchElementException() }
    }
}