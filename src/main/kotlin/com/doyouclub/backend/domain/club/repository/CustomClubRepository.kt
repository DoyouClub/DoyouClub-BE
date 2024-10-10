package com.doyouclub.backend.domain.club.repository

import com.doyouclub.backend.domain.club.model.Club
import com.doyouclub.backend.domain.club.model.enum.Activity
import com.doyouclub.backend.domain.club.model.enum.Tag
import com.doyouclub.backend.domain.post.model.Post
import com.doyouclub.backend.global.util.query
import com.doyouclub.backend.global.util.sortBy
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.query.lt
import org.springframework.data.mongodb.core.query.regex
import org.springframework.stereotype.Repository

@Repository
class CustomClubRepository(
    private val mongoTemplate: ReactiveMongoTemplate
) {
    fun searchPage(
        name: String,
        tag: Tag?,
        activity: Activity?,
        lastId: String?,
        size: Int
    ): Flow<Club> =
        query(mongoTemplate) {
            tag?.let { addCriteria(Club::tag isEqualTo it) }
            activity?.let { addCriteria(Club::activity isEqualTo it) }
            addCriteria(Club::name.regex(name, "i"))
            paging(lastId, size)
        }

    private fun Query.paging(lastId: String?, size: Int) {
        lastId?.let { addCriteria(Post::createdDate lt it) }
        with(Club::id sortBy Direction.DESC)
        limit(size)
    }
}
