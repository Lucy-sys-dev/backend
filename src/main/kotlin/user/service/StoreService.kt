package user.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import user.common.model.ResultData
import user.domain.*
import user.exception.ResourceAlreadyException
import user.exception.ResourceNotFoundException
import user.payload.StoreOptionsRequest
import user.repository.StoreOptionMemberRatingRepository
import user.repository.StoreOptionSchedulesRepository
import user.repository.StoreRepository
import user.repository.StoreSettingItemRepository
import java.util.*
import kotlin.collections.HashMap

/**
 * Create by lucy on 2019-02-11
 **/
@Service
class StoreService (
	@Autowired var storeRepository: StoreRepository,
	@Autowired var storeOptionMemberRatingRepository: StoreOptionMemberRatingRepository,
	@Autowired var storeOptionSchedulesRepository: StoreOptionSchedulesRepository,
	@Autowired var storeSettingItemRepository: StoreSettingItemRepository
) {
	private val log: Logger = LoggerFactory.getLogger(this.javaClass)

	fun findAllStores() : List<Store> = storeRepository.findAll()

	fun findStoresById(id: Long) : Store {
		return storeRepository.findStoresById(id)
			.orElseThrow {  throw ResourceNotFoundException("Store", "id", id) }
	}

	fun checkIfStoreIdIsExist(id: Long) : Boolean {
		storeRepository.findStoresById(id).let { return true }
		return false
	}

	fun updateStore(store: Store) {
		storeRepository.save(store)
	}

	fun findStoreRatingOptionById(id: Long) : StoreMemberRating? {
		return storeOptionMemberRatingRepository.findStoreMemberRatingsByStoreIdAndCoreGroupCode(id, "RATING")
			.orElseThrow { throw ResourceNotFoundException("Store", "id", id) }
	}

	fun createStoreOptionMemberGrade(storeOptionsRequest: StoreOptionsRequest) {
		for( item in storeOptionsRequest.optionLists!! ) {
			val memberGradePk = StoreMemberRatingId(storeOptionsRequest.storeId!!, item.code)

			checkIfMemberGradeIdIsExist(memberGradePk).let { newMemberGradePk ->
				val newMemberGrade = StoreMemberRating(
					newMemberGradePk,
					item.name,
					item.saveRate,
					item.discountRate
				)
				storeOptionMemberRatingRepository.save(newMemberGrade)
			}
		}
	}

	fun checkIfMemberGradeIdIsExist(id: StoreMemberRatingId) : StoreMemberRatingId =
		when {
			storeOptionMemberRatingRepository.findStoreMemberGradesByPk(id).isPresent -> throw ResourceAlreadyException("grade", "store_option")
			else -> id
	}

	fun createStoreOptionSchedule(storeOptionsRequest: StoreOptionsRequest) {
		for( item in storeOptionsRequest.optionLists!! ) {
			var schedulePk = StoreScheduleId(
				storeOptionsRequest.storeId!!,
				storeOptionsRequest.startDate,
				storeOptionsRequest.endDate,
				item.code,
				item.dayCode)

			checkIfScheduleIdIsExist(schedulePk).let { newSchedulePk ->
				val newSchedule = StoreSchedule(newSchedulePk, item.startTime, item.endTime)
				storeOptionSchedulesRepository.save(newSchedule)
			}
		}
	}

	fun checkIfScheduleIdIsExist(id: StoreScheduleId) : StoreScheduleId =
		when {
			storeOptionSchedulesRepository.findStoreSchedulesByPk(id).isPresent -> throw ResourceAlreadyException("schedule", "store_option")
			else -> id
		}

	fun aplyStoreSettings(): ResultData {
		val resultData = ResultData()

		// 서비스 옵션 설정


		return resultData
	}

	// fetch 스토어 설정 정보
	fun findStoreSettingItemByStoreId(storeId: Long): ArrayList<Map<String, String>> {
		var storeItems = ArrayList<Map<String, String>>()
		var items = storeSettingItemRepository.findStoreSettingItemsByStoreIdAndDeleteYn(storeId, "N")
		for (item in items) {
			var storeItem = HashMap<String, String>()
			storeItem.put("item", item.pk!!.itemCode!!)
			storeItem.put("storeName", item.store!!.name!!)
			storeItem.put("onlineYn", item.store!!.onlineYn!!)
			storeItems.add(storeItem)
		}
		return storeItems
	}

}