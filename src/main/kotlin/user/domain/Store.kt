package user.domain

import org.hibernate.annotations.DynamicInsert
import java.io.Serializable
import javax.persistence.*

@Entity
@DynamicInsert
@Table(schema = "core", name = "store",
	uniqueConstraints = arrayOf(
		UniqueConstraint(columnNames = arrayOf("id"))
	))
data class Store (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "id", unique = true, nullable = false)
	var id: Long? = null,

	@Column(name = "name")
	var name: String? = null,

	@Column(name = "business_number")
	var businessNumber: String? = null,

	@Column(name = "zip_code")
	var zipCode: String? = null,

	@Column(name = "address")
	var address: String? = null,

	@Column(name = "address_detail")
	var addressDetail: String? = null,

	@Column(name = "phone1")
	var phone1: String? = null,

	@Column(name = "phone2")
	var phone2: String? = null,

	@Column(name = "phone3")
	var phone3: String? = null,

	@Column(name = "title")
	var title: String? = null,

	@Column(name = "information")
	var information: String? = null,

	@Column(name = "service_type")
	var serviceType: String? = null,

	@Column(name = "online_yn")
	var onlineYn: String? = null

) : Auditable(), Serializable {

	constructor(name: String?) : this() {
		this.name = name
	}
}