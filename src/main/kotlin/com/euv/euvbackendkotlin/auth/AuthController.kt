package com.euv.euvbackendkotlin.auth

import com.euv.euvbackendkotlin.user.User
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
    fun signin(@RequestBody data: AccountCredentialsDto?) : Mono<AuthDto> {
        return if (data!!.email.isNullOrBlank() || data.password.isNullOrBlank())
                Mono.error(ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid client request"))
            else authService.signin(data)
    }

    @PostMapping(value = ["/signup"])
    fun signup(@RequestBody data: AccountCredentialsDto?) : Mono<AuthDto> {
        return authService.signup(data!!)
    }

    @PutMapping(value = ["/refresh/{username}"])
    fun refreshToken(@PathVariable("username") username: String?,
                     @RequestHeader("Authorization") refreshToken: String?) : ResponseEntity<*> {
        return if (refreshToken.isNullOrBlank() || username.isNullOrBlank())
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid client request")
            else ResponseEntity.ok(authService.refreshToken(username, refreshToken).block())
    }
}