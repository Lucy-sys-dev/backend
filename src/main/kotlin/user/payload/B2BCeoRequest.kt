package user.payload

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-03-18
 **/
data class B2BCeoRequest (
	@NotBlank
	var storeId: Long,

	@NotBlank
	var storeName: String,

	var phone1: String? = null,
	var phone2: String? = null,
	var phone3: String? = null,

	@NotBlank
	var userId: String,

	@NotBlank
	var userName: String,

	@NotBlank
	var password: String,

	@NotBlank
	var email: String,

	@NotBlank
	var mobile1: String,

	@NotBlank
	var mobile2: String,

	@NotBlank
	var mobile3: String,

	var enterday: String,

	var bank: String,
	var bankNum: String
)