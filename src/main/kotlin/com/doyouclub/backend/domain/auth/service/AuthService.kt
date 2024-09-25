package com.doyouclub.backend.domain.auth.service

import com.doyouclub.backend.domain.auth.dto.request.SignInRequest
import com.doyouclub.backend.domain.auth.dto.response.SignInResponse
import com.doyouclub.backend.domain.auth.model.SignUpToken
import com.doyouclub.backend.domain.auth.repository.SignUpTokenRepository
import com.doyouclub.backend.domain.user.repository.UserRepository
import com.doyouclub.backend.global.jwt.core.JwtProvider
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val oAuth2Service: OAuth2Service,
    private val userRepository: UserRepository,
    private val signUpTokenRepository: SignUpTokenRepository,
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
}
