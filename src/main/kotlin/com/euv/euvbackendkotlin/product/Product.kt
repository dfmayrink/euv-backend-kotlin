package com.euv.euvbackendkotlin.product

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Product(
    @Id
    var id: ObjectId? = null,
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
    var createdAt: Date? = Date()
)
