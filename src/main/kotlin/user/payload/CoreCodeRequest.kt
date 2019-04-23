package user.payload

import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-02-17
 **/
class CoreCodeRequest {
	@NotNull
	var groupCode: String? = null

	@NotNull
	var groupCodeName: String? = null

	@NotNull
	var groupServiceType: String? = null

	@NotNull
	var groupCodeDescription: String? = null

	@NotNull
	var code: String? = null

	@NotNull
	var name: String? = null

	@NotNull
	var serviceType: String? = null

	@NotNull
	var valueType: String? = null

	var codeAttribute1: String? = null

	var codeAttribute2: String? = null

	var description: String? = null
}