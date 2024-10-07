package com.doyouclub.backend.domain.board.service

import com.doyouclub.backend.domain.board.dto.response.BoardResponse
import com.doyouclub.backend.domain.board.exception.BoardNotFoundException
import com.doyouclub.backend.domain.board.repository.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {
    suspend fun getBoardBYid(id: String): BoardResponse =
        boardRepository.findById(id)
            ?.let { BoardResponse(it) }
            ?: throw BoardNotFoundException()
}
