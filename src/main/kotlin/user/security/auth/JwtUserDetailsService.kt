package user.security.auth

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import user.repository.B2BUserRepository

@Service
class JwtUserDetailsService (
	@Autowired private val b2bUserRepository: B2BUserRepository
) : UserDetailsService {
	private var log = LoggerFactory.getLogger(UserDetailsService::class.java)

	override fun loadUserByUsername(userid: String): User {
		val user = b2bUserRepository!!.findById(userid).get()
		return User(
			user.id,
			user.password,
			listOf(SimpleGrantedAuthority(user.role)))
	}
}