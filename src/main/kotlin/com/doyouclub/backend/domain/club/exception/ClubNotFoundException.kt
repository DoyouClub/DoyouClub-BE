package com.doyouclub.backend.domain.club.exception

import com.doyouclub.backend.global.exception.ServerException

data class ClubNotFoundException(
    override val message: String = "동아리를 찾을 수 없습니다."
) : ServerException(code = 404, message)
