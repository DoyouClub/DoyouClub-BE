package com.doyouclub.backend.domain.club.service

import com.doyouclub.backend.domain.club.dto.response.ClubResponse
import com.doyouclub.backend.domain.club.model.enum.Activity
import com.doyouclub.backend.domain.club.model.enum.Tag
import com.doyouclub.backend.domain.club.repository.ClubRepository
import com.doyouclub.backend.domain.club.repository.CustomClubRepository
import com.doyouclub.backend.domain.user.exception.UserNotFoundException
import com.doyouclub.backend.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class ClubService(
    private val clubRepository: ClubRepository,
    private val customClubRepository: CustomClubRepository,
    private val userRepository: UserRepository
) {
    suspend fun getClubsByUserId(userId: String): Flow<ClubResponse> {
        val user = userRepository.findById(userId) ?: throw UserNotFoundException()

        return clubRepository.findByIdIn(user.clubIds.toList())
            .map { ClubResponse(it) }
    }

    fun searchClubPage(
        name: String,
        tag: Tag?,
        activity: Activity?,
        lastId: String?,
        size: Int
    ): Flow<ClubResponse> =
        customClubRepository.searchPage(name, tag, activity, lastId, size)
            .map { ClubResponse(it) }
}
