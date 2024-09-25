package com.doyouclub.backend.domain.auth.model

import com.doyouclub.backend.domain.user.model.enum.Provider
import com.doyouclub.backend.global.redis.annotation.Key
import org.springframework.data.redis.core.RedisHash

@RedisHash
data class SignUpToken(
    @Key
    val email: String,
    val content: String,
    val provider: Provider
)
