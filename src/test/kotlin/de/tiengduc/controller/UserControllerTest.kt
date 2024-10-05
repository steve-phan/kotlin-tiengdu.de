package de.tiengduc.controller

import de.tiengduc.dto.UserDto
import de.tiengduc.model.entity.User
import de.tiengduc.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserController::class)
@WithMockUser
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `createUser should return user ID when successful`() {
        // Arrange
        val userDto = UserDto("testuser", "password", "test@example.com")
        val user = User(1L, userDto.username, "encodedPassword", userDto.email)
        Mockito.`when`(userService.createUser(userDto)).thenReturn(user)

        // Act & Assert
        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "username": "testuser",
                        "password": "password",
                        "email": "test@example.com"
                    }
                """.trimIndent()
                ) .with(csrf())
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1L))

    }

}