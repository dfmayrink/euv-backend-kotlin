package com.euv.euvbackendkotlin.auth

import com.euv.euvbackendkotlin.exceptions.GraphqlException
import com.euv.euvbackendkotlin.security.JwtTokenProvider
import com.euv.euvbackendkotlin.user.Permission
import com.euv.euvbackendkotlin.user.User
import com.euv.euvbackendkotlin.user.UserDetailsService
import com.euv.euvbackendkotlin.user.UserRepository
import graphql.ErrorType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
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
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    private val logger = Logger.getLogger(AuthService::class.java.name)

    fun signin(data: AccountCredentialsDto): Mono<AuthDto> {
        logger.info("Trying log user ${data.email}")
        val username = data.email
        val password = data.password
        val authenticate = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        val retorno = authenticate.map {
            tokenProvider.createAccessToken(it.name, it.authorities.map { aut -> aut.toString() })
        }.onErrorMap {
            GraphqlException("Invalid user name or password", ErrorType.ValidationError)
        }
        return retorno
    }

    suspend fun signup(data: AccountCredentialsDto): User {
        val username = data.email!!
        val existingUser = userRepository.findByUsername(username)
        if (existingUser != null) throw ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "User already exists")
        val user = User()
        user.username = username
        user.password = passwordEncoder.encode(data.password!!)
        user.permissions = listOf<Permission>(Permission("ROLE_USER")).toMutableList()
        return userRepository.save(user)
    }

    fun refreshToken(username: String, refreshToken: String): ResponseEntity<*> {
        logger.info("Trying get refresh token to user $username")

        val user = userRepository.findUserByUsername(username)
        val tokenResponse: AuthDto = if (user != null) {
            tokenProvider.refreshToken(refreshToken)
        } else {
            throw UsernameNotFoundException("Username $username not found!")
        }
        return ResponseEntity.ok(tokenResponse)
    }
}