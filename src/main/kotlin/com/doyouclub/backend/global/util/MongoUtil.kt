package com.doyouclub.backend.global.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.mapping.toDotPath
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Query
import kotlin.reflect.KProperty

internal infix fun <T> KProperty<T>.sortBy(direction: Direction): Sort = Sort.by(direction, toDotPath())

internal inline fun <reified T : Any> query(mongoTemplate: ReactiveMongoTemplate, init: Query.() -> Unit): Flow<T> =
    Query().apply(init)
        .let { mongoTemplate.find<T>(it) }
        .asFlow()
