package com.euv.euvbackendkotlin.security

import org.springframework.security.core.GrantedAuthority

class Permission : GrantedAuthority {

    var description: String? = null

    override fun getAuthority() = description!!

}