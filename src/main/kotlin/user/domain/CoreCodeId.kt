package user.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * Create by lucy on 2019-02-13
 **/
@Embeddable
data class CoreCodeId (
	@Column(name = "group_code", insertable= true, updatable = false)
	var groupCode: String? = null,

	@Column(name = "code")
	var code: String? = null,

	@Column(name = "service_type")
	var serviceType: String? = null
) : Serializable
