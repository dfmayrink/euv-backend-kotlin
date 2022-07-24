package com.euv.euvbackendkotlin.user

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document
class User : UserDetails {

    @Id
    var id: ObjectId = ObjectId()

    private var username = ""
    private var password = ""
    var accountNonExpired: Boolean? = true
    var accountNonLocked: Boolean? = true
    var credentialsNonExpired: Boolean? = true
    var enabled: Boolean? = true
    var permissions: MutableCollection<Permission>? = ArrayList()

    val roles: List<String?>
        get() {
            val roles: MutableList<String?> = ArrayList()
            for (permission in permissions!!) {
                roles.add(permission.desc)
            }
            return roles
        }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return permissions!!
    }

    override fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired!!
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired!!
    }

    override fun isEnabled(): Boolean {
        return enabled!!
    }

}