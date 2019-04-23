package user.payload

import com.fasterxml.jackson.annotation.JsonFormat
import com.sun.jna.platform.win32.WinDef
import user.enum.Gender
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * Create by lucy on 2019-02-05
 **/
class ServiceProductRequest {
	@NotNull
	var storeId: Long = 0

	@NotNull
	var serviceType: String? = null

	@NotNull
	var name: String? = null

	var onlineYn: String? = null

	var duration: Long = 0

	val prices: List<ServiceProductPrice>? = null
	val options: List<ServiceProductOption>? = null

	var description: String? = null
	var packageYn: String? = null

	var target: String? = null
	var deleteYn: String? = null
	var manager: MutableSet<String>? = null


	@JsonFormat( shape = JsonFormat.Shape.ANY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT" )
	lateinit var startDate: LocalDateTime
	@JsonFormat( shape = JsonFormat.Shape.ANY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT" )
	lateinit var endDate: LocalDateTime

	lateinit var startTime: String
	lateinit var endTime: String
}

data class ServiceProductPrice (
	var priceFlag: String,
	var originPrice: Long,
	var salePrice: Long
)

data class ServiceProductOption (
	var optionId: Long,
	var optionPriceGroupId: Long
)