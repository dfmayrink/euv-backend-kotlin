package com.euv.euvbackendkotlin.product

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ProductService {
    @Autowired
    private lateinit var repository: ProductRepository

    fun findById(id: String): Mono<ProductDto> {
        return repository.findById(ObjectId(id)).map { ProductMapper.convertToDto(it) }
    }

    @PreAuthorize("hasRole('ADMIN')")
    fun create(productDto: ProductDto): Mono<ProductDto> {
        val product = ProductMapper.convertToModel(productDto)
        return repository.save(product).map { ProductMapper.convertToDto(it) }
    }

    @PreAuthorize("hasRole('ADMIN')")
    fun delete(id: ObjectId) : Mono<Void> {
        return repository.deleteById(id)
    }

    fun findAll(exampleDto: ProductDto = ProductDto()): Flux<ProductDto> {
        val example = Example.of( ProductMapper.convertToModel(exampleDto) )
        return repository.findAll(example).map { ProductMapper.convertToDto(it) }
    }
}