package de.tiengduc.model.entity

import de.tiengduc.model.enum.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank


@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    @get: NotBlank
    val username: String = "",

    @Column(nullable = false)
    @get: NotBlank
    val password: String = "",

    @Column(nullable = false, unique = true)
    @get: Email
    val email: String = "",

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: Set<Role> = setOf()
)
