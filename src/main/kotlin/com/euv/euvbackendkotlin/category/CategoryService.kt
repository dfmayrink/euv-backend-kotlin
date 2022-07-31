package com.euv.euvbackendkotlin.category

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CategoryService {
    @Autowired
    private lateinit var repository: CategoryRepository

    fun findById(id: String): Mono<CategoryDto> {
        return repository.findById(ObjectId(id)).map { CategoryMapper.convertToDto(it) }
    }

    @PreAuthorize("hasRole('ADMIN')")
    fun create(categoryDto: CategoryDto): Mono<CategoryDto> {
        val category = CategoryMapper.convertToModel(categoryDto)
        return repository.save(category).map { CategoryMapper.convertToDto(it) }
    }

    @PreAuthorize("hasRole('ADMIN')")
    fun delete(id: ObjectId) : Mono<Void> {
        return repository.deleteById(id)
    }

    fun findAll(exampleDto: CategoryDto = CategoryDto()): Flux<CategoryDto> {
        val example = Example.of(CategoryMapper.convertToModel(exampleDto))
        return repository.findAll(example).map { CategoryMapper.convertToDto(it) }
    }
}