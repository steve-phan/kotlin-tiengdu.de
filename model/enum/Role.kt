package de.tiengduc.model.enum



import jakarta.persistence.*


@Entity
@Table(name = "roles")
data class Role(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val name: RoleName = RoleName.ROLE_USER
)


enum class RoleName {
    ROLE_USER,
    ROLE_MODERATOR,
    ROLE_ADMIN,
}
