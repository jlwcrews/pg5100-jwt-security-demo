package no.jlwcrews.jwtdemo.security.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.jlwcrews.jwtdemo.security.jwt.JwtUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter(@Autowired private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(AUTHORIZATION)
        when {
            authorizationHeader.isNullOrBlank() -> filterChain.doFilter(request, response)
            !authorizationHeader.startsWith("Bearer ") -> filterChain.doFilter(request, response)
            request.servletPath.contains("/api/login") -> filterChain.doFilter(request, response)
            else -> {
                try {
                    val token = authorizationHeader.substring(7)
                    val decodedToken = jwtUtil.decodeToken(token)
                    val email = decodedToken.subject
                    val authority =
                        decodedToken.getClaim("authorities").asList(String::class.java).map { SimpleGrantedAuthority(it) }
                    val authenticationToken = UsernamePasswordAuthenticationToken(email, null, authority)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch (e: Exception) {
                    logger.error("Authorization error ${e.message}")
                    val error = mapOf("error_message" to e.message)
                    response.contentType = APPLICATION_JSON_VALUE
                    response.status = FORBIDDEN.value()
                    jacksonObjectMapper().writeValue(response.outputStream, error)
                }
            }
        }
    }
}