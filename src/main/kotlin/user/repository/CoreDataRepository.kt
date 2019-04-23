package user.repository

import org.springframework.data.jpa.repository.JpaRepository
import user.domain.Area
import user.domain.SettingItem
import javax.persistence.OrderBy

/**
 * Create by lucy on 2019-02-22
 **/
interface RegionsRepository : JpaRepository<Area, Long> {
	fun findRegionsByUpperCodeIsNull(): List<Area>
}

interface SettingItemRepository: JpaRepository<SettingItem, Long> {
	fun findSettingItemsByServiceTypeInOrderByOrderNoAsc(serviceTypes: List<String>) : List<SettingItem>
	fun findSettingItemsByItemAndServiceTypeIn(item: String, serviceTypes: List<String>) : SettingItem
}