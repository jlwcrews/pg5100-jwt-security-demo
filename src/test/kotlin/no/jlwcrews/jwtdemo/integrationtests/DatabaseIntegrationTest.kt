package no.jlwcrews.jwtdemo.integrationtests

import no.jlwcrews.jwtdemo.service.RegisterUserDTO
import no.jlwcrews.jwtdemo.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserService::class)
class DatabaseIntegrationTest(@Autowired private val userService: UserService) {

    @Test
    fun createAndFindUserTest(){
        userService.registerUser(RegisterUserDTO("jim@jimbob.com", "pirate"))
        val createdUser = userService.loadUserByUsername("jim@jimbob.com")
        assert(createdUser.username == "jim@jimbob.com")
        assert(createdUser.authorities.first().authority == "USER")
    }
}