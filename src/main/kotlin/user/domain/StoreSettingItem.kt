package user.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

/**
 * Create by lucy on 2019-03-22
 **/
@Entity
@Table(schema = "core", name = "store_setting_item")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
data class StoreSettingItem(
	@EmbeddedId
	var pk : StoreSettingItemPK? = null,

	@Column(name = "delete_date")
	var deleteDate: LocalDate? = null,

	@Column(name = "delete_by")
	var deleteBy: String? = null
) : Auditable(), Serializable {

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "store_id", referencedColumnName="id", insertable = false, updatable = false)
	var store: Store? = null

//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "item_code", referencedColumnName="item", insertable = false, updatable = false)
//	var settingItem: SettingItem? = null
}

@Embeddable
data class StoreSettingItemPK(
	@Column(name = "store_id")
	var storeId: Long = 0,

	@Column(name = "item_code")
	var itemCode: String? = null,

	@Column(name = "delete_yn")
	var deleteYn: String? = null
) : Serializable