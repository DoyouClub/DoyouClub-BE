package com.doyouclub.backend.domain.user.exception

import com.doyouclub.backend.global.exception.ServerException

data class UserNotFoundException(
    override val message: String = "유저를 찾을 수 없습니다."
) : ServerException(code = 404, message)
