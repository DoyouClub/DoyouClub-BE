package com.doyouclub.backend.domain.club.service

import com.doyouclub.backend.domain.auth.exception.UserAlreadyJoinedClubException
import com.doyouclub.backend.domain.club.dto.request.DeleteMemberRequest
import com.doyouclub.backend.domain.club.dto.request.RegisterMemberRequest
import com.doyouclub.backend.domain.club.dto.response.ClubResponse
import com.doyouclub.backend.domain.club.exception.ClubNotFoundException
import com.doyouclub.backend.domain.club.model.enum.Activity
import com.doyouclub.backend.domain.club.model.enum.Tag
import com.doyouclub.backend.domain.club.repository.ClubRepository
import com.doyouclub.backend.domain.club.repository.CustomClubRepository
import com.doyouclub.backend.domain.user.exception.UserNotFoundException
import com.doyouclub.backend.domain.user.repository.UserRepository
import com.doyouclub.backend.global.exception.common.PermissionDeniedException
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

    suspend fun registerMember(clubId: String, request: RegisterMemberRequest, presidentId: String) {
        val club = clubRepository.findById(clubId) ?: throw ClubNotFoundException()

        if (club.president.id != presidentId) throw PermissionDeniedException()

        val user = userRepository.findByEmail(request.email)
            ?.run {
                if (clubId in clubIds) throw UserAlreadyJoinedClubException()
                copy(clubIds = clubIds.apply { add(clubId) })
            }
            ?: throw UserNotFoundException()

        userRepository.save(user)
    }

    suspend fun deleteMember(clubId: String, request: DeleteMemberRequest, presidentId: String) {
        val club = clubRepository.findById(clubId) ?: throw ClubNotFoundException()

        if (club.president.id != presidentId) throw PermissionDeniedException()

        val user = userRepository.findById(request.userId)
            ?.run { copy(clubIds = clubIds.apply { remove(clubId) }) }
            ?: throw UserNotFoundException()

        userRepository.save(user)
    }
}
