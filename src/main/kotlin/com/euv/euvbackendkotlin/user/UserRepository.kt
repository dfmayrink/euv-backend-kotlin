package com.euv.euvbackendkotlin.user

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserRepository: ReactiveMongoRepository<User, ObjectId> {
    fun findUserByUsername(username: String) : Mono<User>

    fun findByUsername(username: String) : Mono<User>
    fun save(user: User) : Mono<User>
}