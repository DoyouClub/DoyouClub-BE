package com.doyouclub.backend.domain.user.controller

import com.doyouclub.backend.domain.user.dto.request.UpdateUserByIdRequest
import com.doyouclub.backend.domain.user.dto.response.UserResponse
import com.doyouclub.backend.domain.user.service.UserService
import com.doyouclub.backend.global.jwt.security.JwtAuthentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/user")
@RestController
class UserController(
    private val userService: UserService
) {
    @GetMapping("/my")
    suspend fun getMyUser(
        @AuthenticationPrincipal
        authentication: JwtAuthentication
    ): UserResponse =
        userService.getUserById(authentication.id)

    @PutMapping("/{id}")
    suspend fun updateUserById(
        @PathVariable
        id: String,
        @RequestBody
        request: UpdateUserByIdRequest
    ): UserResponse =
        userService.updateUserById(id, request)
}
