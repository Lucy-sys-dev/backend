package user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import user.domain.*
import java.util.*

/**
 * Create by lucy on 2019-02-18
 **/
interface StoreOptionMemberRatingRepository : JpaRepository<StoreMemberRating, StoreMemberRatingId> {
	fun findStoreMemberGradesByPk(storeMemberGradeId: StoreMemberRatingId): Optional<StoreMemberRating>

	@Query("select s FROM StoreMemberRating s WHERE s.pk.storeId = :storeId AND s.pk.coreGroupCode = :coreGroupCode")
	fun findStoreMemberRatingsByStoreIdAndCoreGroupCode(storeId: Long, coreGroupCode: String) : Optional<StoreMemberRating>
}

interface StoreOptionSchedulesRepository : JpaRepository<StoreSchedule, StoreScheduleId> {
	fun findStoreSchedulesByPk(storeScheduleId: StoreScheduleId): Optional<StoreSchedule>
}

interface StoreSettingItemRepository: JpaRepository<StoreSettingItem, StoreSettingItemPK> {
	@Query("select s FROM StoreSettingItem s WHERE s.pk.storeId = :storeId AND s.pk.deleteYn = :deleteYn")
	fun findStoreSettingItemsByStoreIdAndDeleteYn(storeId: Long, deleteYn: String) : List<StoreSettingItem>
}
