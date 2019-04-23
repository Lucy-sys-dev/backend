package user.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import user.exception.ResourceNotFoundException
import user.payload.UserProfile
import user.repository.UserRepository

@RestController
@RequestMapping("/api/user")
class UserController {
	@Autowired
	private val userRepository: UserRepository? = null

	@GetMapping("/{id}")
	fun getUserProfile(@PathVariable(value = "id") id: String) : UserProfile {
		var user = userRepository!!.findById(id)
			.orElseThrow { ResourceNotFoundException("User", "id", id) }

		return UserProfile(user.id, user.name, user.password, user.sex, user.role, user.role)
	}



}