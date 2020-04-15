package com.shaman.reactiveadresse.handler

import com.shaman.reactiveadresse.remote.RechercheAdresseGateway
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class RechercheAdresse(private val rechercheAdresseGateway: RechercheAdresseGateway) {

    suspend fun getAdresses(request: ServerRequest): ServerResponse {
        return ok().bodyValueAndAwait(rechercheAdresseGateway.getAdresseFromGateway(request.queryParam("query").orElse(null)))
    }

}