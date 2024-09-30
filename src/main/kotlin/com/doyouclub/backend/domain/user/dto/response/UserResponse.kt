package com.doyouclub.backend.domain.user.dto.response

import com.doyouclub.backend.domain.user.model.User
import com.doyouclub.backend.domain.user.model.enum.Provider
import com.doyouclub.backend.domain.user.model.enum.Role

data class UserResponse(
    val id: String,
    val email: String,
    val name: String,
    val provider: Provider,
    val roles: Set<Role>
) {
    companion object {
        operator fun invoke(user: User): UserResponse =
            with(user) {
                UserResponse(
                    id = id!!,
                    email = email,
                    name = name,
                    provider = provider,
                    roles = roles
                )
            }
    }
}
