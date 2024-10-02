package de.tiengduc.repository


import de.tiengduc.model.enum.Role
import de.tiengduc.model.enum.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(roleName: RoleName): Role?
}
