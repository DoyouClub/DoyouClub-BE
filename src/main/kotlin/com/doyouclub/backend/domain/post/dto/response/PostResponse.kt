package com.doyouclub.backend.domain.post.dto.response

import com.doyouclub.backend.domain.post.model.Post
import java.time.LocalDateTime

data class PostResponse(
    val id: String,
    val writerId: String,
    val boardId: String,
    val title: String,
    val content: String,
    val likedUserIds: Set<String>,
    val images: List<String>,
    val createdDate: LocalDateTime
) {
    companion object {
        operator fun invoke(post: Post): PostResponse =
            with(post) {
                PostResponse(
                    id = id!!,
                    writerId = writerId,
                    boardId = boardId,
                    title = title,
                    content = content,
                    likedUserIds = likedUserIds,
                    images = images,
                    createdDate = createdDate!!
                )
            }
    }
}
