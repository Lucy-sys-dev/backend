package user.web

import org.intellij.lang.annotations.Language
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import user.domain.ReceptStore
import user.payload.ReceptStoreRequest
import user.repository.ReceptStoreRepository
import javax.validation.Valid
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import user.exception.ResourceAlreadyException
import user.exception.ResourceNotFoundException
import user.payload.ReceptStoreProfile
import user.payload.ReceptStoreUser
import user.service.ReceptStoreService
import java.net.URI
import java.util.*
import kotlin.collections.HashMap


@RestController
@RequestMapping("/api/recept")
class ReceptStoreController(
	private val receptStoreService : ReceptStoreService
) {
	@Autowired
	private val receptStoreRepository: ReceptStoreRepository? = null

	@GetMapping("/stores/name/{storename}")
	fun findByStoreName(@PathVariable(value = "storename") storename: String) : ReceptStoreProfile {
		var receptStore = receptStoreRepository!!.findByStoreName(storename)
			.orElseThrow { ResourceNotFoundException("ReceptStore", "store_name", storename) }

		return ReceptStoreProfile(receptStore.seq, receptStore.userName, receptStore.serviceType, receptStore.storeName,
			                      receptStore.job, receptStore.mobile1, receptStore.mobile2, receptStore.mobile3)
	}

	@GetMapping("/stores")
	fun findAllReceptStores() : ResponseEntity<List<HashMap<String, String>>> =
		ResponseEntity(receptStoreService.findAllReceptStores(), HttpStatus.OK)

	@PostMapping("/store", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun createReceptStore(@Valid @RequestBody receptStoreRequest: ReceptStoreRequest , bindingResult: BindingResult) : ResponseEntity<Any> {
		if(bindingResult.hasErrors())
			return ResponseEntity.badRequest().build()

		val result = receptStoreService.createReceptStore(receptStoreRequest)

		val uri = URI.create("/register/search")
		return ResponseEntity.created(uri).body(result)
	}

	@PostMapping("/stores/user")
	fun getReceptStoresByUser(@Valid @RequestBody receptStoreUser: ReceptStoreUser) : ResponseEntity<ReceptStore> =
		ResponseEntity(receptStoreService.findReceptStoresByUser(receptStoreUser), HttpStatus.OK)

	@GetMapping("/stores/confirm/{id}")
	fun findReceptStoresByStoreId(@PathVariable(value = "id") id: Long) : ResponseEntity<ReceptStore?> =
		ResponseEntity(receptStoreService.findReceptStoresByStoreId(id), HttpStatus.OK)

	@PutMapping("/stores/confirm/{seq}")
	fun confirmReceptStore(@PathVariable(value="seq") seq : Long) : ResponseEntity<ReceptStore?> {
		return ResponseEntity(receptStoreService.confirmStore(seq),HttpStatus.OK)
	}

	@DeleteMapping("/stores/{seq}")
	fun deleteReceptStore(@PathVariable(value = "seq") seq: Long) : ResponseEntity<Unit> =
		ResponseEntity(receptStoreService.deleteReceptStoreBySeq(seq), HttpStatus.NO_CONTENT)

	@ExceptionHandler
	fun resourceIsAlready(ex: ResourceAlreadyException): ResponseEntity<String>? =
		error(ex.message!!, HttpStatus.CONFLICT)

	@ExceptionHandler
	fun resourceIsNotFound(ex: ResourceNotFoundException): ResponseEntity<String>? =
		error(ex.message!!, HttpStatus.NOT_FOUND)

	@Language("JSON")
	private fun error(message: String, code: HttpStatus) =
		ResponseEntity.status(code)
			.body("{ \"error\": \"Registration Error\", \"message\": \"$message\" }")
}