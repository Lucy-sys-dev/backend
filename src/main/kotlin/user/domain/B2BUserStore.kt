package user.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.JoinColumnOrFormula
import org.hibernate.annotations.JoinFormula
import org.hibernate.annotations.Where
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(schema = "customer", name = "b2buser_store")
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class B2BUserStore (

	@EmbeddedId
	var b2BUserStorePK: B2BUserStorePK?,

	@Column(name = "delete_yn")
	var deleteYn: String? = null,

	@Column(name = "position")
	var position: String? = null,

	@Column(name = "confirm_yn")
	var confirmYn: String? = null,

	@Column(name = "join_date")
	var joinDate: LocalDate? = null,

	@Column(name = "nick_name")
	var nickName: String? = null,

	@Column(name = "salary_bank")
	var salaryBank: String? = null,

	@Column(name = "salary_bank_number")
	var salaryBankNumber: String? = null,

	@Column(name = "role")
	var role: String? = null

): Auditable(), Serializable {

	@Embeddable
	data class B2BUserStorePK (
		@Column(name="user_id", nullable=false, insertable = false, updatable = false)
		var userId: String? = "",

		@Column(name="store_id", nullable=false)
		var storeId: Long? = null
	) : Serializable {

	}
	constructor() : this(null, "")

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName="id", insertable = false, updatable = false)
	var user: B2BUser? = null

	@OneToOne
	@JoinColumn(name = "store_id", referencedColumnName="id", insertable = false, updatable = false)
	@Where(clause = "delete_yn='N'")
	var store: Store? = null

//	@OneToOne
//	@JoinColumnOrFormula(
//		formula = JoinFormula(value = "")
//	)


}