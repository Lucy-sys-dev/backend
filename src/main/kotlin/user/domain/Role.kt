package user.domain

enum class Role {
	ADMIN, CEO, EMPLOYEE, USER, STORE;

	fun authority() = "ROLE_$name"
}