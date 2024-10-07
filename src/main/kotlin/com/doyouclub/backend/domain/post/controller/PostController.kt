package com.doyouclub.backend.domain.post.controller

import com.doyouclub.backend.domain.post.dto.response.PostResponse
import com.doyouclub.backend.domain.post.service.PostService
import com.doyouclub.backend.global.jwt.security.JwtAuthentication
import kotlinx.coroutines.flow.Flow
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RequestMapping("/post")
@RestController
class PostController(
    private val postService: PostService
) {
    @GetMapping("/my")
    suspend fun getMyPostPage(
        @AuthenticationPrincipal
        authentication: JwtAuthentication,
        @RequestParam(required = false)
        lastCreatedDate: LocalDateTime?,
        @RequestParam
        size: Int
    ): Flow<PostResponse> =
        postService.getMyPostPage(authentication.id, lastCreatedDate, size)
}
