package com.doyouclub.backend.domain.auth.exception

import com.doyouclub.backend.global.exception.ServerException

data class InvalidSignUpTokenException(
    override val message: String = "유효하지 않은 회원가입 토큰입니다."
) : ServerException(code = 403, message)
