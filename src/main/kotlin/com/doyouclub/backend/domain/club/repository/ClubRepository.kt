package com.doyouclub.backend.domain.club.repository

import com.doyouclub.backend.domain.club.model.Club
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClubRepository : CoroutineCrudRepository<Club, String> {
    fun findByIdIn(ids: List<String>): Flow<Club>
}
