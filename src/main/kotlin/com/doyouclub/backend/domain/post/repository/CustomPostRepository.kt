package com.doyouclub.backend.domain.post.repository

import com.doyouclub.backend.domain.post.model.Post
import com.doyouclub.backend.global.util.query
import com.doyouclub.backend.global.util.sortBy
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.query.lt
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class CustomPostRepository(
    private val mongoTemplate: ReactiveMongoTemplate
) {
    fun getPageByWriterId(
        writerId: String,
        lastCreatedDate: LocalDateTime?,
        size: Int
    ): Flow<Post> =
        query(mongoTemplate) {
            addCriteria(Post::writerId isEqualTo writerId)
            paging(lastCreatedDate, size)
        }

    private fun Query.paging(lastCreatedDate: LocalDateTime?, size: Int) {
        lastCreatedDate?.let { addCriteria(Post::createdDate lt it) }
        with(Post::createdDate sortBy Direction.DESC)
        limit(size)
    }
}
