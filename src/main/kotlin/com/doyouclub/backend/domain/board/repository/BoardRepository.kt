package com.doyouclub.backend.domain.board.repository

import com.doyouclub.backend.domain.board.model.Board
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : CoroutineCrudRepository<Board, String>
