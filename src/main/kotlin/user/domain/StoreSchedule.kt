package user.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

/**
 * Create by lucy on 2019-02-19
 **/
@Entity
@Table(schema = "core", name = "store_schedule")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
data class StoreSchedule (
	@EmbeddedId
	var pk: StoreScheduleId? = null,

	@Column(name = "start_time")
	var startTime: String? = null,

	@Column(name = "end_time")
	var endTime: String? = null

) : Auditable(), Serializable {
}

@Embeddable
data class StoreScheduleId (
	@Column(name = "store_id")
	var storeId: Long = 0,

	@Column(name = "start_date")
	var startDate: Date? = null,

	@Column(name = "end_date")
	var endDate: Date? = null,

	@Column(name = "code")
	var code: String? = null,

	@Column(name = "day_code")
	var dayCode: String? = null
) : Serializable