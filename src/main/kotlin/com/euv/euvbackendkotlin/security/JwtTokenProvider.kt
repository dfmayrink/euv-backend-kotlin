package com.euv.euvbackendkotlin.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.euv.euvbackendkotlin.auth.TokenVO
import com.euv.euvbackendkotlin.exceptions.InvalidJwtAuthenticationException
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class JwtTokenProvider {

    private val Bearer = "Bearer "

    @Value("\${security.jwt.token.secret-key:secret}")
    private var secretKey = "secret"

    @Value("\${security.jwt.token.expire-length:3600000}")
    private var validityInMilliseconds: Long = 3_600_000 //1h

    @Autowired
    private lateinit var userDetailsService: ReactiveUserDetailsService

    private lateinit var algorithm: Algorithm

    @PostConstruct
    protected fun init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        algorithm = Algorithm.HMAC512(secretKey.toByteArray())
    }

    fun createAccessToken(username: String, roles: List<String?>) : TokenVO {
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        val accessToken = getAccessToken(username, roles, now, validity)
        val refreshToken = getRefreshToken(username, roles, now)
        return TokenVO(
            username = username,
            authenticated = true,
            accessToken = accessToken,
            refreshToken = refreshToken,
            created = now,
            expiration = validity
        )
    }

    fun refreshToken(refreshToken: String) : TokenVO {
        var token = ""
        if(refreshToken.contains(Bearer)) token = refreshToken.substring(Bearer.length)
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = verifier.verify(token)
        val username: String = decodedJWT.subject
        val roles: List<String> = decodedJWT.getClaim("roles").asList(String::class.java)
        return createAccessToken(username, roles)
    }

    private fun getAccessToken(username: String, roles: List<String?>, now: Date, validity: Date): String {
        val issuerURL = "https://eraumavezbh.com.br"
        return JWT.create()
            .withClaim("roles", roles)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .withIssuer(issuerURL)
            .sign(algorithm)
            .trim()
    }

    private fun getRefreshToken(username: String, roles: List<String?>, now: Date): String {
        val validityRefreshToken = Date(now.time + validityInMilliseconds * 3)
        return JWT.create()
            .withClaim("roles", roles)
            .withExpiresAt(validityRefreshToken)
            .withSubject(username)
            .sign(algorithm)
            .trim()
    }
    
    fun getAuthentication(token: String) : Mono<Authentication> {
        val decodedJWT: DecodedJWT = decodedToken(token)
        return userDetailsService.findByUsername(decodedJWT.subject).map {
            UsernamePasswordAuthenticationToken(it, "", it.authorities)
        }
    }

    private fun decodedToken(token: String): DecodedJWT {
        val algorithm = Algorithm.HMAC512(secretKey.toByteArray())
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    fun resolveToken(req: ServerHttpRequest): String? {
        val bearerToken = req.headers.getFirst("Authorization")
        return if(!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(Bearer.length)
        } else null
    }

    fun validateToken(token: String): Boolean {
        val decodedJWT = decodedToken(token)
        try {
            decodedJWT.expiresAt.before(Date())
            return true
        } catch (e: Exception) {
            throw InvalidJwtAuthenticationException("Expired or invalid JWT token!")
        }
    }
}
