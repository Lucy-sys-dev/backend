package user.repository

import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import user.domain.B2BUser
import java.util.*
import javax.annotation.Nullable

@Repository
interface B2BUserRepository : JpaRepository<B2BUser, Long> {
	//@Nullable
	fun findById(id: String) : Optional<B2BUser>
	fun countById(id: String) : Int

	fun findB2BUsersByIdAndPassword(id: String, password: String) : Optional<B2BUser>

	fun findByName(name: String) : Optional<B2BUser>

	//@Query("SELECT u FROM B2BUser u WHERE u.mobile1 = ?1")
	fun findB2BUsersByMobile1(mobile1: String) : Optional<B2BUser>

	//@Query("SELECT u FROM B2BUser u WHERE u.mobile1 = :mobile1 AND u.mobile2 = :mobile2 AND u.mobile3 = :mobile3")
	//fun findB2BUsersByMobile1AndAndMobile2AndMobile3(@Param("mobile1") mobile1: String?, @Param("mobile2") mobile2: String?, @Param("mobile3") mobile3: String?) : Optional<B2BUser>
	fun findB2BUsersByMobile1AndAndMobile2AndMobile3(mobile1: String, mobile2: String, mobile3: String) : Optional<B2BUser>
}