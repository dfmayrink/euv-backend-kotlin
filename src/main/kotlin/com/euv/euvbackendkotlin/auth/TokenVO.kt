package com.euv.euvbackendkotlin.auth

import java.util.*

data class TokenVO(

    val username: String? = "",
    val authenticated: Boolean? = false,
    val created: Date? = null,
    val expiration: Date? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
