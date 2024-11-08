package com.doyouclub.backend.domain.auth.exception

import com.doyouclub.backend.global.exception.ServerException

data class InvalidAccessException(
    override val message: String = "유효하지 않은 접근입니다."
) : ServerException(code = 403, message)
