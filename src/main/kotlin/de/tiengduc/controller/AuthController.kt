package de.tiengduc.controller


import de.tiengduc.dto.LoginRequest
import de.tiengduc.security.JwtTokenProvider
import de.tiengduc.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
     private val jwtTokenProvider: JwtTokenProvider,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {
    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<String> {

        return ResponseEntity.ok("JwtAuthenticationResponse(jwt)")
    }
}