package no.jlwcrews.jwtdemo.repos

import no.jlwcrews.jwtdemo.models.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String?): UserEntity
}