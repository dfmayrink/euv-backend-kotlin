package com.euv.euvbackendkotlin.services

import com.euv.euvbackendkotlin.repository.ProductRepository
import com.euv.euvbackendkotlin.model.Product
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductService {
    @Autowired
    private lateinit var repository: ProductRepository

    fun findById(id: String): Mono<Product> {
        return repository.findById(ObjectId(id))
    }

    fun create(product: Product): Mono<Product> {
        return repository.save(product)
    }
}