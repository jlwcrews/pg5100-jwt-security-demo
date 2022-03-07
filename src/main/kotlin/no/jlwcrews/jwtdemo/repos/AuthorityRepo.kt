package no.jlwcrews.jwtdemo.repos

import no.jlwcrews.jwtdemo.models.entities.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepo: JpaRepository<AuthorityEntity, Long> {

    fun findByName(authority: String?): AuthorityEntity
}