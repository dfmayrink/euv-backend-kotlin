package com.euv.euvbackendkotlin.model

import org.springframework.security.core.GrantedAuthority

class Permission : GrantedAuthority {

    var description: String? = null

    override fun getAuthority() = description!!

}