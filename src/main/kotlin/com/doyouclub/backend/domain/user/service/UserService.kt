package com.doyouclub.backend.domain.user.service

import com.doyouclub.backend.domain.user.dto.response.UserResponse
import com.doyouclub.backend.domain.user.exception.UserNotFoundException
import com.doyouclub.backend.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    suspend fun getUserById(id: String): UserResponse =
        userRepository.findById(id)
            ?.let { UserResponse(it) }
            ?: throw UserNotFoundException()
}
