package no.jlwcrews.jwtdemo.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*

object JwtUtil {

    private val SECRET = "do_not_store_secrets_in_the_source_code"

    private val algorithm = Algorithm.HMAC256(SECRET)

    fun createToken(user: User, issuer: String): String {
        return JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .withIssuer(issuer)
            .withClaim("authorities", user.authorities.map { it.authority })
            .sign(algorithm)
    }

    fun decodeToken(token: String): DecodedJWT {
        val jwtVerifier = JWT.require(algorithm).build()
        return jwtVerifier.verify(token)
    }
}