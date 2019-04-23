package user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@EnableConfigurationProperties
@EnableCaching
@Configuration
open class UserApplication {
}

fun main(args: Array<String>) {
	configureApplication(SpringApplicationBuilder()).run(*args)
}

fun configureApplication(builder: SpringApplicationBuilder): SpringApplicationBuilder {
	return builder.sources(UserApplication::class.java)
}
