package com.doyouclub.backend.global.config

import com.doyouclub.backend.global.redis.annotation.EnableReactiveRedisRepositories
import org.springframework.context.annotation.Configuration

@EnableReactiveRedisRepositories(basePackages = ["com.doyouclub.backend.domain.auth"])
@Configuration
class RedisConfiguration
