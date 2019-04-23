package user.service

import org.jetbrains.kotlin.backend.common.push
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import user.domain.ReceptStore
import user.domain.Store
import user.enum.AreaGroup
import user.enum.AreaList
import user.exception.ResourceNotFoundException
import user.payload.ReceptStoreRequest
import user.payload.ReceptStoreUser
import user.repository.ReceptStoreRepository
import user.repository.StoreRepository
import java.time.LocalDateTime.now
import java.util.*
import kotlin.collections.HashMap

@Service
class ReceptStoreService(
	@Autowired private val receptStoreRepository: ReceptStoreRepository,
	@Autowired private val storeRepository: StoreRepository
) {
	fun findAllReceptStores() : List<HashMap<String, String>> {
		val storeList = receptStoreRepository.findAll()
		val storeData = ArrayList<HashMap<String, String>>()
		for (store in storeList) {
			var data = HashMap<String, String>()
			data.put("seq", store.seq.toString())
			data.put("storeName", store.storeName!!)
			data.put("userName", store.userName!!)
			data.put("city", store.city!!)
			data.put("cityName", AreaGroup.valueOf(store.city!!).viewName)
			data.put("district", store.district!!)
			data.put("districtName", AreaList.valueOf(store.district!!).viewName)
			data.put("mobile1", store.mobile1!!)
			data.put("mobile2", store.mobile2!!)
			data.put("mobile3", store.mobile3!!)
			data.put("ceoName", store.ceoName!!)
			data.put("ceoMobile1", store.ceoMobile1!!)
			data.put("ceoMobile2", store.ceoMobile2!!)
			data.put("ceoMobile3", store.ceoMobile3!!)
			data.put("confirmYn", store.confirmYn!!)
			data.put("deleteYn", store.deleteYn!!)
			storeData.push(data)
		}

		return storeData
	}

	fun findReceptStoresByStoreId(id: Long) : ReceptStore? {
		return receptStoreRepository.findReceptStoresByStoreIdAndConfirmYn(id, "Y")
			.orElseThrow {  throw ResourceNotFoundException("ReceptStore", "storeId", id)}

	}

	fun findBySeq(seq: Long): ReceptStore? {
		return receptStoreRepository.findReceptStoresBySeq(seq)
			.orElseThrow {  throw ResourceNotFoundException("ReceptStore", "seq", seq)}
	}

	fun confirmStore(seq: Long) : ReceptStore? {
		findBySeq(seq).let { newReceptStore ->
			val store = Store(newReceptStore!!.storeName)
			storeRepository.save(store)
			newReceptStore.confirmBy = newReceptStore.updateBy
			newReceptStore.confirmDate = now()
			newReceptStore.storeId = store.id
			newReceptStore.confirmYn = "Y"
			receptStoreRepository.save(newReceptStore)
			return newReceptStore
		}
	}

	fun deleteReceptStoreBySeq(seq: Long) {
		findBySeq(seq).let { newReceptStore ->
			newReceptStore!!.deleteYn = "Y"
			newReceptStore!!.deleteDate = now()
			newReceptStore!!.deleteBy = "sa1b12"
			receptStoreRepository.save(newReceptStore)
		}
	}

	fun createReceptStore(receptStoreRequest: ReceptStoreRequest) {
		var receptStore : ReceptStore = ReceptStore(storeName = receptStoreRequest.storeName,
													serviceType = receptStoreRequest.serviceType,
													userName = receptStoreRequest.userName,
													job = receptStoreRequest.job,
													mobile1 = receptStoreRequest.mobile1,
													mobile2 = receptStoreRequest.mobile2,
													mobile3 = receptStoreRequest.mobile3,
													phone1 = receptStoreRequest.phone1,
													phone2 = receptStoreRequest.phone2,
													phone3 = receptStoreRequest.phone3,
													city = receptStoreRequest.city,
													district = receptStoreRequest.district,
													ceoName = receptStoreRequest.ceoName,
													ceoMobile1 = receptStoreRequest.ceoMobile1,
													ceoMobile2 = receptStoreRequest.ceoMobile2,
													ceoMobile3 = receptStoreRequest.ceoMobile3 )

		receptStoreRepository.save(receptStore)
	}

	fun findReceptStoresByUser(receptStoreUser: ReceptStoreUser) : ReceptStore {
		return receptStoreRepository.findReceptStoresByCeoNameAndCeoMobile1AndCeoMobile2AndCeoMobile3AndDeleteYn(
			receptStoreUser.userName!!, receptStoreUser.mobile1!!, receptStoreUser.mobile2!!, receptStoreUser.mobile3!!, "N")
			.orElseThrow {
				throw ResourceNotFoundException("ReceptStore", "ceoName", receptStoreUser.userName!!)}
	}
}

