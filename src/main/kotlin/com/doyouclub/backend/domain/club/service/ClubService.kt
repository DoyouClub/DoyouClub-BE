package com.doyouclub.backend.domain.club.service

import com.doyouclub.backend.domain.club.dto.response.ClubResponse
import com.doyouclub.backend.domain.club.repository.ClubRepository
import com.doyouclub.backend.domain.user.exception.UserNotFoundException
import com.doyouclub.backend.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class ClubService(
    private val clubRepository: ClubRepository,
    private val userRepository: UserRepository
) {
    suspend fun getMyClubs(id: String): Flow<ClubResponse> {
        val user = userRepository.findById(id) ?: throw UserNotFoundException()

        return clubRepository.findByIdIn(user.clubIds)
            .map { ClubResponse(it) }
    }
}
