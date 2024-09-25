package com.doyouclub.backend.domain.auth.dto.response

data class SignInResponse(
    val isNew: Boolean,
    val email: String,
    val signUpToken: String?,
    val accessToken: String?,
    val refreshToken: String?
)
