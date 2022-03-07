package no.jlwcrews.jwtdemo.models.entities

import no.jlwcrews.jwtdemo.models.entities.AuthorityEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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