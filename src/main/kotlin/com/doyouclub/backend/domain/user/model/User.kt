package com.doyouclub.backend.domain.user.model

import com.doyouclub.backend.domain.user.model.enum.Provider
import com.doyouclub.backend.domain.user.model.enum.Role
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Id
    val id: String? = null,
    val email: String,
    val name: String,
    val provider: Provider,
    val roles: Set<Role>
)
