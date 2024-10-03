package de.tiengduc.security


import de.tiengduc.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

     override fun loadUserByUsername(username: String): UserDetails {
        println("userName $username")
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
        println("found userName $user")
        return UserPrincipal.create(user)
    }
}
