package de.tiengduc.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignUpRequest(
    @field:NotBlank(message = "Username cannot be blank")
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters--- controller layer")
    val username: String,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    val password: String,

    @NotBlank(message = "Password cannot be blank")
    val age: Number,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Your email address is not valid")
    val email: String
)