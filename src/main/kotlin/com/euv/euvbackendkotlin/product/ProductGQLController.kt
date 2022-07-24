/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.euv.euvbackendkotlin.product

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class ProductGQLController(private val productService: ProductService) {
    @QueryMapping
    fun products(@Argument("name") name: String?): Flux<ProductDto> {
        var example = ProductDto(name = name, createdAt = null)
        return productService.findAll(example)
    }

    @MutationMapping
    fun createProduct(@Argument("product") productDto: ProductDto): Mono<ProductDto> {
        return productService.create(productDto)
    }
}