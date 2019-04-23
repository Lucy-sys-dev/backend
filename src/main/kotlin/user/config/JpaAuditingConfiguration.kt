package user.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.util.*
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableTransactionManagement
open class JpaAuditingConfiguration {

//	@Bean
//	open fun auditorProvider(): AuditorAware<String> {
//		return AuditorAware {
//			if (SecurityContextHolder.getContext().authentication != null) {
//			val oauth2 = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
//			val loggedInUserId = oauth2.token.claims["sub"].toString()
////			Optional.of(loggedInUserId)
//				//val oauth2 = SecurityContextHolder.getContext().authentication
//				//val loggedInUserId = oauth2.principal as UserDetails
//				Optional.of(oauth2.name)
//			} else {
//				Optional.of("Unknown")
//			}
//		}
//	}

	@Bean
	open fun auditorProvider(): AuditorAware<String?> {
		return getCurrentAuditor()
	}

//	@Bean
//	fun dateTimeProvider(dateTimeService: DateTimeService): DateTimeProvider {
//		return AuditingDateTimeProvider(dateTimeService)
//	}

	@Override
	fun getCurrentAuditor(): AuditorAware<String?> {
		val authentication = SecurityContextHolder.getContext().authentication

		return AuditorAware {Optional.of(
			if (authentication == null || !authentication.isAuthenticated) {
				"null"
			} else (authentication.principal as User).getUsername()
		)}
	}
}

//private class CustomAuditorAware : AuditorAware<String> {
//	override fun getCurrentAuditor(): Optional<String> {
//		return if (SecurityContextHolder.getContext().authentication != null) {
////			val oauth2 = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken
////			val loggedInUserId = oauth2.token.claims["sub"].toString()
////			Optional.of(loggedInUserId)
//			val oauth2 = SecurityContextHolder.getContext().authentication
//			//val loggedInUserId = oauth2.principal as UserDetails
//			Optional.of(oauth2.name)
//
//		} else {
//			Optional.of("Unknown")
//		}
//	}
//}