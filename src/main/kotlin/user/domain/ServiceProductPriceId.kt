package user.domain

import lombok.AllArgsConstructor
import lombok.EqualsAndHashCode
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import kotlin.text.Typography.prime

/**
 * Create by lucy on 2019-01-29
 **/
@Embeddable
data class ServiceProductPriceId ( //: Serializable {
	@Column(name = "service_id")
	val serviceId: Long = 0,

	@Column(name = "price_flag")
	val priceFlag: String? = null,

	@Column(name = "start_date")
	val startDate: LocalDateTime? = null,

	@Column(name = "end_date")
	val endDate: LocalDateTime? = null,

	@Column(name = "start_time")
	val startTime: String? = null,

	@Column(name = "end_time")
	val endTime: String? = null
) : Serializable