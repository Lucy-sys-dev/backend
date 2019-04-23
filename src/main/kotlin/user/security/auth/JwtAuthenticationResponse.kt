package user.security.auth

import user.domain.B2BUser
import user.domain.B2BUserStore

data class JwtAuthenticationResponse(val token: String, val userinfo: HashMap<String, String>)