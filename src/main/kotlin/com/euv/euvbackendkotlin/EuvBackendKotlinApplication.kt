package com.euv.euvbackendkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class EuvBackendKotlinApplication

fun main(args: Array<String>) {
	runApplication<EuvBackendKotlinApplication>(*args)

	val encoders: MutableMap<String, PasswordEncoder> = HashMap()
	encoders["bcrypt"] = BCryptPasswordEncoder()
	val passwordEncoder = DelegatingPasswordEncoder("bcrypt", encoders)
	passwordEncoder.setDefaultPasswordEncoderForMatches(BCryptPasswordEncoder())

	val result = passwordEncoder.encode("user")
	println("My hash $result")
}
