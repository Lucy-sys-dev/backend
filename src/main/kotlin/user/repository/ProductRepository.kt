package user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import user.domain.ServiceProduct
import java.util.*

/**
 * Create by lucy on 2019-01-28
 **/
@Repository
interface ProductRepository : JpaRepository<ServiceProduct, Long> {
	fun findProductServiceByStoreId(id: Long) : List<ServiceProduct>

	fun findServiceProductsByStoreIdAndName(id: Long, name: String) : Optional<ServiceProduct>
}
