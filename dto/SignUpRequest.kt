package de.tiengduc.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank


data class SignUpRequest(
    @NotBlank
    val username: String,

    @NotBlank
    val password: String,

    @NotBlank
    @Email
    val email: String
)
