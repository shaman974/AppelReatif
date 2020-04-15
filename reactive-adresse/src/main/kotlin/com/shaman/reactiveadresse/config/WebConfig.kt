package com.shaman.reactiveadresse.config

import com.shaman.reactiveadresse.handler.RechercheAdresse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.coRouter

@Configuration
@EnableWebFlux
class WebConfig : WebFluxConfigurer {

    @Bean
    fun routerFunctionA(handler: RechercheAdresse) = coRouter {
        "/adresse".nest {
            accept(APPLICATION_JSON).nest {
                GET("/", handler::getAdresses)
            }
        }
    }

}