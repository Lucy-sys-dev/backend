package user.security.auth

import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import user.security.config.WebConfig
import user.security.token.JwtAuthenticationToken
import user.security.token.RawAccessJwtToken
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//class JwtAuthenticationTokenFilter(
//) : OncePerRequestFilter() {
//	private val log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter::class.java)
//
//	override fun doFilterInternal(req: HttpServletRequest,
//	                              res: HttpServletResponse,
//	                              chain: FilterChain) {
//		val auth = SecurityContextHolder.getContext().authentication
//		val userName = auth.principal as String
//		val user = userDetailsService.loadUserByUsername(userName) as UserDetailsEx
//
//		log.info("user: ${user.name}")
//
//		chain.doFilter(req, res)
//	}
//}

class JwtAuthenticationTokenFilter(matcher: RequestMatcher)
	: AbstractAuthenticationProcessingFilter(matcher) {

	override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

		val tokenHeader: String? = request.getHeader(WebConfig.JWT_TOKEN_HEADER_PARAM)

		if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
			throw AuthenticationServiceException("No JWT token found in request headers")
		}

		val authToken = tokenHeader.substring(7)
		val token = RawAccessJwtToken(authToken)
		return authenticationManager.authenticate(JwtAuthenticationToken(token))
	}

	override fun successfulAuthentication(
		request: HttpServletRequest,
		response: HttpServletResponse,
		chain: FilterChain,
		authResult: Authentication
	) {
		val context = SecurityContextHolder.createEmptyContext()
		context.authentication = authResult
		SecurityContextHolder.setContext(context)
		chain.doFilter(request, response)
	}
}