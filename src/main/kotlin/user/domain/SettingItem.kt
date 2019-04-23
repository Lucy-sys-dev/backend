package user.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import javax.persistence.*

/**
 * Create by lucy on 2019-03-22
 **/
@Entity
@Table(schema = "public", name = "setting_item")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamicUpdate
@DynamicInsert
data class SettingItem (
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	var id: Long? = null,

	@Column(name = "service_type")
	var serviceType: String? = null,

	@Column(name = "item")
	var item: String? = null,

	@Column(name = "name")
	var name: String? = null,

	@Column(name = "delete_yn")
	var deleteYn: String? = null,

	@Column(name = "icon")
	var icon: String? = null,

	@Column(name = "classification")
	var classification: String? = null,

	@Column(name = "order_no")
	var orderNo: Int? = null

) : Auditable(), Serializable {
	//constructor()
}