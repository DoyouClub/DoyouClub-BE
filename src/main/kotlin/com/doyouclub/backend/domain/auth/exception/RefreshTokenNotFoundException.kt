package com.doyouclub.backend.domain.auth.exception

import com.doyouclub.backend.global.exception.ServerException

data class RefreshTokenNotFoundException(
    override val message: String = "리프레쉬 토큰을 찾을 수 없습니다."
) : ServerException(code = 404, message)
