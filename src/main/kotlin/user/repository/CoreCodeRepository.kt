package user.repository

import org.springframework.data.jpa.repository.JpaRepository
import user.domain.CoreCode
import user.domain.CoreCodeId
import java.util.*

/**
 * Create by lucy on 2019-02-17
 **/
interface CoreCodeRepository: JpaRepository<CoreCode, Long> {
	fun findCoreCodesByPk(pk: CoreCodeId) : Optional<CoreCode>
}