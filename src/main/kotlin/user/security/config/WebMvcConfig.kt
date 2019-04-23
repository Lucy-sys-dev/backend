package user.security.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Create by lucy on 2019-01-17
 **/
@Configuration
open class WebMvcConfig : WebMvcConfigurer {
	private val MAX_AGE_SECS: Long = 3600

	@Override
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
			.maxAge(MAX_AGE_SECS)
//		super.addCorsMappings(registry)
	}

}