package com.euv.euvbackendkotlin.auth

import java.util.*

data class MyAccountDto(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val roles: List<String> = listOf<String>()
)
