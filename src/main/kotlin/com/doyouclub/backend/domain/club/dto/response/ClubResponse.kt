package com.doyouclub.backend.domain.club.dto.response

import com.doyouclub.backend.domain.club.model.Club
import com.doyouclub.backend.domain.club.model.President
import com.doyouclub.backend.domain.club.model.enum.Activity
import com.doyouclub.backend.domain.club.model.enum.Tag

data class ClubResponse(
    val id: String,
    val name: String,
    val image: String,
    val description: String,
    val president: President?,
    val room: String?,
    val score: Double,
    val generation: Int,
    val activity: Activity,
    val tag: Tag
) {
    companion object {
        operator fun invoke(club: Club): ClubResponse =
            with(club) {
                ClubResponse(
                    id = id!!,
                    name = name,
                    image = image,
                    description = description,
                    president = president,
                    room = room,
                    score = score,
                    generation = generation,
                    activity = activity,
                    tag = tag
                )
            }
    }
}
