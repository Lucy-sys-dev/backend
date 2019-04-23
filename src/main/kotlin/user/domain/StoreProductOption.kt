package user.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId

/**
 * Create by lucy on 2019-02-19
 **/
data class StoreProductOption (
	@EmbeddedId
	var pk: StoreProductOptionId? = null,

	@Column(name = "value")
	var value: Int = 0,

	@Column(name = "value_type")
	var valueType: String? = null
) {
}

@Embeddable
data class StoreProductOptionId (
	@Column(name = "store_id")
	val storeId: Long = 0,

	@Column(name = "core_group_code")
	val coreGroupCode: String? = null,

	@Column(name = "core_code")
	var coreCode: String? = null,

	@Column(name = "cls_group_code")
	var clsGroupCode: String? = null,

	@Column(name = "cls_code")
	var clsCode: String? = null
) : Serializable

