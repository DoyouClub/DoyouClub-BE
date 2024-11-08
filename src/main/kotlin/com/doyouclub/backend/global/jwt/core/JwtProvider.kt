package com.doyouclub.backend.global.jwt.core

import com.doyouclub.backend.domain.user.model.User
import com.doyouclub.backend.global.jwt.security.JwtAuthentication
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import javax.crypto.SecretKey
import kotlin.time.Duration

class JwtProvider(
    private val secretKey: SecretKey,
    private val accessTokenExpire: Duration,
    private val refreshTokenExpire: Duration
) {
    private val parser =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()!!

    fun createTokens(user: User): Pair<String, String> =
        mapOf(
            "id" to user.id,
            "roles" to user.roles.joinToString(",")
        ).let { createAccessToken(it) to createRefreshToken(it) }

    private fun createAccessToken(claims: Map<String, *>): String =
        createToken(claims, accessTokenExpire)

    private fun createRefreshToken(claims: Map<String, *>): String =
        createToken(claims, refreshTokenExpire)

    fun getAuthentication(token: String): JwtAuthentication =
        parser.parseClaimsJws(token)
            .run { JwtAuthentication(body) }

    private fun createToken(claims: Map<String, *>, expire: Duration): String {
        val now = Date()

        return Jwts.builder()
            .setIssuedAt(now)
            .setClaims(claims)
            .setExpiration(Date(now.time + expire.inWholeMilliseconds))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }
}
