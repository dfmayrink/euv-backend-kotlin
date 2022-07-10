package com.euv.euvbackendkotlin.repository

import com.euv.euvbackendkotlin.model.Product
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: ReactiveMongoRepository<Product, ObjectId> {
}