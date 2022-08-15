package com.euv.euvbackendkotlin.api

import com.euv.euvbackendkotlin.auth.AccountCredentialsDto
import com.euv.euvbackendkotlin.auth.AuthController
import com.euv.euvbackendkotlin.auth.AuthService
import com.euv.euvbackendkotlin.config.SpringSecurityTestConfig
import com.euv.euvbackendkotlin.security.JwtTokenProvider
import com.euv.euvbackendkotlin.user.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@WebFluxTest(controllers = [AuthController::class])
@Import(SpringSecurityTestConfig::class, AuthService::class)
internal class AuthControllerTest {

    @MockBean
    private lateinit var userRepositor: UserRepository

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockBean
    private lateinit var jwtTokenProvider: JwtTokenProvider


    @Test
    fun signin() {
        var accountCredentialsVO = AccountCredentialsDto("test", "test")
        val result = webClient.post().uri("signin").contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(accountCredentialsVO))
            .exchange()
    }

    @Test
    fun signup() {
    }

    @Test
    fun refreshToken() {
    }
}