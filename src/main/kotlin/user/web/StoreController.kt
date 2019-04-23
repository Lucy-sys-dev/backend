package user.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import user.common.controller.GeneralRestController
import user.domain.Store
import user.domain.StoreMemberRating
import user.domain.StoreSettingItem
import user.payload.StoreOptionsRequest
import user.service.StoreService
import java.net.URI
import javax.validation.Valid

/**
 * Create by lucy on 2019-02-18
 **/
@RestController
@RequestMapping("/api/stores")
class StoreController (
	@Autowired var storeService: StoreService
) : GeneralRestController() {
	@GetMapping("")
	fun findAllStores() : ResponseEntity<*> {
		return ResponseEntity(storeService.findAllStores(), HttpStatus.OK)
	}

	@GetMapping("/{storeId}")
	fun findStoresById(@PathVariable("storeId") storeId: Long) : ResponseEntity<Store?> {
		return ResponseEntity(storeService.findStoresById(storeId), HttpStatus.OK)
	}

	@GetMapping("/{storeId}/options/rating")
	fun findStoreRatingOptionById(@PathVariable("storeId") storeId: Long) : ResponseEntity<StoreMemberRating?> {
		return ResponseEntity(storeService.findStoreRatingOptionById(storeId), HttpStatus.OK)
	}

	@PostMapping("/options/rating" ,produces = arrayOf(MediaType.APPLICATION_JSON_VALUE), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun createStoreOptionMemberGrade(@Valid @RequestBody storeOptionsRequest: StoreOptionsRequest, bindingResult: BindingResult) : ResponseEntity<Any> {
		if(bindingResult.hasErrors())
			return ResponseEntity.badRequest().build()

		val result = storeService.createStoreOptionMemberGrade(storeOptionsRequest)
		val uri = URI.create("/api/stores/${storeOptionsRequest.storeId}")
		return ResponseEntity.created(uri).body(result)
	}

	@PostMapping("/options/schedule",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun createStoreOptionSchedule(@Valid @RequestBody storeOptionsRequest: StoreOptionsRequest, bindingResult: BindingResult) : ResponseEntity<Any> {
		if(bindingResult.hasErrors())
			return ResponseEntity.badRequest().build()

		val result = storeService.createStoreOptionSchedule(storeOptionsRequest)
		val uri = URI.create("/api/stores/${storeOptionsRequest.storeId}")
		return ResponseEntity.created(uri).body(result)
	}

	@PostMapping("/options/product" ,produces = arrayOf(MediaType.APPLICATION_JSON_VALUE), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun createStroeOptionProduct(@Valid @RequestBody storeOptionsRequest: StoreOptionsRequest, bindingResult: BindingResult) : ResponseEntity<Any> {
		if(bindingResult.hasErrors())
			return ResponseEntity.badRequest().build()

		val result = storeService.createStoreOptionSchedule(storeOptionsRequest)
		val uri = URI.create("/api/stores/${storeOptionsRequest.storeId}")
		return ResponseEntity.created(uri).body(result)

	}

	// fetch 스토어 설정 정보
	@GetMapping("/setting/items/{storeId}")
	fun findStoreSettingItemByStoreId(@PathVariable("storeId") storeId: Long) : ResponseEntity<*> {
		return ResponseEntity(storeService.findStoreSettingItemByStoreId(storeId), HttpStatus.OK)
	}



}