package com.euv.euvbackendkotlin.security

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
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