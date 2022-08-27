package com.euv.euvbackendkotlin.auth

import com.euv.euvbackendkotlin.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import java.security.Principal


@Controller
class AuthGQLController(private val authService: AuthService) {
    @Autowired
    private lateinit var userDetailsService: ReactiveUserDetailsService
    @QueryMapping
    fun signin(@Argument("auth") accountCredentialsDto: AccountCredentialsDto): Mono<AuthDto> {
        return authService.signin(accountCredentialsDto)
    }

    @QueryMapping
    fun myAccount(principal: Principal?): MyAccountDto {
        principal as UsernamePasswordAuthenticationToken
        val user = principal.principal as User
        return MyAccountDto(user.firstName, user.lastName, user.username, user.authorities.map { it.authority.toString() })

    }

    @MutationMapping
    fun signup(@Argument("register") accountCredentialsDto: AccountCredentialsDto) : Mono<AuthDto> {
        return authService.signup(accountCredentialsDto)
    }

}