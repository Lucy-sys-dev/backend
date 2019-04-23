package user.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Create by lucy on 2019-02-12
 **/
@Entity
@Table(schema = "public", name = "core_code")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
data class CoreCode(
	@EmbeddedId
	var pk: CoreCodeId? = null,

	@Column(name = "name")
	var name: String? = null,

	@Column(name = "value_type")
	var valueType: String? = null,

	@Column(name = "code_attribute_1")
	var codeAttribute1: String? = null,

	@Column(name = "code_attribute_2")
	var codeAttribute2: String? = null,

	@Column(name = "description")
	var description: String? = null,

	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "group_code", referencedColumnName="code", insertable = false, updatable = false, nullable = false)
	var groupCode : CoreGroupCode? = null

) : Auditable(), Serializable {
	constructor(pk: CoreCodeId?,
	            name: String?,
	            valueType: String?,
	            codeAttribute1: String?,
	            codeAttribute2: String?,
	            description: String?
	) : this() {
		this.pk = pk
		this.name = name
		this.valueType = valueType
		this.codeAttribute1 = codeAttribute1
		this.codeAttribute2 = codeAttribute2
		this.description = description
	}
}

