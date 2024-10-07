package com.doyouclub.backend.domain.board.controller

import com.doyouclub.backend.domain.board.dto.response.BoardResponse
import com.doyouclub.backend.domain.board.service.BoardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/board")
@RestController
class BoardController(
    private val boardService: BoardService
) {
    @GetMapping("/{id}")
    suspend fun getBoardById(
        @PathVariable
        id: String
    ): BoardResponse =
        boardService.getBoardBYid(id)
}
