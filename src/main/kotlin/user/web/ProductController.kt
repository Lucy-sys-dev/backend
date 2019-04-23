package user.web

import org.intellij.lang.annotations.Language
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import user.common.controller.GeneralRestController
import user.common.model.ResultData
import user.common.template.ServiceMethodTemplate
import user.domain.ServiceProduct
import user.exception.ResourceAlreadyException
import user.exception.ResourceNotFoundException
import user.payload.ReceptStoreRequest
import user.payload.ServiceProductRequest
import user.service.ProductService
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/api/product")
class ProductController (
	@Autowired var productService: ProductService
) {
	@GetMapping("/style/category")
	fun getStyleCategory() =
		ResponseEntity(productService.getStyleCategory(), HttpStatus.OK)

	@GetMapping("/services")
	fun findAllProducts() : ResponseEntity<*> {
		return ResponseEntity(productService.findAll(), HttpStatus.OK)
	}

	@GetMapping("/services/{storeId}")
	fun getServiceProduct(@PathVariable("storeId") storeId: Long) : ResponseEntity<*> {
		return ResponseEntity(productService.findProductServiceByStoreId(storeId), HttpStatus.OK)
	}

	@PostMapping("/service", produces = arrayOf(APPLICATION_JSON_VALUE), consumes = arrayOf(APPLICATION_JSON_VALUE))
	fun addServiceProduct(@Valid @RequestBody serviceProductRequest: ServiceProductRequest, bindingResult: BindingResult) : ResponseEntity<Any> {
//		return serviceCall(object : ServiceMethodTemplate {
//			override fun call(): ResultData {
//				return productService.createServiceProduct(serviceProductRequest)
//			}
//		})
		if(bindingResult.hasErrors())
			return ResponseEntity.badRequest().build()

		val result = productService.createServiceProduct(serviceProductRequest)
		val uri = URI.create("/services/${serviceProductRequest.storeId}")
		return ResponseEntity.created(uri).body(result)
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