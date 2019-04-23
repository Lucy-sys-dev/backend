package user.web

import org.hamcrest.Matchers
import org.intellij.lang.annotations.Language
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import user.domain.B2BUser
import user.domain.ReceptStore
import user.domain.Role
import user.repository.B2BUserRepository
import user.security.auth.JwtAuthenticationProvider
import user.security.token.JwtAuthenticationToken
import user.security.token.RawAccessJwtToken
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ReceptStoreControllerTest {
	@Autowired
	private lateinit var mockMvc: MockMvc

//	@MockBean
//	private lateinit var b2BUserRepository: B2BUserRepository

	@MockBean
	private lateinit var jwtAuthenticationProvider: JwtAuthenticationProvider

	@Test
	fun post_signup_returns_200_and_newly_created_user() {

		val body = JSONObject()
			.put("ceoMobile1", "010")
			.put("ceoMobile2", "4008")
			.put("ceoMobile3", "7039")
			.put("job", "점")
			.put("mobile1", "010")
			.put("mobile2", "4008")
			.put("mobile3", "7039")
			.put("serviceType", "HAIR")
			.put("startTime", "0900")
			.put("endTime", "1600")
			.toString()


		mockMvc.perform(
			MockMvcRequestBuilders.post("/api/recept/store")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ReceptStoreControllerTest.jsonReceptStore(ReceptStoreControllerTest.STORE_TEST)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isCreated)
//			.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.`is`(ReceptStoreControllerTest.STORE_TEST.name)))
//			.andExpect(MockMvcResultMatchers.jsonPath("$.seq", Matchers.`is`(ReceptStoreControllerTest.STORE_TEST.seq)))
//			.andExpect(MockMvcResultMatchers.jsonPath("$.storeName", Matchers.`is`(ReceptStoreControllerTest.STORE_TEST.storeName)))
	}

	@Test
	fun get_recept_stores_return() {
		mockMvc.perform(
			MockMvcRequestBuilders.get("/api/recept/stores")
				.header("X-Authorization", DUMMY_TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
			//.content(jsonUser(updatedUser)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)

	}

	@Test
	fun get_recept_stores_by_user_return() {
		val body = JSONObject()
			.put("userName", "김지연")
			.put("mobile1", "010")
			.put("mobile2", "4008")
			.put("mobile3", "7039")
			.toString()

		mockMvc.perform(
			MockMvcRequestBuilders.get("/api/recept/stores/user")
				.header("X-Authorization", DUMMY_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
			.content(body)
			//.content(jsonUser(updatedUser)))
		).andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)

	}

	@Test
	fun get_recept_stores_by_storeId_return() {
		mockMvc.perform(
			MockMvcRequestBuilders.get("/api/recept/stores/confirm/2")
				.header("X-Authorization", DUMMY_TOKEN)
				.contentType(MediaType.APPLICATION_JSON))
			//.content(jsonUser(updatedUser)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)

	}

	@Test
	fun confirm_store() {
		givenLoggedInUser(USER_ADMIN)

//		BDDMockito.given<B2BUser>(b2BUserRepository.save(ArgumentMatchers.any()))
//			.willReturn(updatedUser)

		mockMvc.perform(put("/api/recept/confirm/store/5")
			.header("X-Authorization", DUMMY_TOKEN)
			.contentType(MediaType.APPLICATION_JSON))
				//.content(jsonUser(updatedUser)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
	}

	@Test
	fun delete_store() {
		givenLoggedInUser(USER_ADMIN)

		val updatedUser = USER_ADMIN.copy(role = "ADMIN")

//		BDDMockito.given<B2BUser>(b2BUserRepository.save(ArgumentMatchers.any()))
//			.willReturn(updatedUser)

		mockMvc.perform(delete("/api/recept/store/1")
			.header("X-Authorization", DUMMY_TOKEN)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonUser(updatedUser)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isNoContent)
	}

	companion object {

		private val STORE_TEST = ReceptStore(1, "HAIR", "남영 헤어샵", "CEO", "010", "4008", "7039")

		private val USER_ADMIN =
			B2BUser("test5",  "김지연", "12345", "F", "010", "4008", "7039")

		@Language("JSON")
		private fun jsonReceptStore(receptStore: ReceptStore) = """{
  "name": "${receptStore.userName}",
  "serviceType": "${receptStore.serviceType}",
  "storeName": "${receptStore.storeName}",
  "job": "${receptStore.job}",
  "mobile1": "${receptStore.mobile1}",
  "mobile2": "${receptStore.mobile2}",
  "mobile3": "${receptStore.mobile3}"
}
"""
		@Language("JSON")
		private fun jsonUser(user: B2BUser) = """{
  "id": "${user.id}",
  "name": "${user.name}",
  "password": "${user.password}",
  "sex": "${user.sex}",
  "role": "${user.role}"
}
"""
	}

	private val DUMMY_TOKEN = "Bearer 123j12n31lkmdp012j21d"

	private fun givenLoggedInUser(user: B2BUser) {
//		BDDMockito.given(b2BUserRepository.findById(user.id!!)).willReturn(Optional.of(user))

		BDDMockito.given(jwtAuthenticationProvider.supports(JwtAuthenticationToken::class.java)).willReturn(true)
		BDDMockito.given(jwtAuthenticationProvider.authenticate(jwtAuthenticationToken())).willReturn(
			JwtAuthenticationToken(
				org.springframework.security.core.userdetails.User(
					user.id,
					"",
					listOf(SimpleGrantedAuthority(Role.valueOf(user.role!!).authority()))
				),
				listOf(SimpleGrantedAuthority(Role.valueOf(user.role!!).authority()))
			)
		)
	}

	private fun jwtAuthenticationToken() =
		JwtAuthenticationToken(RawAccessJwtToken(DUMMY_TOKEN.substring(7)))

}