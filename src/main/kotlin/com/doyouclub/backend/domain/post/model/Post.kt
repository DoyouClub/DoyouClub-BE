package com.doyouclub.backend.domain.post.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Post(
    @Id
    val id: String? = null,
    val writerId: String,
    val boardId: String,
    val title: String,
    val content: String,
    val likedUserIds: HashSet<String> = hashSetOf(),
    val images: List<String>,
    @Indexed
    @CreatedDate
    val createdDate: LocalDateTime? = null
)
