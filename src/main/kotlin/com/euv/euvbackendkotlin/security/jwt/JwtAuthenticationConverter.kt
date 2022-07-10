package com.euv.euvbackendkotlin.security.jwt

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationConverter : ServerAuthenticationConverter {

    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    private val log = LogFactory.getLog(this::class.java)

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {
        val token = jwtTokenProvider.resolveToken(exchange!!.request)

        return if(!token.isNullOrBlank() && jwtTokenProvider.validateToken(token)) {
            jwtTokenProvider.getAuthentication(token)
        } else
            Mono.empty()
    }
}