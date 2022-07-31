package com.euv.euvbackendkotlin.category

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: ReactiveMongoRepository<Category, ObjectId> {
}