package com.shaman.reactiveadresse.remote

interface RechercheAdresseGateway {

    suspend fun getAdresseFromGateway(critere: String?) : List<ResponseAdresse>

}

data class ResponseAdresse(val adresse: String, val codePostal: String)