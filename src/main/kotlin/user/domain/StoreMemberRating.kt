package user.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.*
import java.io.Serializable
import javax.persistence.*
import javax.persistence.Entity
import javax.persistence.Table

/**
 * Create by lucy on 2019-02-18
 **/
@Entity
@Table(schema = "core", name = "store_member_rating")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
data class StoreMemberRating (
	@EmbeddedId
	var pk: StoreMemberRatingId? = null,

	@Column(name = "name")
	var name: String? = null,

	@Column(name = "save_rate")
	var saveRate: Float? = null,

	@Column(name = "discount_rate")
	var discountRate: Float? = null

) : Auditable() , Serializable {

	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumns(
//		JoinColumn(name = "core_group_code", referencedColumnName = "group_code", nullable = false),
//		JoinColumn(name = "core_code", referencedColumnName = "code", nullable = false),
//		JoinColumn(referencedColumnName = "service_type", )
////		,JoinColumn(referencedColumnName = "service_type", )
//	)
	@JoinColumnsOrFormulas(
		JoinColumnOrFormula(column = JoinColumn(name = "core_group_code", referencedColumnName = "group_code", insertable = false, updatable = false)),
		JoinColumnOrFormula(column = JoinColumn(name = "core_code", referencedColumnName = "code", insertable = false, updatable = false)),
		JoinColumnOrFormula(formula = JoinFormula(value = "'ALL'", referencedColumnName = "service_type"))
		)
	var coreCode: CoreCode? = null
}

@Embeddable
data class StoreMemberRatingId (
	@Column(name = "store_id")
	var storeId: Long = 0,

	@Column(name = "core_group_code")
	var coreGroupCode: String? = null,

	@Column(name = "core_code")
	var coreCode: String? = null,

	@Column(name = "cls_group_code")
	var clsGroupCode: String? = null,

	@Column(name = "cls_code")
	var clsCode: String? = null
) : Serializable