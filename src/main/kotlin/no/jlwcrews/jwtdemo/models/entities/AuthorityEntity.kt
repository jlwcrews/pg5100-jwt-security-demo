package no.jlwcrews.jwtdemo.models.entities

import javax.persistence.*

@Entity
@Table(name = "authorities")
class AuthorityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "authority_id")
    val id: Long? = null,

    @Column(name = "authority_name")
    val name: String?
) {
    override fun toString(): String {
        return "AuthorityEntity(id=$id, name=$name)"
    }
}