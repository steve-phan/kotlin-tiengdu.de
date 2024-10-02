package de.tiengduc.service

import de.tiengduc.repository.UserRepository
import de.tiengduc.dto.SignUpRequest
import de.tiengduc.model.entity.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun existsByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    fun createUser(signUpRequest: SignUpRequest): User {
        val user = User(
            username = signUpRequest.username,
            password = passwordEncoder.encode(signUpRequest.password),
            email = signUpRequest.email
        )
        return userRepository.save(user)
    }
}
