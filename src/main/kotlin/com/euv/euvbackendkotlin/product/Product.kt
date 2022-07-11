package com.euv.euvbackendkotlin.product

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Product(
    @Id
    var id: ObjectId = ObjectId(),
    var name: String = "",
    var description: String = "",
    var images: List<String> = ArrayList<String>(),
    var code: String= "",
    var sku: String = "",
    var price: Double = .0,
    var priceSale: Double = .0,
    var tags: List<String> = ArrayList<String>(),
    var inStock: Boolean = false,
    var taxes: Boolean = false,
    var category: List<String> = ArrayList<String>()
)
