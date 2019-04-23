package user.security.config

import com.google.common.collect.ImmutableList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.access.channel.ChannelProcessingFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import user.security.auth.JwtAuthenticationProvider
import user.security.auth.JwtAuthenticationTokenFilter
import java.util.*

//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//open class WebConfig(
//		val bCryptPasswordEncoder: BCryptPasswordEncoder,
//		val authenticationProvider : JwtAuthenticationProvider
//) : WebSecurityConfigurerAdapter() {
//
//	@Bean
//	public override fun authenticationManager(): AuthenticationManager {
//		val daoAuthenticationProvider = DaoAuthenticationProvider()
//		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
//		daoAuthenticationProvider.setUserDetailsService(userDetailsService)
//
//		return ProviderManager(listOf(authenticationProvider, daoAuthenticationProvider))
//	}
//
//	@Bean
//	open fun authenticationTokenFilterBean(): JwtAuthenticationTokenFilter {
//		val filter = JwtAuthenticationTokenFilter(AntPathRequestMatcher(TOKEN_BASED_AUTH_ENTRY_POINT))
//		filter.setAuthenticationManager(authenticationManager())
//		return filter
//	}
//
//	override fun configure(http: HttpSecurity) {
//		http
//				.cors().and()
//				.csrf().disable()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no sessions
//				.and()
//				.authorizeRequests()
//				.antMatchers("/api/**").permitAll()
//				.antMatchers("/error/**").permitAll()
//				.antMatchers(HttpMethod.POST, "/login").permitAll()
//				.anyRequest().authenticated()
//				.and()
//				.addFilter(JWTAuthenticationFilter(authenticationManager()))
//				.addFilter(JWTAuthorizationFilter(authenticationManager()))
//	}
//
//	@Throws(Exception::class)
//	override fun configure(auth: AuthenticationManagerBuilder) {
//		auth.userDetailsService(userDetailsService)
//				.passwordEncoder(bCryptPasswordEncoder)
//	}
//
//	@Bean
//	open fun authProvider(): DaoAuthenticationProvider {
//		val authProvider = DaoAuthenticationProvider()
//		authProvider.setUserDetailsService(userDetailsService)
//		authProvider.setPasswordEncoder(bCryptPasswordEncoder)
//		return authProvider
//	}
//
//	@Bean
//	open fun passwordEncoder(): PasswordEncoder {
//		return BCryptPasswordEncoder()
//	}
//
//	companion object {
//		const val JWT_TOKEN_HEADER_PARAM = "X-Authorization"
//		const val LOGIN_ENDPOINT = "/login"
//		const val SIGNUP_ENDPOINT = "/signup"
//		const val API_USERS_ENTRY_POINT = "/api/users"
//		const val TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**"
//		const val ADMIN_ROLE = "ADMIN"
//	}
//}

@Configuration
@EnableWebSecurity
open class WebConfig : WebSecurityConfigurerAdapter() {

	@Autowired
	private lateinit var userDetailsService: UserDetailsService

	@Autowired
	private lateinit var authenticationProvider: JwtAuthenticationProvider

	@Bean
	public override fun authenticationManager(): AuthenticationManager {
		val daoAuthenticationProvider = DaoAuthenticationProvider()
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
		daoAuthenticationProvider.setUserDetailsService(userDetailsService)

		return ProviderManager(listOf(authenticationProvider, daoAuthenticationProvider))
	}

	@Bean
	open fun authenticationTokenFilterBean(): JwtAuthenticationTokenFilter {
		val filter = JwtAuthenticationTokenFilter(AntPathRequestMatcher(TOKEN_BASED_AUTH_ENTRY_POINT))
		filter.setAuthenticationManager(authenticationManager())
		return filter
	}

	override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
		authenticationManagerBuilder
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder())
	}

	@Bean
	open fun passwordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}

	override fun configure(httpSecurity: HttpSecurity) {
		httpSecurity
			.cors()
		.and()
			.csrf().disable()
			.exceptionHandling()
		.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests()
			.antMatchers(LOGIN_ENDPOINT).permitAll()
			.antMatchers(API_USERS_ENTRY_POINT2).permitAll()

			.antMatchers(API_USERS_ENTRY_POINT1).hasAnyRole(ADMIN_ROLE)
			.antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated()
			.antMatchers(HttpMethod.POST,"/api/register/**").permitAll()
			.antMatchers(HttpMethod.POST,"/api/recept/store").permitAll()

		addCustomFilters(httpSecurity)
		disablePageCaching(httpSecurity)
	}

	private fun addCustomFilters(httpSecurity: HttpSecurity) {
		httpSecurity
			.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter::class.java)
	}

	private fun disablePageCaching(httpSecurity: HttpSecurity) {
		httpSecurity.headers().cacheControl()
	}

	companion object {
		const val JWT_TOKEN_HEADER_PARAM = "X-Authorization"
		const val LOGIN_ENDPOINT = "/login"
		const val SIGNUP_ENDPOINT = "/api/register/**"
		const val API_USERS_ENTRY_POINT1 = "/api/users"
		const val API_USERS_ENTRY_POINT2 = "/api/recept/store"
		const val TOKEN_BASED_AUTH_ENTRY_POINT = "/api/aaa/**"
		const val ADMIN_ROLE = "ADMIN"
		const val CEO_ROLE = "CEO"
		const val EMPLOYEE_ROLE = "EMPLOYEE"
		const val USER_ROLE = "USER"
	}
}