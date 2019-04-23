package user.payload

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.Id
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-02-18
 **/
class StoreOptionsRequest {
	@NotNull
	var storeId: Long? = null

	@JsonFormat( shape = JsonFormat.Shape.ANY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9" )
	lateinit var startDate: Date
	@JsonFormat( shape = JsonFormat.Shape.ANY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9" )
	lateinit var endDate: Date

	var optionLists: List<OptionList>? = null
}

data class OptionList (
	@NotNull
	var code: String? = null,

	@NotNull
	var name: String? = null,

	//schedule
	var dayCode: String? = null,
	var startTime: String? = null,
	var endTime: String? = null,

	//grade
	var saveRate: Float? = null,
	var discountRate: Float? = null
)