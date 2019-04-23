package user.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import user.domain.B2BUser
import user.domain.B2BUserStore
import user.domain.Store
import user.exception.ResourceAlreadyException
import user.exception.ResourceNotFoundException
import user.payload.B2BCeoRequest
import user.payload.B2BEmployeeRequest
import user.payload.EmployeeRequest
import user.repository.B2BUserRepository
import user.repository.B2BUserStoreRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
//@Transactional
class B2BUserService(
	@Autowired private var b2BUserRepository: B2BUserRepository,
	@Autowired private var b2BUserStoreRepository: B2BUserStoreRepository
) {

	private val log: Logger = LoggerFactory.getLogger(this.javaClass)

//	fun Register(b2bAccountRequest: B2BEmployeeRequest) : B2BUser {
//		if (b2BUserRepository.countById(b2bAccountRequest.userId!!) == 0) {
//			b2bAccountRequest.password = passwordEncoder!!.encode(b2bAccountRequest.password)
//			var b2bAccount = B2BUser(
//				b2bAccountRequest.userId!!, b2bAccountRequest.name!!, b2bAccountRequest.password!!, "W")
//			return b2BUserRepository!!.save(b2bAccount)
//		} else {
//			throw AccountException("UserId[%s] already taken.", b2bAccountRequest.userId!!)
//		}
//
//	}

	@Autowired
	private lateinit var storeService: StoreService

	fun findAllB2BUsers() : List<B2BUser> = b2BUserRepository.findAll()

	fun findB2BUserById(id: String) : B2BUser? {
		return b2BUserRepository.findById(id)
			.orElseThrow {  throw ResourceNotFoundException("B2BUser", "id", id) }
	}

	fun findB2BUserStoreByStoreIdAndUserId(storeId: Long, userId: String) : B2BUserStore {
		var pk = B2BUserStore.B2BUserStorePK(userId, storeId)
		return b2BUserStoreRepository.findB2BUserStoresByb2BUserStorePK(pk)
	}

	fun findB2BUserStoreByUserId(userId: String) : B2BUserStore {
		return b2BUserStoreRepository.findB2BUserStoresByB2BUserStorePKUserIdAndDeleteYn(userId, "N")
	}

	fun createEmployee(user: B2BEmployeeRequest) : B2BUser {
		var b2BUser = B2BUser(user.userId, user.name, user.password, user.sex, user.mobile1, user.mobile2, user.mobile3)
		var b2bUserStore = B2BUserStore(B2BUserStore.B2BUserStorePK(user.userId, user.storeId), "N")
		checkIfMobileIsAlreadyUsed(b2BUser).let { newUser ->
			b2BUserRepository.save(newUser.apply {
				password = BCryptPasswordEncoder().encode(newUser.password)
				role = "EMPLOYEE"
			})
		}.also { b2BUserStoreRepository.save(b2bUserStore)
				 return b2BUser
		}
		//todo 변경
		return b2BUser
	}

	fun createCeo(data: B2BCeoRequest) : B2BUser {
		var newStore = Store(id = data.storeId, name = data.storeName, phone1 = data.phone1, phone2 = data.phone2, phone3 = data.phone3)
		var b2BUser = B2BUser(id = data.userId, name = data.userName, password = data.password, sex = "F",
							  mobile1 = data.mobile1, mobile2 = data.mobile2, mobile3 = data.mobile3, role = "STORE")
		var b2bUserStore = B2BUserStore(B2BUserStore.B2BUserStorePK(data.userId, data.storeId), deleteYn = "N", position = "CEO", confirmYn = "Y", role = "SHOP_ADMIN",
										joinDate = LocalDate.parse(data.enterday, DateTimeFormatter.BASIC_ISO_DATE), salaryBank = data.bank, salaryBankNumber = data.bankNum)
		checkIfMobileIsAlreadyUsed(b2BUser).let { newUser ->
			b2BUserRepository.save(newUser.apply {
				password = BCryptPasswordEncoder().encode(newUser.password)
				role = "STORE"
			})
		}.also {
			b2BUserStoreRepository.save(b2bUserStore)
			storeService.updateStore(newStore)

		}
		return b2BUser
	}

	fun checkIfMobileIsAlreadyUsed(user: B2BUser): B2BUser =
		when {
			b2BUserRepository.findB2BUsersByMobile1AndAndMobile2AndMobile3(
				user.mobile1!!, user.mobile2!!, user.mobile3!!
			).isPresent -> {
				throw ResourceAlreadyException("mobile", "b2buser")
			}
			else -> user
		}

	fun findB2BUserEmployeeByStoreId(storeId: Long) : List<B2BUserStore> = b2BUserStoreRepository.findB2BUserStoresByb2BUserStorePKStoreId(storeId)

	fun confirmEmployeeStoreId(employeeRequest: EmployeeRequest) {
		var b2BUserStorePK = B2BUserStore.B2BUserStorePK(employeeRequest.userId, employeeRequest.storeId)
		b2BUserStoreRepository.findB2BUserStoresByb2BUserStorePK(b2BUserStorePK).let { newStoreEmployee ->
			b2BUserStoreRepository.save(newStoreEmployee.apply {
				confirmYn = "Y"
			})
		}
	}

	fun deleteEmployeeStoreId(employeeRequest: EmployeeRequest) {
		var b2BUserStorePK = B2BUserStore.B2BUserStorePK(employeeRequest.userId, employeeRequest.storeId)
		b2BUserStoreRepository.findB2BUserStoresByb2BUserStorePK(b2BUserStorePK).let { newStoreEmployee ->
			b2BUserStoreRepository.save(newStoreEmployee.apply {
				deleteYn = "Y"
			})
		}
	}
}