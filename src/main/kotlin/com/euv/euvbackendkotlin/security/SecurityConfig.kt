package com.euv.euvbackendkotlin.security

import com.euv.euvbackendkotlin.security.JwtAuthenticationConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import reactor.core.publisher.Mono


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {

    @Autowired
    private lateinit var jwtAuthenticationConverter: JwtAuthenticationConverter

    @Bean
    fun reactiveAuthenticationManager(
        userDetailsService: org.springframework.security.core.userdetails.ReactiveUserDetailsService?,
        passwordEncoder: PasswordEncoder?
    ): ReactiveAuthenticationManager? {
        val authenticationManager = UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService)
        authenticationManager.setPasswordEncoder(passwordEncoder)
        return authenticationManager
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        val encoders: MutableMap<String, PasswordEncoder> = HashMap<String, PasswordEncoder>()
        encoders["bcrypt"] = BCryptPasswordEncoder()
        val passwordEncoder = DelegatingPasswordEncoder("bcrypt", encoders)
        passwordEncoder.setDefaultPasswordEncoderForMatches(BCryptPasswordEncoder())
        return passwordEncoder
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("https://eraumavezbh.com.br", "http://localhost:3001")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        val authFilter = AuthenticationWebFilter { authentication: Authentication ->
            Mono.just(authentication)
        }
        authFilter.setServerAuthenticationConverter(jwtAuthenticationConverter)

        http
            .authorizeExchange { exchanges: AuthorizeExchangeSpec ->
                exchanges
                    .pathMatchers("/auth/signin",
                        "/auth/signup", "/auth/refresh", "/actuator/**", "/swagger-ui.html", "/v3/api-docs", "/files/**").permitAll()
                    .anyExchange().authenticated()
            }
            .cors().and()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)

        return http.build()
    }


}