package com.doyouclub.backend.config

import com.doyouclub.backend.fixture.jwtProvider
import com.doyouclub.backend.global.config.SecurityConfiguration
import com.doyouclub.backend.global.jwt.security.JwtFilter
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@TestConfiguration
class SecurityTestConfiguration {
    @Bean
    fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        SecurityConfiguration()
            .filterChain(http, jwtFilter())

    @Bean
    fun jwtFilter(): JwtFilter = JwtFilter(jwtProvider)
}
