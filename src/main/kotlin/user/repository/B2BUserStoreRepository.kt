package user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import user.domain.B2BUserStore
import java.util.*

@Repository
interface B2BUserStoreRepository : JpaRepository<B2BUserStore, B2BUserStore.B2BUserStorePK> {
	//@Nullable
//	fun findB2BUserStoresByUserId(userId: String): Optional<B2BUserStore>
//	fun countB2BUserStoresByUserId(userId: String): Int
//
	fun findB2BUserStoresByb2BUserStorePKStoreId(storeId: Long) : List<B2BUserStore>

	fun findB2BUserStoresByb2BUserStorePK(b2BUserStorePK: B2BUserStore.B2BUserStorePK): B2BUserStore

	fun findB2BUserStoresByB2BUserStorePKUserIdAndDeleteYn(userId: String, deleteYn: String) : B2BUserStore
//	fun countB2BUserStoresByStoreId(storeId: Int): Int
//
//	fun findB2BUserStoresByUserIdAndStoreId(userId: String, storeId: Int) : Optional<B2BUserStore>
}