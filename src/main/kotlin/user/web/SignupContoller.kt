package user.web

import org.intellij.lang.annotations.Language
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import user.domain.B2BUser
import user.domain.ReceptStore
import user.exception.ResourceAlreadyException
import user.payload.ReceptStoreRequest
import user.repository.B2BUserRepository
import user.repository.ReceptStoreRepository
import javax.validation.Valid

@RestController
@RequestMapping("/api/register")
open class SignupContoller(
	@Autowired private val cacheManager: CacheManager,
	@Autowired internal var b2BUserRepository: B2BUserRepository
) {
	private val log: Logger = LoggerFactory.getLogger(this.javaClass)

	@Autowired
	private val receptStoreRepository: ReceptStoreRepository? = null

//	@PostMapping("/recept/store")
//	fun receptStore(@Valid @RequestBody receptStore: ReceptStore) : ResponseEntity<*> {
//		val receptStore = ReceptStore(
//			receptStore.userName!!, receptStore.serviceType!!, receptStore.storeName!!,
//			receptStore.job!!, receptStore.mobile1!!, receptStore.mobile2!!,receptStore.mobile3!!
//		)
//
//		val result4 = receptStoreRepository!!.save(receptStore)
//
//		val location = ServletUriComponentsBuilder
//			.fromCurrentRequest().path("/api/recept/store/{store_name}")
//			.buildAndExpand(result.storeName).toUri()
//
//		return ResponseEntity.created(location).build<Any>()
//
//	}

	@PostMapping("/b2b", produces = ["application/json"])
	fun signup(@RequestBody user: B2BUser) =
		checkIfUsernameOrEmailIsAlreadyUsed(user).let { newUser ->
			ResponseEntity(b2BUserRepository!!.save(
				newUser.apply {
					password = BCryptPasswordEncoder().encode(newUser.password)
					log.info("encode password ["+ password+"]")
				}
			).also {
				clearCache()
			}.copy(password = ""), HttpStatus.CREATED)
		}

	@ExceptionHandler
	fun usernameIsAlreadyUsed(ex: ResourceAlreadyException) =
		error(ex.message!!)

//	@ExceptionHandler
//	fun emailIsAlreadyUsed(ex: ResourceAlreadyException) =
//		error(ex.message!!)

	@Language("JSON")
	private fun error(message: String) =
		ResponseEntity.status(HttpStatus.CONFLICT)
			.body("{ \"error\": \"Registration Error\", \"message\": \"$message\" }")

	private fun clearCache() {
		cacheManager.cacheNames.parallelStream().forEach {
			cacheManager.getCache(it).clear()
		}
	}

	private fun checkIfUsernameOrEmailIsAlreadyUsed(user: B2BUser) =
		when {
			b2BUserRepository!!.findById(user.id!!).isPresent -> throw ResourceAlreadyException("userId", "b2buser")
			//repository.findByEmail(user.email).isPresent -> throw EmailIsAlreadyUsedException()
			else -> user
		}

}