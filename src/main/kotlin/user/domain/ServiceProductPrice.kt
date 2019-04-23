package user.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import javax.persistence.*

/**
 * Create by lucy on 2019-01-28
 **/
@Entity
@Table(schema = "catalog", name = "service_product_price")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ServiceProductPrice (
	@EmbeddedId
	var pk: ServiceProductPriceId?,

//	@ManyToOne
//	@JoinColumn(name = "service_id", foreignKey = ForeignKey(name = "fk_service_product_price_service_product"), insertable = false, updatable = false) //mappedBy = "styleServicePrice")
//	val serviceProduct: ServiceProductRequest,

	@Column(name = "origin_price")
	var originPrice: Long? = null,

	@Column(name = "sale_price")
	var salePrice: Long? = null
) : Auditable(), Serializable {

	private constructor() : this(null, 0, 0)

//	constructor(
//		pk: ServiceProductPriceId?,
//		originPrice: Long?,
//		discountPrice: Long?,
//		priceOptionFlag: String?,
//		option1Price: Long?,
//		option2Price: Long?,
//		option3Price: Long?,
//		fixedYn: String?
//	) : this() {
//		this.pk = pk
//		this.originPrice = originPrice
//		this.discountPrice = discountPrice
//		if (originPrice != null) {
//			this.salePrice = originPrice - discountPrice!!
//		}
//		this.priceOptionFlag = priceOptionFlag
//		this.option1Price = option1Price
//		this.option2Price = option2Price
//		this.option3Price = option3Price
//		this.fixedYn = fixedYn
//	}


	override fun equals(other: Any?): Boolean {
		var result = false
		if (other is ServiceProduct) {
			val otherObj = other as ServiceProductPrice?
			result = this.pk!!.serviceId == otherObj!!.pk!!.serviceId &&
					this.pk!!.priceFlag == otherObj!!.pk!!.priceFlag &&
					this.pk!!.startDate == otherObj!!.pk!!.startDate &&
					this.pk!!.endDate == otherObj!!.pk!!.endDate &&
					this.pk!!.startTime == otherObj!!.pk!!.startTime &&
					this.pk!!.endTime == otherObj.pk!!.endTime
		}
		return result
	}

	override fun hashCode(): Int {
		return this.pk!!.serviceId.hashCode() * 41 +
				this.pk!!.endDate!!.hashCode() * 17
	}
}