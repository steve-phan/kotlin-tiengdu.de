package de.tiengduc.model.entity

import de.tiengduc.model.enum.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    @get: NotBlank
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters --- service layer")
    var username: String,

    @Column(nullable = false)
    @get: NotBlank
    var password: String,

    @Column(nullable = false, unique = true)
    @get: Email(message = "Your email address is not valid- entityyyy")
    var email: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: Set<Role> = setOf()
)