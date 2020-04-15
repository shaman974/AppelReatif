package com.shaman.reactiveadresse.remote.impl

import com.shaman.reactiveadresse.remote.RechercheAdresseGateway
import com.shaman.reactiveadresse.remote.ResponseAdresse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@Component
class RechercheAdresseGatewayImpl(@Value("\${api.adresse.url}" ) private val urlApi: String) : RechercheAdresseGateway {
    override suspend fun getAdresseFromGateway(critere: String?): List<ResponseAdresse> {
        val webClient = WebClient.create("$urlApi?q=$critere")
        val body = webClient.get().accept(MediaType.APPLICATION_JSON).awaitExchange().awaitBody<ListAddressGeoGouv>()
        return body.features.map { ResponseAdresse(it.properties.label, it.properties.citycode) }
    }
}


data class ListAddressGeoGouv(val features:List<AddressGeoGouv>)
data class AddressGeoGouv(val type:String, val geometry:Geometry,val properties:Property)
data class Geometry(val type:String, val coordinates:List<String>)
data class Property(val label:String,
                    val score:String,
                    val id:String,
                    val type:String,
                    val x:Double,
                    val y:Double,
                    val importance:Double,
                    val name:String,
                    val postcode:String,
                    val citycode:String,
                    val city:String,
                    val context:String)