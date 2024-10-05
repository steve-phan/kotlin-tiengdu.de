package de.tiengduc.service

import de.tiengduc.dto.UserDto
import de.tiengduc.exception.UsernameAlreadyExistsException
import de.tiengduc.model.entity.User
import de.tiengduc.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this) // Initialize mocks
    }

    @Test
    fun `createUser should throw exception when username already exists`() {
        // Arrange
        val userDto = UserDto("testuser", "password", "test@example.com")
        `when`(userRepository.existsByUsername(userDto.username)).thenReturn(true)

        // Act & Assert
        val exception = assertThrows(UsernameAlreadyExistsException::class.java) {
            userService.createUser(userDto)
        }

        assertEquals("Username 'testuser' is already in use. Please choose another one.", exception.message)
    }

    @Test
    fun `createUser should create user when username does not exist`() {
        // Arrange
        val userDto = UserDto("testuser", "password", "test@example.com")
        val encodedPassword = "encodedPassword"
        val user = User(1L, userDto.username, encodedPassword, userDto.email)

        `when`(userRepository.existsByUsername(userDto.username)).thenReturn(false)
        `when`(passwordEncoder.encode(userDto.password)).thenReturn(encodedPassword)
        `when`(userRepository.save(any(User::class.java))).thenReturn(user)

        // Act
        val createdUser = userService.createUser(userDto)

        // Assert
        assertNotNull(createdUser)
        assertEquals(1L, createdUser.id)
        assertEquals("testuser", createdUser.username)
        assertEquals("encodedPassword", createdUser.password)
    }

 }