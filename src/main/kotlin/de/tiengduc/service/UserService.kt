package de.tiengduc.service

import de.tiengduc.dto.UserDto
import de.tiengduc.model.entity.User
import de.tiengduc.repository.UserRepository
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

    fun createUser(userDto: UserDto): User {
        val user = User(
            username = userDto.username,
            password = passwordEncoder.encode(userDto.password),
            email = userDto.email
        )
        return userRepository.save(user)
    }

    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun updateUser(id: Long, userDto: UserDto): User? {
        val existingUser = userRepository.findById(id).orElse(null) ?: return null

        existingUser.apply {
            username = userDto.username
            email = userDto.email
            password = passwordEncoder.encode(userDto.password)
        }

        return userRepository.save(existingUser)
    }

    fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}