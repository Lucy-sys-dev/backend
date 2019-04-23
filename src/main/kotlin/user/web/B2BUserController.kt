package user.web

import org.intellij.lang.annotations.Language
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import user.domain.B2BUser
import user.domain.B2BUserStore
import user.exception.ResourceAlreadyException
import user.exception.ResourceNotFoundException
import user.payload.B2BCeoRequest
import user.payload.B2BEmployeeRequest
import user.payload.EmployeeRequest
import user.service.B2BUserService
import java.net.URI

@RestController
@RequestMapping("/api/b2b")
class B2BUserController(
	private val b2BUserService: B2BUserService
) {
	private val log: Logger = LoggerFactory.getLogger(this.javaClass)

	@GetMapping
	fun findAllB2BUsers(): ResponseEntity<List<B2BUser>> =
		ResponseEntity(b2BUserService.findAllB2BUsers(), HttpStatus.OK)

	@GetMapping("/{id}")
	fun findB2BUserById(@PathVariable("id") id: String) : ResponseEntity<B2BUser?> {
		return ResponseEntity(b2BUserService.findB2BUserById(id), HttpStatus.OK)
//		return if (user != null) ResponseEntity(user, HttpStatus.OK)
//		else ResponseEntity(HttpStatus.NOT_FOUND)
	}

	@GetMapping("/check/{id}")
	fun checkB2BUserId(@PathVariable("id") id: String) : ResponseEntity<*> {
		return if (b2BUserService.findB2BUserById(id) != null) ResponseEntity(id, HttpStatus.CONFLICT)
		else ResponseEntity(id, HttpStatus.OK)
	}

	@PostMapping("/ceo", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun createCeo(@RequestBody data: B2BCeoRequest, bindingResult: BindingResult) : ResponseEntity<Any> {
		if(bindingResult.hasErrors())
			return ResponseEntity.badRequest().build()
		val result = b2BUserService.createCeo(data)
		return ResponseEntity.ok().body(HttpStatus.CREATED)

	}

	@PostMapping("/employee")
	fun createEmployee(@RequestBody user: B2BEmployeeRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<HttpStatus> {
	val newUser = b2BUserService.createEmployee(user)
	val headers = HttpHeaders()
	headers.location = uriBuilder.path("/api/users/b2b/{id}").buildAndExpand(newUser.id).toUri()
		return ResponseEntity(headers, HttpStatus.CREATED)
	}

	@GetMapping("/{storeId}/employee")
	fun findB2BUserEmployeeByStoreId(@PathVariable("storeId") storeId: Long) : ResponseEntity<*> {
		return ResponseEntity(b2BUserService.findB2BUserEmployeeByStoreId(storeId), HttpStatus.OK)
	}

	@GetMapping("/{storeId}/{userId}")
	fun findB2BUserStoreByStoreIdAndUserId(@PathVariable("storeId") storeId: Long, @PathVariable("userId") userId: String) : ResponseEntity<B2BUserStore?> {
		return ResponseEntity(b2BUserService.findB2BUserStoreByStoreIdAndUserId(storeId, userId), HttpStatus.OK)
//		return if (user != null) ResponseEntity(user, HttpStatus.OK)
//		else ResponseEntity(HttpStatus.NOT_FOUND)
	}

	@PutMapping("/employee/confirm")
	fun confirmEmployeeStoreId(@RequestBody employeeRequest: EmployeeRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<Unit> {
		b2BUserService.confirmEmployeeStoreId(employeeRequest)
		val headers = HttpHeaders()
		headers.location = uriBuilder.path("/api/b2b/{id}/employee").buildAndExpand(employeeRequest.storeId).toUri()
		return ResponseEntity(headers, HttpStatus.OK)
	}

	@PutMapping("/employee/delete")
	fun deleteEmployeeStoreId(@RequestBody employeeRequest: EmployeeRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<Unit> {
		b2BUserService.deleteEmployeeStoreId(employeeRequest)
		val headers = HttpHeaders()
		headers.location = uriBuilder.path("/api/b2b/{id}/employee").buildAndExpand(employeeRequest.storeId).toUri()
		return ResponseEntity(headers, HttpStatus.OK)
	}

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