package com.doyouclub.backend.domain.club.model

import com.doyouclub.backend.domain.club.model.enum.Activity
import com.doyouclub.backend.domain.club.model.enum.Tag
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Club(
    @Id
    val id: String? = null,
    val name: String,
    val image: String,
    val description: String,
    val president: President?,
    val room: String?,
    val score: Double,
    val generation: Int,
    val activity: Activity,
    val tag: Tag
)
