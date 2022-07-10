package com.euv.euvbackendkotlin.security.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class JwtTokenFilter(@field:Autowired private val tokenProvider: JwtTokenProvider)
    : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = tokenProvider.resolveToken(exchange.request)
        if(!token.isNullOrBlank() && tokenProvider.validateToken(token)) {
            return tokenProvider.getAuthentication(token).flatMap {
                SecurityContextHolder.getContext().authentication = it
                chain.filter(exchange)
            }
        }
        return chain.filter(exchange)
    }
}