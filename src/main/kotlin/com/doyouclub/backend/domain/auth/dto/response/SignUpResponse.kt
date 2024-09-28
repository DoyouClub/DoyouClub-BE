package com.doyouclub.backend.domain.auth.dto.response

data class SignUpResponse(
    val accessToken: String,
    val refreshToken: String
)
