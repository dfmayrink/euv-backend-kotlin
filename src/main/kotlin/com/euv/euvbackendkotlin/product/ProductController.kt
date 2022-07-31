package com.euv.euvbackendkotlin.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/products")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @GetMapping
    fun getAllProduct() : Flux<ProductDto> {
        return productService.findAll()
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: String): Mono<ProductDto> {
        return productService.findById(id)
    }

    @PostMapping
    fun postProduct(@RequestBody productDto: ProductDto) : Mono<ProductDto> {
        return productService.create(productDto)
    }
}