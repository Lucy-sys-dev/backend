package user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import user.domain.User
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
	fun findById(id: String) : Optional<User>
}