package com.shaman.reactiveadresse.handler

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RechercheAdresseTest {

    @Autowired
    lateinit var client: WebTestClient

    private var wireMockServer = WireMockServer(8850)

    @BeforeAll
    fun setup() {
        wireMockServer.start()
    }

    @AfterAll
    fun closeDown() {
        wireMockServer.stop()
        wireMockServer.resetMappings()
    }

    @Test
    fun testGetAdresse() {
        wireMockServer.stubFor(WireMock
                .get(WireMock.anyUrl())
                .withQueryParam("q", WireMock.equalTo("toto"))
                .willReturn(WireMock.aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("json/mockAdresse.json")))

        client.get().uri("/adresse?query=toto")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].adresse")
                .isEqualTo("Chemin Totor 97430 Le Tampon")
                .jsonPath("$[0].codePostal")
                .isEqualTo("97422")
                .jsonPath("$.length()")
                .isEqualTo(2)
    }
}