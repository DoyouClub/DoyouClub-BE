package com.doyouclub.backend.domain.auth.exception

import com.doyouclub.backend.global.exception.ServerException

data class UserAlreadyJoinedClubException(
    override val message: String = "이미 동아리에 가입한 사용자입니다."
) : ServerException(code = 409, message)
