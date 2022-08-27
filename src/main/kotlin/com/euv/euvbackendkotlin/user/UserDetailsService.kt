package com.euv.euvbackendkotlin.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserDetailsService :
    ReactiveUserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun findByUsername(username: String): Mono<UserDetails> {
        return userRepository.findByUsername(username)
            .cast(UserDetails::class.java)
    }


}