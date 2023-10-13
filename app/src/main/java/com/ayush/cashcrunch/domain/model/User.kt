package com.ayush.cashcrunch.domain.model

data class User(
    val userId: String,
    val name: String,
    val email: String,
    val password: String,
    val photo: String = ""
)
