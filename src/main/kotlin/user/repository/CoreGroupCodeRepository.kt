package user.repository

import org.springframework.data.jpa.repository.JpaRepository
import user.domain.CoreGroupCode
import java.util.*

/**
 * Create by lucy on 2019-02-17
 **/
interface CoreGroupCodeRepository : JpaRepository<CoreGroupCode, Long> {
	fun findCoreGroupCodesByCode(code: String) : Optional<CoreGroupCode>

	fun findCoreGroupCodesByName(name: String) : Optional<CoreGroupCode>
}