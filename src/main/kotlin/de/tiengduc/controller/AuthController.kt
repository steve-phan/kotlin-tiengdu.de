package de.tiengduc.controller

import de.tiengduc.dto.JwtAuthenticationResponse
import de.tiengduc.dto.LoginRequest
import de.tiengduc.dto.SignUpRequest
import de.tiengduc.security.JwtTokenProvider
import de.tiengduc.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userService: UserService,  
    private val passwordEncoder: PasswordEncoder
) {
    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<JwtAuthenticationResponse> {
         val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username,
                loginRequest.password
            )
        )
        println("auth  $authentication abc")
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtTokenProvider.generateToken(authentication)

        return ResponseEntity.ok(JwtAuthenticationResponse(jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<String> {
        if (userService.existsByUsername(signUpRequest.username)) {
            return ResponseEntity.badRequest().body("Username is already taken!")
        }
        val user = userService.createUser(signUpRequest)
        return ResponseEntity.ok("User registered successfully")
    }
}
