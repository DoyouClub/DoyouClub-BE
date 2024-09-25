package com.doyouclub.backend.domain.auth.exception

import com.doyouclub.backend.global.exception.ServerException

data class InvalidRefreshTokenException(
    override val message: String = "유효하지 않은 리프레쉬 토큰입니다."
) : ServerException(code = 403, message)
