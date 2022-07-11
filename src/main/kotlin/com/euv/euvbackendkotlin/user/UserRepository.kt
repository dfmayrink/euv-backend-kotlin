package com.euv.euvbackendkotlin.user

import com.euv.euvbackendkotlin.user.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserRepository: ReactiveMongoRepository<User, ObjectId> {
    fun findUserByUsername(username: String) : Mono<User>

    suspend fun findByUsername(username: String) : User
    suspend fun save(user: User) : User
}