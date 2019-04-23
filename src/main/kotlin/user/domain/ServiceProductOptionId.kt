package user.domain

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * Create by lucy on 2019-02-15
 **/
@Embeddable
data class ServiceProductOptionId (
	@Column(name = "service_id")
	val serviceId: Long = 0,

	@Column(name = "option_id")
	val optionId: Long = 0,

	@Column(name = "option_price_group_id")
	val optionPriceGroupId: Long = 0,

	@Column(name = "start_date")
	val startDate: LocalDateTime? = null,

	@Column(name = "end_date")
	val endDate: LocalDateTime? = null

) : Serializable