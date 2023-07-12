package com.euv.euvbackendkotlin.auth

import com.euv.euvbackendkotlin.security.JwtTokenProvider
import com.euv.euvbackendkotlin.user.User
import com.euv.euvbackendkotlin.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import java.util.logging.Logger

@Service
class AuthService {


    @Autowired
    private lateinit var authenticationManager: ReactiveAuthenticationManager

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    private val logger = Logger.getLogger(AuthService::class.java.name)

    fun signin(data: AccountCredentialsVO): Mono<TokenVO> {
        logger.info("Trying log user ${data.email}")
        val username = data.email
        val password = data.password
        val authenticate = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        val retorno = authenticate.map {
            tokenProvider.createAccessToken(it.name, it.authorities.map { it2 -> it2.toString() })
        }.onErrorMap {
            ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid client request")
        }
        return retorno
    }

    fun signup(data: AccountCredentialsVO): Mono<User> {
        val username = data.email!!
        val existingUser = userRepository.findByUsername(username)
        return existingUser.map {
            User()
        }.switchIfEmpty(
            Mono.fromCallable {
                val user = User()
                user.username = username
                user.password = passwordEncoder.encode(data.password!!)
                userRepository.save(user)
            }.flatMap { it }
        )

    }

    fun refreshToken(username: String, refreshToken: String): Mono<ResponseEntity<*>> {
        logger.info("Trying get refresh token to user $username")

        val user = userRepository.findUserByUsername(username)
        return user.map {
            ResponseEntity.ok(tokenProvider.refreshToken(refreshToken))
        }
    }
}