package de.tiengduc.repository

import de.tiengduc.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    abstract fun findByUsername(username: String): Nothing?
}