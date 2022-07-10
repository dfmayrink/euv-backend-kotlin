package com.euv.euvbackendkotlin.api

import com.euv.euvbackendkotlin.data.vo.v1.AccountCredentialsVO
import com.euv.euvbackendkotlin.data.vo.v1.TokenVO
import com.euv.euvbackendkotlin.model.User
import com.euv.euvbackendkotlin.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    lateinit var authService: AuthService

    @PostMapping(value = ["/signin"])
    fun signin(@RequestBody data: AccountCredentialsVO?) : Mono<TokenVO> {
        return if (data!!.username.isNullOrBlank() || data.password.isNullOrBlank())
                Mono.error(ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid client request"))
            else authService.signin(data!!)
    }

    @PostMapping(value = ["/signup"])
    suspend fun signup(@RequestBody data: AccountCredentialsVO?) : User {
        return authService.signup(data!!)
    }

    @PutMapping(value = ["/refresh/{username}"])
    fun refreshToken(@PathVariable("username") username: String?,
                     @RequestHeader("Authorization") refreshToken: String?) : ResponseEntity<*> {
        return if (refreshToken.isNullOrBlank() || username.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid client request")
            else authService.refreshToken(username, refreshToken)
    }
}