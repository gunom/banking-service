package com.example.bankingservice.service

import com.example.bankingservice.domain.user.User
import com.example.bankingservice.domain.user.repository.UserRepository
import com.example.bankingservice.global.enum.ExceptionMessage
import com.example.bankingservice.global.exception.UserDuplicateException
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
        if(exUser != null) throw UserDuplicateException(ExceptionMessage.USER_DUPLICATE_EXCEPTION.message)
        userRepository.save(User(email = email, password = passwordEncoder.encode(password)))
    }
}