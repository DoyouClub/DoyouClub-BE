package com.doyouclub.backend.domain.auth.exception

import com.doyouclub.backend.global.exception.ServerException

data class SignUpTokenNotFoundException(
    override val message: String = "회원가입 토큰을 찾을 수 없습니다."
) : ServerException(code = 404, message)
