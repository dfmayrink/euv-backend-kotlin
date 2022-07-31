package com.euv.euvbackendkotlin.category

import org.bson.types.ObjectId

data class CategoryDto(
    var id: ObjectId? = null,
    var name: String? = null,
    var description: String? = null,
    var classify: List<String>? = null,
)
