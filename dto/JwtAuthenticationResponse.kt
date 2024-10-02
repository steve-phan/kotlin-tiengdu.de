package de.tiengduc.dto

data class JwtAuthenticationResponse(
    val accessToken: String,
    val tokenType: String = "Bearer"
)
