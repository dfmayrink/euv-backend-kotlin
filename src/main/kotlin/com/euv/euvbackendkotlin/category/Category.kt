package com.euv.euvbackendkotlin.category

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Category(
    @Id
    var id: ObjectId? = null,
    var name: String? = null,
    var description: String? = null,
    var classify: List<String>? = null,
)
