package no.jlwcrews.jwtdemo

import no.jlwcrews.jwtdemo.models.entities.AuthorityEntity
import no.jlwcrews.jwtdemo.models.entities.UserEntity
import no.jlwcrews.jwtdemo.service.RegisterUserDTO
import no.jlwcrews.jwtdemo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class JwtDemoApplication {

/*	@Bean
	fun init(@Autowired userService: UserService) = CommandLineRunner {
		val jimbob = RegisterUserDTO(email = "jim@bob.com", password = "pirate")
		userService.registerUser(jimbob)
		val joebob = RegisterUserDTO(email = "joe@bob.com", password = "pirate")
		userService.registerUser(joebob)
		val userAuthority = userService.createAuthority(AuthorityEntity(name = "USER"))
		val adminAuthority = userService.createAuthority(AuthorityEntity(name = "ADMIN"))
		userService.grantAuthorityToUser(jimbob.email, userAuthority.name)
		userService.grantAuthorityToUser(joebob.email, adminAuthority.name)
	}*/

	@Bean
	fun passwordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}
}

fun main(args: Array<String>) {
	runApplication<JwtDemoApplication>(*args)
}




