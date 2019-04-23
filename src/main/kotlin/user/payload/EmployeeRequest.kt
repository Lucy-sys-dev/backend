package user.payload

import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-02-27
 **/
class EmployeeRequest {
	@NotNull
	var storeId: Long? = null

	@NotNull
	var userId: String? = null
}