package user.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import user.payload.CoreCodeRequest
import user.repository.CoreCodeRepository
import user.service.CoreDataService
import java.net.URI
import javax.validation.Valid

/**
 * Create by lucy on 2019-02-17
 **/
@RestController
@RequestMapping("/api/cores")
class CoreDataController(
	@Autowired var coreDataService: CoreDataService

) {
	@GetMapping("/area/cities")
	fun getCities() =
		ResponseEntity(coreDataService.getCities(), HttpStatus.OK)

	@GetMapping("/area/city/{city}")
	fun getGusByCity(@PathVariable("city") city: String)  =
		ResponseEntity(coreDataService.getGusByCity(city), HttpStatus.OK)

	@GetMapping("/banks")
	fun getBanks() =
		ResponseEntity(coreDataService.getBanks(), HttpStatus.OK)

	@GetMapping("")
	fun getCoreCodes() =
		ResponseEntity(coreDataService.findAll(), HttpStatus.OK)

	@PostMapping("",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun addCoreCode(@Valid @RequestBody coreCodeRequest: CoreCodeRequest, bindingResult: BindingResult) : ResponseEntity<Any> {
		if(bindingResult.hasErrors())
			return ResponseEntity.badRequest().build()

		val result = coreDataService.createCoreCode(coreCodeRequest)
		val uri = URI.create("/cores")
		return ResponseEntity.created(uri).body(result)
	}

	@GetMapping("/regions")
	fun getRegions() =
		ResponseEntity(coreDataService.findReginons(), HttpStatus.OK)

	@GetMapping("/setting/items/{service}")
	fun findSettingItem(@PathVariable("service") service: String) : ResponseEntity<*> {
		return ResponseEntity(coreDataService.findSettingItemByServiceType(service), HttpStatus.OK)

	}


}