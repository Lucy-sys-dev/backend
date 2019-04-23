package user.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.sql.Timestamp
import java.time.Instant
import java.io.Serializable
import java.sql.Date
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
//@Table(schema = "customer", name = "b2b_user",
//	   uniqueConstraints = arrayOf(
//		   UniqueConstraint(columnNames = arrayOf("id"))
//	   ))
@Table(schema = "customer", name = "b2buser")
//@SequenceGenerator(
//	name="PersistentAuditEventGenerator",
//	sequenceName="PAE_SEQUENCE"
//)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class B2BUser (
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	var id: String? = null,

	@NotBlank
	@Size(max = 50)
	@Column(name = "name")
	var name: String? = null,

	@NotBlank
	@Size(max = 100)
	@Column(name = "password")
	var password: String? = null,

	@NotBlank
	@Size(max = 3)
	@Column(name = "sex")
	var sex: String? = null,

//	@CreatedDate
//	@Column(name = "register_date")
//	var register_date: Instant? = null
//
//	@LastModifiedDate
//	@Column(name = "update_date")
//	var update_date: Instant? = null

//	@OneToOne
//	@JoinTable(
//		name = "role", schema = "ref",
//		joinColumns = [JoinColumn(name="role_id")],
//		inverseJoinColumns = [JoinColumn(name = "id")]
//	)
//	var role: Role? = null

	@Column(name = "mobile1", nullable=false)
	var mobile1: String? = null,

	@Column(name = "mobile2", nullable=false)
	var mobile2: String? = null,

	@Column(name = "mobile3", nullable=false)
	var mobile3: String? = null,

	@Column(name = "birthday")
	var birthday: Date? = null,

	@NotBlank
	@Size(max = 10)
	@Column(name = "role_id")
	var role: String? = null

) : Auditable(), Serializable {

//
//)
//	constructor() {
//
//	}

//	constructor(id: String,
//				name: String,
//				password: String,
//				sex: String,
//	            role: String
//	) : this() {
//		this.id = id
//		this.name = name
//		this.password = password
//		this.sex = sex
//		this.role = role
//
//	}

}