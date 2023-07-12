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
    fun signin(@RequestBody data: AccountCredentialsVO?) : Mono<TokenVO> {
        return if (data!!.email.isNullOrBlank() || data.password.isNullOrBlank())
                Mono.error(ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid client request"))
            else authService.signin(data)
    }

    @PostMapping(value = ["/signup"])
    fun signup(@RequestBody data: AccountCredentialsVO?) : Mono<User> {
        return authService.signup(data!!)
    }

    @PutMapping(value = ["/refresh/{username}"])
    fun refreshToken(@PathVariable("username") username: String?,
                     @RequestHeader("Authorization") refreshToken: String?) : Mono<ResponseEntity<*>> {
        return if (refreshToken.isNullOrBlank() || username.isNullOrBlank())
            Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid client request"))
            else authService.refreshToken(username, refreshToken)
    }
}