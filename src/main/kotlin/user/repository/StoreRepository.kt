package user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import user.domain.Store
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
	fun findStoresById(id: Long) : Optional<Store>

}