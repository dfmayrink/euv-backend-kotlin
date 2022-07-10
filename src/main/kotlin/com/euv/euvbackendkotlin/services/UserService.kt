package br.com.erudio.services

import com.euv.euvbackendkotlin.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.logging.Logger

@Service
class UserService(@field:Autowired var repository: UserRepository) : ReactiveUserDetailsService {

    private val logger = Logger.getLogger(UserService::class.java.name)

    override fun findByUsername(username: String?): Mono<UserDetails> {
        logger.info("Finding one User by Username $username!")
        val user = repository.findUserByUsername(username!!)
        return (user ?: throw UsernameNotFoundException("Username $username not found!")) as Mono<UserDetails>
    }
}