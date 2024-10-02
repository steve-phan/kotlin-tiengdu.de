package de.tiengduc.dto

import jakarta.validation.constraints.NotBlank


data class LoginRequest(
    @NotBlank
    val username: String,

    @NotBlank
    val password: String
)
