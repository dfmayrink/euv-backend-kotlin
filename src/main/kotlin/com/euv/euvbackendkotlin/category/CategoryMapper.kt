package com.euv.euvbackendkotlin.category

object CategoryMapper {
    fun convertToDto(category: Category) : CategoryDto {
        category.apply {
            return CategoryDto(id, name, description, classify)
        }
    }
    fun convertToModel(categoryDto: CategoryDto) : Category {
        categoryDto.apply {
            return Category(id, name, description, classify)
        }
    }
}