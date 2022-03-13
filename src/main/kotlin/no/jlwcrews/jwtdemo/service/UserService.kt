package no.jlwcrews.jwtdemo.service

import no.jlwcrews.jwtdemo.models.entities.AuthorityEntity
import no.jlwcrews.jwtdemo.models.entities.UserEntity
import no.jlwcrews.jwtdemo.repos.AuthorityRepo
import no.jlwcrews.jwtdemo.repos.UserRepo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    @Autowired private val userRepo: UserRepo,
    @Autowired private val authorityRepo: AuthorityRepo
    ): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val user = userRepo.findByEmail(it)
            return User(user.email, user.password, user.authorities.map { authority -> SimpleGrantedAuthority(authority.name) })
        }
        throw UsernameNotFoundException("Error authenticating user")
    }

    fun registerUser(user: RegisterUserDTO): UserEntity {
        val newUser = UserEntity(email = user.email, password = BCryptPasswordEncoder().encode(user.password))
        return userRepo.save(newUser)
    }

    fun createAuthority(authorityName: AuthorityEntity): AuthorityEntity {
        return authorityRepo.save(authorityName)
    }

    fun grantAuthorityToUser(email: String?, authorityName: String?){
        val user = userRepo.findByEmail(email)
        val authority = authorityRepo.findByName(authorityName)
        user.authorities.add(authority)
        userRepo.save(user)
    }

    fun getAuthorities(): List<String?>{
        return authorityRepo.findAll().map { it.name }
    }

    fun getUsers(): List<UserEntity>{
        return userRepo.findAll()
    }
}

data class RegisterUserDTO(val email: String, val password: String)