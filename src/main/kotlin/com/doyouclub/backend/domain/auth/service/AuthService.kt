package com.doyouclub.backend.domain.auth.service

import com.doyouclub.backend.domain.auth.dto.request.RefreshRequest
import com.doyouclub.backend.domain.auth.dto.request.SignInRequest
import com.doyouclub.backend.domain.auth.dto.request.SignUpRequest
import com.doyouclub.backend.domain.auth.dto.response.RefreshResponse
import com.doyouclub.backend.domain.auth.dto.response.SignInResponse
import com.doyouclub.backend.domain.auth.dto.response.SignUpResponse
import com.doyouclub.backend.domain.auth.exception.*
import com.doyouclub.backend.domain.auth.model.RefreshToken
import com.doyouclub.backend.domain.auth.model.SignUpToken
import com.doyouclub.backend.domain.auth.repository.RefreshTokenRepository
import com.doyouclub.backend.domain.auth.repository.SignUpTokenRepository
import com.doyouclub.backend.domain.user.exception.UserNotFoundException
import com.doyouclub.backend.domain.user.model.User
import com.doyouclub.backend.domain.user.model.enum.Role
import com.doyouclub.backend.domain.user.repository.UserRepository
import com.doyouclub.backend.global.jwt.core.JwtProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
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

    suspend fun signUp(request: SignUpRequest): SignUpResponse = coroutineScope {
        with(request) {
            val getUserJob = launch {
                userRepository.findByEmail(email)
                    ?.run { throw AccountAlreadyExistException() }
            }
            val getSignUpTokenDeferred =
                async { signUpTokenRepository.findByKey(email) ?: throw SignUpTokenNotFoundException() }

            getUserJob.join()
            val signUpToken = getSignUpTokenDeferred.await()

            if (signUpToken.content != token) throw InvalidSignUpTokenException()

            val user = userRepository.save(
                User(
                    email = email,
                    name = name,
                    provider = signUpToken.provider,
                    roles = setOf(Role.MEMBER),
                    clubIds = emptyList()
                )
            )
            val (accessToken, refreshToken) = jwtProvider.createTokens(user)

            SignUpResponse(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }
}
