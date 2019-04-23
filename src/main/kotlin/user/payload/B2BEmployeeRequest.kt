package user.payload

import javax.validation.constraints.NotBlank

data class B2BEmployeeRequest (
	@NotBlank
	var userId: String? = null,

	@NotBlank
	var password: String? = null,

	@NotBlank
	var name: String? = null,

	@NotBlank
	var storeId: Long? = null,

	var sex: String? = null,

	@NotBlank
	var mobile1: String? = null,

	@NotBlank
	var mobile2: String? = null,

	@NotBlank
	var mobile3: String? = null
)

