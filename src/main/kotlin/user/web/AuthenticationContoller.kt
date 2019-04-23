package user.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import user.domain.B2BUser
import user.domain.B2BUserStore
import user.payload.LoginRequest
import user.repository.B2BUserRepository
import user.security.auth.JwtAuthenticationResponse
import user.security.token.JwtTokenFactory
import user.service.B2BUserService

@RestController
class AuthenticationContoller {
	private val log: Logger = LoggerFactory.getLogger(this.javaClass)

	@Autowired
	private lateinit var authenticationManager: AuthenticationManager

	@Autowired
	private lateinit var jwtTokenFactory: JwtTokenFactory

	@Autowired
	private lateinit var userDetailsService: UserDetailsService

	@Autowired
	private lateinit var b2BUserRepository: B2BUserRepository

	@Autowired
	private lateinit var b2BUserService: B2BUserService

	@PostMapping("/login",produces = [(MediaType.APPLICATION_JSON_VALUE)])
	fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<JwtAuthenticationResponse>? {

		try {
			val authentication = authenticationManager.authenticate(
				UsernamePasswordAuthenticationToken(
					loginRequest.username,
					loginRequest.password
				)
			)
			SecurityContextHolder.getContext().authentication = authentication

			//val userDetails = authentication.principal

			val userDetails = userDetailsService.loadUserByUsername(loginRequest.username)
			val token = jwtTokenFactory.generateToken(userDetails)

			loginRequest.password = BCryptPasswordEncoder().encode(loginRequest.password)
			//val userinfo = null;
			val userinfo = HashMap<String, String>()

			val b2bUser: B2BUser = b2BUserRepository.findById(loginRequest.username!!).get().apply {
				if ( role == "ADMIN" ) {
					userinfo.put("userid", id!!)
					userinfo.put("role", role!!)
					userinfo.put("username", name!!)
				} else {
					val userstore: B2BUserStore = b2BUserService.findB2BUserStoreByUserId(loginRequest.username)
					password = ""
					userinfo.put("id", userstore.b2BUserStorePK!!.userId!!)
					userinfo.put("username", userstore.user!!.name!!)
					userinfo.put("role", userstore.role!!)
					userinfo.put("storeid", userstore.store!!.id.toString())
					userinfo.put("storename", userstore.store!!.name.toString())
					userinfo.put("servicetype", userstore.store!!.serviceType!!)
				}
			}

			return ResponseEntity.ok(JwtAuthenticationResponse(token, userinfo))
		} catch (e: Exception) {
			log.error("[err] POST /login: {}", e.message, e)
			throw e
		}
	}
}