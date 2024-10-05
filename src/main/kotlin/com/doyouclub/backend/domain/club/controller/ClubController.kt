package com.doyouclub.backend.domain.club.controller

import com.doyouclub.backend.domain.club.dto.response.ClubResponse
import com.doyouclub.backend.domain.club.service.ClubService
import com.doyouclub.backend.global.jwt.security.JwtAuthentication
import kotlinx.coroutines.flow.Flow
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/club")
@RestController
class ClubController(
    private val clubService: ClubService
) {
    @GetMapping("/my")
    suspend fun getMyClubs(
        @AuthenticationPrincipal
        authentication: JwtAuthentication
    ): Flow<ClubResponse> =
        clubService.getMyClubs(authentication.id)
}
