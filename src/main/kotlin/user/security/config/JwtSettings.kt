package user.security.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "user.security.jwt")
open class JwtSettings {
	open var tokenExpirationTime: Int? = null
	open var tokenIssuer: String? = null
	open var tokenSigningKey: String? = null
}