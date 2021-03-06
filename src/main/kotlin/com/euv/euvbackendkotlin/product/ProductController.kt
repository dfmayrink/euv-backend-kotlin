package com.euv.euvbackendkotlin.product

import com.euv.euvbackendkotlin.product.Product
import com.euv.euvbackendkotlin.product.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/products")
class ProductController {

    @Autowired
    lateinit var productService: ProductService

    @GetMapping
    fun getAllProduct() : Flux<Product> {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: String): Mono<Product> {
        return productService.findById(id);
    }

    @PostMapping
    fun postProduct(@RequestBody product: Product) : Mono<Product> {
        return productService.create(product)
    }
}