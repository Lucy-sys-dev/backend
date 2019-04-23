package user.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

/**
 * Create by lucy on 2019-02-15
 **/
@Entity
@Table(schema = "catalog", name = "service_product_option")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ServiceProductOption (
	@EmbeddedId
	var pk: ServiceProductOptionId?,

	@Column(name = "delete_yn")
	var deleteYn: String? = null,

	@Column(name = "delete_date")
	var deleteDate: LocalDateTime? = null,

	@Column(name = "delete_by")
	var deleteBy: String? = null
) : Auditable(), Serializable {

	private constructor() : this(null, null, null, null)

	override fun equals(other: Any?): Boolean {
		var result = false
		if (other is ServiceProduct) {
			val otherObj = other as ServiceProductOption?
			result = this.pk!!.serviceId == otherObj!!.pk!!.serviceId &&
					this.pk!!.optionId == otherObj!!.pk!!.optionId &&
					this.pk!!.startDate == otherObj!!.pk!!.startDate &&
					this.pk!!.endDate == otherObj!!.pk!!.endDate &&
					this.pk!!.optionPriceGroupId == otherObj.pk!!.optionPriceGroupId
		}
		return result
	}

	override fun hashCode(): Int {
		return this.pk!!.serviceId.hashCode() * 41 +
				this.pk!!.optionId.hashCode() * 41 +
				this.pk!!.endDate!!.hashCode() * 17
	}
}