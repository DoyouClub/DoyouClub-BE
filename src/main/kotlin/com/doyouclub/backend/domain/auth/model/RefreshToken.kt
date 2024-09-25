package com.doyouclub.backend.domain.auth.model

import com.doyouclub.backend.global.redis.annotation.Key
import org.springframework.data.redis.core.RedisHash

@RedisHash
data class RefreshToken(
    @Key
    val id: String,
    val content: String
)
