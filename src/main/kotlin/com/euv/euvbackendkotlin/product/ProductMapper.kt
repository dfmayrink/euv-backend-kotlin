package com.euv.euvbackendkotlin.product

object ProductMapper {
    fun convertToDto(product: Product) : ProductDto {
        product.apply {
            return ProductDto(id, name, description, images, code, sku, price, priceSale, tags, inStock,
                taxes, this.category, createdAt)
        }
    }
    fun convertToModel(productInput: ProductDto) : Product {
        productInput.apply {
            return Product(id, name, description, images, code, sku, price, priceSale, tags, inStock,
                taxes, category, createdAt)
        }
    }
}