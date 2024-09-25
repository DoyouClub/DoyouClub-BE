package com.doyouclub.backend.domain.auth.client

interface OAuth2Client {
    suspend fun getEmail(token: String): String
}
