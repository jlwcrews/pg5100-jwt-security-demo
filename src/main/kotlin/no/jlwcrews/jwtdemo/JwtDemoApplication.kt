package no.jlwcrews.jwtdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class JwtDemoApplication {

	@Bean
	fun passwordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}
}

fun main(args: Array<String>) {
	runApplication<JwtDemoApplication>(*args)
}




