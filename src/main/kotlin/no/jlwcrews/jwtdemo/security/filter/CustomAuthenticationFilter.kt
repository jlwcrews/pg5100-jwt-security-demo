package no.jlwcrews.jwtdemo.security.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFilter(@Autowired private val authManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter() {

    private val SECRET = "do_not_put_secrets_in_source_code"
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val user = request?.getParameter("username")
        val password = request?.getParameter("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(user, password)
        return authManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authentication: Authentication?
    ) {
        val user: User = authentication?.principal as User
        val algorithm = Algorithm.HMAC256(SECRET)
        val accessToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .withIssuer(request?.requestURL.toString())
            .withClaim("authorities", user.authorities.map { it.authority })
            .sign(algorithm)
        val refreshToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 300 * 60 * 1000))
            .withIssuer(request?.requestURL.toString())
            .sign(algorithm)
        val tokens = mapOf("access_token" to accessToken, "refresh_token" to refreshToken)
        response?.contentType = APPLICATION_JSON_VALUE
        response?.addCookie(Cookie("access_token", accessToken))
        jacksonObjectMapper().writeValue(response?.outputStream, tokens)
    }
}