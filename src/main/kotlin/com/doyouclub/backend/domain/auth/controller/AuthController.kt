package com.doyouclub.backend.domain.auth.controller

import com.doyouclub.backend.domain.auth.dto.request.RefreshRequest
import com.doyouclub.backend.domain.auth.dto.request.SignInRequest
import com.doyouclub.backend.domain.auth.dto.request.SignUpRequest
import com.doyouclub.backend.domain.auth.dto.response.RefreshResponse
import com.doyouclub.backend.domain.auth.dto.response.SignInResponse
import com.doyouclub.backend.domain.auth.dto.response.SignUpResponse
import com.doyouclub.backend.domain.auth.service.AuthService
import org.springframework.web.bind.annotation.*

@RequestMapping("/auth")
@RestController
class AuthController(
    private val authService: AuthService
) {
    @GetMapping("/logout/{id}")
    suspend fun logout(
        @PathVariable("id")
        id: String
    ) {
        authService.logout(id)
    }

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

    @PostMapping("/sign-up")
    suspend fun signUp(
        @RequestBody
        request: SignUpRequest
    ): SignUpResponse =
        authService.signUp(request)
}
