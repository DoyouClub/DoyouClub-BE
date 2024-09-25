package com.doyouclub.backend.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@EnableReactiveMongoAuditing
@Configuration
class MongoConfiguration {
    @Bean
    fun reactiveMappingMongoConverter(mongoMappingContext: MongoMappingContext): MappingMongoConverter =
        MappingMongoConverter(
            ReactiveMongoTemplate.NO_OP_REF_RESOLVER,
            mongoMappingContext
        ).apply { setTypeMapper(DefaultMongoTypeMapper(null)) }
}
