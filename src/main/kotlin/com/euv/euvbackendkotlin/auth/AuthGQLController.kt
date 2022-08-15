package com.euv.euvbackendkotlin.auth

import com.euv.euvbackendkotlin.exceptions.GraphqlException
import com.euv.euvbackendkotlin.user.User
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class AuthGQLController(private val authService: AuthService) {
    @QueryMapping
    fun signin(@Argument("auth") accountCredentialsDto: AccountCredentialsDto): Mono<AuthDto> {
        return authService.signin(accountCredentialsDto)
    }

    @MutationMapping
    fun signup(@Argument("auth") accountCredentialsDto: AccountCredentialsDto) : Mono<AuthDto> {
        return authService.signup(accountCredentialsDto)
    }

}