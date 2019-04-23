package user.payload

import java.sql.Timestamp
import java.time.Instant

class UserProfile (
	var id: String?,
	var name: String?,
	var password: String?,
	var sex: String?,
	var role_id: String?,
	var role_name: String?
)