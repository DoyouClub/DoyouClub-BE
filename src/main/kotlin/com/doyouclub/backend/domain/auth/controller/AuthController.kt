package com.doyouclub.backend.domain.auth.controller

import com.doyouclub.backend.domain.auth.dto.request.RefreshRequest
import com.doyouclub.backend.domain.auth.dto.request.SignInRequest
import com.doyouclub.backend.domain.auth.dto.response.RefreshResponse
import com.doyouclub.backend.domain.auth.dto.response.SignInResponse
import com.doyouclub.backend.domain.auth.service.AuthService
import org.springframework.web.bind.annotation.*

@RequestMapping("/auth")
@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/sign-in")
    suspend fun signIn(
        @RequestBody
        request: SignInRequest
    ): SignInResponse =
        authService.signIn(request)

    @PostMapping("/refresh")
    suspend fun refresh(
        @RequestBody
        request: RefreshRequest
    ): RefreshResponse =
        authService.refresh(request)

    @GetMapping("/logout/{id}")
    suspend fun logout(
        @PathVariable("id")
        id: String
    ) {
        authService.logout(id)
    }
}
