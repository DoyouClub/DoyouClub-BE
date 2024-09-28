package com.doyouclub.backend.domain.auth.exception

import com.doyouclub.backend.global.exception.ServerException

data class AccountAlreadyExistException(
    override val message: String = "이미 가입된 계정입니다."
) : ServerException(code = 409, message)
