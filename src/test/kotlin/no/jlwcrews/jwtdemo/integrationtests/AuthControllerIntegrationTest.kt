package no.jlwcrews.jwtdemo.integrationtests

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application.integrationtest.yml"])
@Transactional
class AuthControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testRegisterEndpoint() {
        mockMvc.post("/api/register") {
            contentType = APPLICATION_JSON
            content = "{\"email\":\"jim@bob.com\", \"password\":\"pirate\"}"
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
    }

    @Disabled
    @Test
    fun testLoginEndpoint() {

        mockMvc.post("/api/login") {
            contentType = APPLICATION_JSON
            content = "{\"username\":\"jim@bob.com\", \"password\":\"pirate\"}"
        }
            .andExpect { status { isOk() } }
            .andExpect { cookie { exists("access_token") } }

    }

    @Disabled
    @Test
    fun testLoginEndpointWithWrongPasswordShouldFail() {

        mockMvc.post("/api/login") {
            contentType = APPLICATION_JSON
            content = "{\"username\":\"jim@bob.com\", \"password\":\"popsicle\"}"
        }
            .andExpect { status { isUnauthorized() } }
    }

    @Test
    fun testGetAuthorities(){
        mockMvc.get("/api/authority/all") {
        }
            .andExpect { status { isOk() } }
    }
}