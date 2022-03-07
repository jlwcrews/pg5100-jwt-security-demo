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
    @Autowired private val authorityRepo: AuthorityRepo,
    @Autowired private val passwordEncoder: BCryptPasswordEncoder
    ): UserDetailsService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val user = userRepo.findByEmail(it)
            return User(user.email, user.password, user.authorities.map { authority -> SimpleGrantedAuthority(authority.name) })
        }
        throw UsernameNotFoundException("Error authenticating user")
    }

    fun registerUser(user: RegisterUserDTO): UserEntity {
        val newUser = UserEntity(email = user.email, password = BCryptPasswordEncoder().encode(user.password))
        logger.info("Registering new user ${newUser.email} with password ${newUser.password}")
        return userRepo.save(newUser)
    }

    fun createAuthority(authorityName: AuthorityEntity): AuthorityEntity {
        logger.info("Creating new authority")
        return authorityRepo.save(authorityName)
    }

    fun getUserByEmail(email: String): UserEntity? {
        logger.info("Fetching user")
        return userRepo.findByEmail(email)
    }

    fun grantAuthorityToUser(email: String?, authorityName: String?){
        val user = userRepo.findByEmail(email)
        val authority = authorityRepo.findByName(authorityName)
        logger.info("Authority ${authority.name} being granted to ${user.email}")
        user.authorities.add(authority)
    }

    fun getAuthorities(): List<String?>{
        logger.info("Retrieving all authorities")
        return authorityRepo.findAll().map { it.name }
    }

    fun getUsers(): List<UserEntity>{
        logger.info("Retrieving all users")
        return userRepo.findAll()
    }
}

data class RegisterUserDTO(val email: String, val password: String)