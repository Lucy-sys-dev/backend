package user.payload

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class ReceptStoreRequest {
	@NotBlank
	var storeName: String? = null

	@NotBlank
	var serviceType: String? = null

	@NotBlank
	var userName: String? = null

	@NotBlank
	var job: String? = null

	@NotBlank
	var mobile1: String? = null

	@NotBlank
	var mobile2: String? = null

	@NotBlank
	var mobile3: String? = null

	@NotBlank
	var city: String? = null

	@NotBlank
	var district: String? = null

	var phone1: String? = null
	var phone2: String? = null
	var phone3: String? = null

	var ceoName: String? = null
	var ceoMobile1: String? = null
	var ceoMobile2: String? = null
	var ceoMobile3: String? = null
}