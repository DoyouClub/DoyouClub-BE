package com.doyouclub.backend.global.exception.common

import com.doyouclub.backend.global.exception.ServerException

data class PermissionDeniedException(
    override val message: String = "권한이 없습니다."
) : ServerException(code = 403, message)
