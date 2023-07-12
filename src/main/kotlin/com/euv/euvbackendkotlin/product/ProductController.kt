package com.euv.euvbackendkotlin.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/products")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @GetMapping
    fun getAllProduct() : Flux<Product> {
        return productService.findAll()
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: String): Mono<Product> {
        return productService.findById(id)
    }

    @PostMapping
    @Secured
    fun postProduct(@RequestBody product: Product) : Mono<Product> {
        return productService.create(product)
    }
}