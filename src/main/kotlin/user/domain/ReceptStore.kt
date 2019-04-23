package user.domain

import lombok.Data
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import java.io.Serializable
import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Data
@org.hibernate.annotations.DynamicUpdate
@DynamicInsert
@Table(schema = "customer", name="recept_store")
data class ReceptStore (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq", updatable = false)
	var seq: Long? = null,

	@Column(name = "store_name")
	var storeName: String? = null,

	@Column(name = "service_type")
	var serviceType: String? = null,

	@Column(name = "user_name")
	var userName: String? = null,

	@Column(name = "job")
	var job: String? = null,

	@Column(name = "mobile1")
	var mobile1: String? = null,

	@Column(name = "mobile2")
	var mobile2: String? = null,

	@Column(name = "mobile3")
	var mobile3: String? = null,

	@Column(name = "phone1")
	var phone1: String? = null,

	@Column(name = "phone2")
	var phone2: String? = null,

	@Column(name = "phone3")
	var phone3: String? = null,

	@Column(name = "city")
	var city: String? = null,

	@Column(name = "district")
	var district: String? = null,

	@Column(name = "ceo_name")
	var ceoName: String? = null,

	@Column(name = "ceo_mobile1")
	var ceoMobile1: String? = null,

	@Column(name = "ceo_mobile2")
	var ceoMobile2: String? = null,

	@Column(name = "ceo_mobile3")
	var ceoMobile3: String? = null,

	@Column(name = "confirm_yn", insertable = false)
	var confirmYn: String? = null,

	@Column(name = "confirm_date", insertable = false)
	var confirmDate: LocalDateTime? = null,

	@Column(name = "confirm_by", insertable = false)
	var confirmBy: String? = null,

	@Column(name = "store_id", insertable = false)
	var storeId: Long? = null,

	@Column(name = "delete_yn", insertable = false)
	var deleteYn: String? = null,

	@Column(name = "delete_date", insertable = false)
	var deleteDate: LocalDateTime? = null,

	@Column(name = "delete_by", insertable = false)
	var deleteBy: String? = null

): Auditable(), Serializable {

	constructor(
		storeName: String,
		serviceType: String,
		userName: String,
		job: String,
		mobile1: String,
		mobile2: String,
		mobile3: String,
		phone1: String,
		phone2: String,
		phone3: String,
		city: String,
		district: String,
		ceoName: String,
		ceoMobile1: String,
		ceoMobile2: String,
		ceoMobile3: String
	) : this()
}
