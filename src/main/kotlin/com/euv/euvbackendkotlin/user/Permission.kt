package com.euv.euvbackendkotlin.user

import org.springframework.security.core.GrantedAuthority

class Permission(var desc: String? = null) : GrantedAuthority {

    override fun getAuthority() = desc!!

}