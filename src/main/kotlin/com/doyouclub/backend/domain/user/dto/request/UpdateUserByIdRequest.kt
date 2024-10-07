package com.doyouclub.backend.domain.user.dto.request

import com.doyouclub.backend.domain.user.model.User

data class UpdateUserByIdRequest(
    val name: String
) {
    fun toDomain(user: User): User =
        user.copy(name = name)
}
