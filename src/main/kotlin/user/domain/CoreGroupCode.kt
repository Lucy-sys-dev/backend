package user.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import javax.persistence.*

/**
 * Create by lucy on 2019-02-12
 **/
@Entity
@Table(schema = "public", name = "core_group_code")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
data class CoreGroupCode (
	@Id
	@Column(name = "code", updatable = false, nullable = false)
	var code: String? = null,

	@Column(name = "name")
	var name: String? = null,

	@Column(name = "service_type")
	var serviceType: String? = null,

	@Column(name = "description")
	var description: String? = null,

	@JsonIgnore
	@OneToMany(cascade= arrayOf(CascadeType.ALL), fetch=FetchType.LAZY, mappedBy = "groupCode")
	var coreCodes: List<CoreCode>? = null

) : Auditable() , Serializable {

	constructor(code: String?,
	            name: String?,
	            serviceType: String?,
	            description: String?
	) : this() {
		this.code = code
		this.name = name
		this.serviceType = serviceType
		this.description = description
	}
}