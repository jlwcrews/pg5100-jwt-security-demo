package no.jlwcrews.jwtdemo.models.entities

import javax.persistence.*

@Entity
@Table(name = "authorities")
class AuthorityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_authority_id_seq")
    @SequenceGenerator(
        name = "authorities_authority_id_seq",
        allocationSize = 1
    )
    @Column(name = "authority_id")
    val id: Long? = null,

    @Column(name = "authority_name")
    val name: String?
) {
    override fun toString(): String {
        return "AuthorityEntity(id=$id, name=$name)"
    }
}