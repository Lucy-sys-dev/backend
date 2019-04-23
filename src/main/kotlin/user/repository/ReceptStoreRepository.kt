package user.repository

import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import user.domain.ReceptStore
import java.util.*

@Repository
interface ReceptStoreRepository :JpaRepository<ReceptStore, Long> {
	fun findByUserName(userName: String) : Optional<ReceptStore>

	fun findByStoreName(storeName: String?) : Optional<ReceptStore>

	fun findReceptStoresBySeq(seq: Long) : Optional<ReceptStore>

	fun findReceptStoresByStoreIdAndConfirmYn(storeId: Long, confirmYn: String) : Optional<ReceptStore>

	@Query("SELECT v from ReceptStore v where v.storeName = :storeName and v.deleteYn = 'N'")
	fun findByStoreNameAndDeleteYn(@Param("storeName") storeName: String?)

	fun findReceptStoresByCeoNameAndCeoMobile1AndCeoMobile2AndCeoMobile3AndDeleteYn(
		userName: String, mobile1: String, mobile2:String, mobile3:String, deleteYn:String) : Optional<ReceptStore>

	//@Modifying(clearAutomatically = true)
//	@Transactional
//	@Modifying
//	@Query("update ReceptStore set confirm_date = now() where seq = :seq ")
//	fun confirmStore(@Param("seq") seq: Long?)


}