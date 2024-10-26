package com.doyouclub.backend.domain.user.repository

import com.doyouclub.backend.domain.user.model.User
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<User, String> {
    suspend fun findByEmail(email: String): User?

    fun findAllByClubIdsContains(clubId: String): Flow<User>
}
