package com.doyouclub.backend.domain.auth.service

import com.doyouclub.backend.domain.auth.dto.request.RefreshRequest
import com.doyouclub.backend.domain.auth.dto.request.SignInRequest
import com.doyouclub.backend.domain.auth.dto.response.RefreshResponse
import com.doyouclub.backend.domain.auth.dto.response.SignInResponse
import com.doyouclub.backend.domain.auth.exception.InvalidAccessException
import com.doyouclub.backend.domain.auth.exception.InvalidRefreshTokenException
import com.doyouclub.backend.domain.auth.exception.RefreshTokenNotFoundException
import com.doyouclub.backend.domain.auth.model.RefreshToken
import com.doyouclub.backend.domain.auth.model.SignUpToken
import com.doyouclub.backend.domain.auth.repository.RefreshTokenRepository
import com.doyouclub.backend.domain.auth.repository.SignUpTokenRepository
import com.doyouclub.backend.domain.user.exception.UserNotFoundException
import com.doyouclub.backend.domain.user.repository.UserRepository
import com.doyouclub.backend.global.jwt.core.JwtProvider
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val oAuth2Service: OAuth2Service,
    private val userRepository: UserRepository,
    private val signUpTokenRepository: SignUpTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProvider: JwtProvider
) {
    suspend fun signIn(request: SignInRequest): SignInResponse {
        val email = oAuth2Service.getEmailByTokenAndProvider(request.token, request.provider)
        val user = userRepository.findByEmail(email)
        val (accessToken, refreshToken) = user?.let(jwtProvider::createTokens) ?: (null to null)
        val signUpToken =
            if (user == null)
                signUpTokenRepository.save(
                    SignUpToken(
                        email = email,
                        content = request.token,
                        provider = request.provider
                    )
                )
            else null

        return SignInResponse(
            isNew = (user == null),
            email = email,
            signUpToken = signUpToken?.content,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    suspend fun refresh(request: RefreshRequest): RefreshResponse {
        val authentication = runCatching { jwtProvider.getAuthentication(request.refreshToken) }
            .getOrElse { throw InvalidRefreshTokenException() }
        val (id, content) = refreshTokenRepository.findByKey(authentication.id) ?: throw RefreshTokenNotFoundException()

        if (content != request.refreshToken) {
            refreshTokenRepository.deleteByKey(id)

            throw InvalidAccessException()
        }

        val user = userRepository.findById(authentication.id) ?: throw UserNotFoundException()
        val (accessToken, refreshToken) = jwtProvider.createTokens(user)

        refreshTokenRepository.save(
            RefreshToken(
                id = id,
                content = refreshToken
            )
        )

        return RefreshResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    suspend fun logout(id: String) {
        refreshTokenRepository.deleteByKey(id)
    }
}
