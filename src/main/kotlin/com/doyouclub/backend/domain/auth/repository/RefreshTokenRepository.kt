package com.doyouclub.backend.domain.auth.repository

import com.doyouclub.backend.domain.auth.model.RefreshToken
import com.doyouclub.backend.global.redis.repository.CoroutineRedisRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : CoroutineRedisRepository<RefreshToken, String>
