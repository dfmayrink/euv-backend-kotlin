package com.euv.euvbackendkotlin.product

import org.bson.types.ObjectId
import java.util.*

data class ProductDto(
    var id: ObjectId? = null,
    var url: String? = null,
    var name: String? = null,
    var description: String? = null,
    var cover: String? = null,
    var images: List<String>? = null,
    var code: String? = null,
    var sku: String? = null,
    var price: Double? = null,
    var priceSale: Double? = null,
    var tags: List<String>? = null,
    var inStock: Boolean? = null,
    var taxes: Boolean? = null,
    var category: String? = null,
    var createdAt: Date? = null
)
