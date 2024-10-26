package com.doyouclub.backend.domain.club.controller

import com.doyouclub.backend.domain.club.dto.request.DeleteMemberRequest
import com.doyouclub.backend.domain.club.dto.request.RegisterMemberRequest
import com.doyouclub.backend.domain.club.dto.response.ClubResponse
import com.doyouclub.backend.domain.club.model.enum.Activity
import com.doyouclub.backend.domain.club.model.enum.Tag
import com.doyouclub.backend.domain.club.service.ClubService
import com.doyouclub.backend.global.jwt.security.JwtAuthentication
import kotlinx.coroutines.flow.Flow
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/club")
@RestController
class ClubController(
    private val clubService: ClubService
) {
    @GetMapping("/userId/{userId}")
    suspend fun getClubsByUserId(
        @PathVariable
        userId: String
    ): Flow<ClubResponse> =
        clubService.getClubsByUserId(userId)

    @GetMapping("/search")
    suspend fun searchClubPage(
        @RequestParam
        name: String,
        @RequestParam
        tag: Tag?,
        @RequestParam
        activity: Activity?,
        @RequestParam
        lastId: String?,
        @RequestParam
        size: Int
    ): Flow<ClubResponse> =
        clubService.searchClubPage(name, tag, activity, lastId, size)

    @PatchMapping("/manage/register/{id}")
    suspend fun registerMember(
        @PathVariable("id")
        id: String,
        @RequestBody
        request: RegisterMemberRequest,
        @AuthenticationPrincipal
        authentication: JwtAuthentication
    ) {
        clubService.registerMember(id, request, authentication.id)
    }

    @PatchMapping("/manage/delete/{id}")
    suspend fun deleteMember(
        @PathVariable("id")
        id: String,
        @RequestBody
        request: DeleteMemberRequest,
        @AuthenticationPrincipal
        authentication: JwtAuthentication
    ) {
        clubService.deleteMember(id, request, authentication.id)
    }
}
