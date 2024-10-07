package com.doyouclub.backend.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebFluxConfiguration : WebFluxConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        DateTimeFormatterRegistrar()
            .apply {
                setUseIsoFormat(true)
                registerFormatters(registry)
            }
    }
}
