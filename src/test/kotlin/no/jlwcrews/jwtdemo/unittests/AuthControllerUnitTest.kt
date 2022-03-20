package no.jlwcrews.jwtdemo.unittests

import io.mockk.every
import io.mockk.mockk
import no.jlwcrews.jwtdemo.models.entities.UserEntity
import no.jlwcrews.jwtdemo.service.RegisterUserDTO
import no.jlwcrews.jwtdemo.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@WebMvcTest
class AuthControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun userService() = mockk<UserService>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun testRegisterEndpoint(){
        every { userService.registerUser(RegisterUserDTO("jim@bob.com", "pirate")) } answers {
            val user = UserEntity(userId = 1, email = "jim@bob.com", password = BCryptPasswordEncoder().encode("pirate"))
            user
        }

        mockMvc.post("/api/register"){
            contentType = APPLICATION_JSON
            content = "{\"email\":\"jim@bob.com\", \"password\":\"pirate\"}"
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
    }

    @Test
    fun testLoginEndpoint(){
        val encryptedPassword = BCryptPasswordEncoder().encode("pirate")
        every { userService.loadUserByUsername("jim@bob.com") } answers {
            User("jim@bob.com", encryptedPassword, listOf(SimpleGrantedAuthority("ADMIN")))
        }

        mockMvc.post("/api/login"){
            contentType = APPLICATION_JSON
            content = "{\"username\":\"jim@bob.com\", \"password\":\"pirate\"}"
        }
            .andExpect { status { isOk() } }
            .andExpect { cookie { exists("access_token")} }

    }

    @Test
    fun testLoginEndpointWithWrongPasswordShouldFail(){
        val encryptedPassword = BCryptPasswordEncoder().encode("pirate")
        every { userService.loadUserByUsername("jim@bob.com") } answers {
            User("jim@bob.com", encryptedPassword, listOf(SimpleGrantedAuthority("ADMIN")))
        }

        mockMvc.post("/api/login"){
            contentType = APPLICATION_JSON
            content = "{\"username\":\"jim@bob.com\", \"password\":\"popsicle\"}"
        }
            .andExpect { status { isUnauthorized() } }
    }
}