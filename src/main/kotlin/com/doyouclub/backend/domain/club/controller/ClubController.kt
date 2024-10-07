package com.doyouclub.backend.domain.club.controller

import com.doyouclub.backend.domain.club.dto.response.ClubResponse
import com.doyouclub.backend.domain.club.service.ClubService
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
