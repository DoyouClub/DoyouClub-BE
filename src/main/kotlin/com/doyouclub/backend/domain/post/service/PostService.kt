package com.doyouclub.backend.domain.post.service

import com.doyouclub.backend.domain.post.dto.response.PostResponse
import com.doyouclub.backend.domain.post.repository.CustomPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PostService(
    private val customPostRepository: CustomPostRepository
) {
    fun getMyPostPage(
        writerId: String,
        lastCreatedDate: LocalDateTime?,
        size: Int
    ): Flow<PostResponse> =
        customPostRepository.getPageByWriterId(writerId, lastCreatedDate, size)
            .map { PostResponse(it) }
}
