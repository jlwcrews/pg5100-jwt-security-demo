package no.jlwcrews.jwtdemo.models.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(
        name = "users_user_id_seq",
        allocationSize = 1
    )
    @Column(name = "user_id", nullable = false)
    val userId: Long? = null,

    @Column(name = "user_email")
    val email: String?,

    @Column(name = "user_created")
    val created: LocalDateTime = LocalDateTime.now(),

    @Column(name = "user_password")
    var password: String? = null,

    @Column(name = "user_enabled")
    val enabled: Boolean = true
) {
    @ManyToMany(fetch = FetchType.EAGER)
    val authorities: MutableList<AuthorityEntity> = mutableListOf()

    override fun toString(): String {
        return "UserEntity(userId=$userId, email=$email, password=$password, created=$created)"
    }

}