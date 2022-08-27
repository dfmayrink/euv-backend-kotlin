package com.euv.euvbackendkotlin.auth

data class AccountCredentialsDto(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
)
