package user.domain

import lombok.Data
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.Where
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Create by lucy on 2019-02-22
 **/
@Entity
@Data
@org.hibernate.annotations.DynamicUpdate
@DynamicInsert
@Table(schema = "public", name="regions")
data class Area(
	@Id
	@Column(name = "code", updatable = false, nullable = false)
	var code: String? = null,

	@Column(name = "name")
	var name: String? = null,

	@Column(name = "upper_code")
	var upperCode: String? = null,

	@Column(name = "delete_yn", insertable = false)
	var deleteYn: String? = null,

	@Column(name = "delete_date", insertable = false)
	var deleteDate: LocalDateTime? = null,

	@Column(name = "delete_by", insertable = false)
	var deleteBy: String? = null,

	@Where(clause = "deleteYn='N'")
	@ManyToOne(cascade= arrayOf(CascadeType.ALL)) //, fetch = FetchType.LAZY)
	@JoinColumn(name="upper_code", insertable = false, updatable = false)
	var parentArea: Area
	,
//	@Where(clause = "deleteYn='N'")
	@OneToMany(mappedBy = "parentArea")
////	@OneToMany(fetch = FetchType.LAZY)
////	@JoinColumn(name="upper_code")
	var childRegions: Set<Area>

): Auditable(), Serializable {
}