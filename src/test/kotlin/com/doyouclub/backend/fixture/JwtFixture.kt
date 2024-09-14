package com.doyouclub.backend.fixture

import com.doyouclub.backend.global.config.JwtConfiguration
import com.doyouclub.backend.global.jwt.security.JwtAuthentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

val SECRET_KEY = (1..100).map { ('a'..'z').random() }.joinToString("")
const val ACCESS_TOKEN_EXPIRE = 5L
const val REFRESH_TOKEN_EXPIRE = 10L
val ROLES = setOf(SimpleGrantedAuthority("USER"))
val jwtProvider = JwtConfiguration()
    .jwtProvider(SECRET_KEY, ACCESS_TOKEN_EXPIRE, REFRESH_TOKEN_EXPIRE)

fun createJwtAuthentication(
    id: String = ID,
    roles: Set<GrantedAuthority> = ROLES
): JwtAuthentication =
    JwtAuthentication(
        id = id,
        roles = roles
    )
