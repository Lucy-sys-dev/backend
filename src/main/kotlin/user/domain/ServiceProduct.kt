package user.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.*
import user.domain.types.json.JsonBinaryType
import user.enum.Gender
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.Entity
import javax.persistence.OrderBy
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/**
 * Create by lucy on 2019-01-28
 **/
@Entity
@Table(schema = "catalog", name = "service_product")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
data class ServiceProduct (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	var id: Long? = null,

	@NotBlank
	@Size(max = 100)
	@Column(name = "name")
	var name: String? = null,

	@NotBlank
	@Column(name = "store_id")
	var storeId: Long? = null,

	@NotBlank
	@Column(name = "service_type")
	var serviceType: String? = null,

	@NotBlank
	@Column(name = "duration")
	var duration: Long? = null,

	@Column(name = "online_yn")
	var onlineYn: String? = null,

	@Type(type = "jsonb")
	@Column(name = "manager", columnDefinition = "jsonb")
	var manager: MutableSet<String> = mutableSetOf(),

	@Column(name = "delete_yn", insertable = false)
	var deleteYn: String? = null,

	@Column(name = "delete_date", insertable = false)
	var deleteDate: LocalDateTime? = null,

	@Column(name = "delete_by", insertable = false)
	var deleteBy: String? = null,

//	@Lob
	@Column(name = "description")
	var description: String? = null,

	@Column(name = "target")
	var target: String? = null,

	//@JsonIgnore
	@OneToMany//(mappedBy = "serviceProduct", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id")
	@OrderBy("end_date Desc")
	var serviceProductPrices: List<ServiceProductPrice> = emptyList(),

	@OneToMany//(mappedBy = "serviceProduct", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id")
	@Where(clause = "delete_yn = 'N'")
	@OrderBy("end_date Desc")
	var serviceProductOptions: List<ServiceProductOption> = emptyList(),

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinFormula("(SELECT spp.* FROM product.service_product_price spp WHERE spp.service_id = id ORDER BY spp.end_date desc LIMIT 1)")
//	val lastServiceProductPrice: ServiceProductPrice? = null

//	@OneToOne
//	@JoinColumn(name = "service_id")
//	@OrderBy("end_date Desc")
//	@Formula(value = "(SELECT spp.service_id FROM product.service_product_price spp WHERE spp.service_id = id ORDER BY spp.end_date desc LIMIT 1)"
////				referencedColumnName = "service_id"
//	)

	@Transient
	var lastServiceProductPrice: ServiceProductPrice? = null

) : Auditable(), Serializable {

//	constructor(
//		name: String?,
//		storeId: Long?,
//		serviceType: String?,
//		duration: Long?)
//			: this() {
//		this.name = name
//		this.storeId = storeId
//		this.serviceType = serviceType
//		this.duration = duration
//	}

	@PostLoad
	fun setLastServiceProductPrice() {
//		var serviceProductPrice: ServiceProductPrice? = null
		if (serviceProductPrices == null || serviceProductPrices.isEmpty()) {
			this.lastServiceProductPrice = null
		} else {
			for (item in serviceProductPrices) {
				if (item.pk!!.priceFlag.equals("weekday")) {
					if (this.lastServiceProductPrice == null) {
						this.lastServiceProductPrice = item
					} else {
						if (this.lastServiceProductPrice!!.pk!!.endDate!! < item.pk!!.endDate) {
							this.lastServiceProductPrice = item
						}
					}
				}
			}
		}
	}
}