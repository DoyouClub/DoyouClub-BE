package com.doyouclub.backend.domain.user.service

import com.doyouclub.backend.domain.club.exception.ClubNotFoundException
import com.doyouclub.backend.domain.club.repository.ClubRepository
import com.doyouclub.backend.domain.user.dto.request.UpdateUserByIdRequest
import com.doyouclub.backend.domain.user.dto.response.UserResponse
import com.doyouclub.backend.domain.user.exception.UserNotFoundException
import com.doyouclub.backend.domain.user.repository.UserRepository
import com.doyouclub.backend.global.exception.common.PermissionDeniedException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val clubRepository: ClubRepository
) {
    suspend fun getUserById(id: String): UserResponse =
        userRepository.findById(id)
            ?.let { UserResponse(it) }
            ?: throw UserNotFoundException()

    suspend fun getUsersByClubId(clubId: String, presidentId: String): Flow<UserResponse> {
        val club = clubRepository.findById(clubId) ?: throw ClubNotFoundException()

        if (club.president.id != presidentId) throw PermissionDeniedException()

        return userRepository.findAllByClubIdsContains(clubId)
            .map { UserResponse(it) }
    }

    suspend fun updateUserById(id: String, request: UpdateUserByIdRequest): UserResponse {
        val user = userRepository.findById(id) ?: throw UserNotFoundException()

        return UserResponse(userRepository.save(request.toDomain(user)))
    }
}
