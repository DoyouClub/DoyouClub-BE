package com.doyouclub.backend.domain.auth.service

import com.doyouclub.backend.domain.auth.client.KakaoClient
import com.doyouclub.backend.domain.auth.client.OAuth2Client
import com.doyouclub.backend.domain.user.model.enum.Provider
import org.springframework.stereotype.Service

@Service
class OAuth2Service(
    private val kakaoClient: KakaoClient
) {
    suspend fun getEmailByTokenAndProvider(token: String, provider: Provider): String =
        provider.getClient()
            .getEmail(token)

    private fun Provider.getClient(): OAuth2Client =
        when (this) {
            Provider.KAKAO -> kakaoClient
        }
}
