package com.euv.euvbackendkotlin.product

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: ReactiveMongoRepository<Product, ObjectId> {
}