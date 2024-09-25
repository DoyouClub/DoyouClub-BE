package com.doyouclub.backend.domain.user.repository

import com.doyouclub.backend.domain.user.model.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<User, String> {
    suspend fun findByEmail(email: String): User?
}
