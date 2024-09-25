package com.doyouclub.backend.domain.auth.dto.request

import com.doyouclub.backend.domain.user.model.enum.Provider

data class SignInRequest(
    val token: String,
    val provider: Provider
)
