package com.doyouclub.backend.domain.auth.dto.request

data class SignUpRequest(
    val email: String,
    val token: String,
    val name: String
)
