package user.web

import com.fasterxml.jackson.databind.node.ArrayNode
import org.hamcrest.Matchers
import org.intellij.lang.annotations.Language
import org.junit.Test
import org.junit.runner.RunWith
import org.json.JSONArray
import org.json.JSONObject
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*
import user.domain.B2BUser
import user.domain.Role
import user.domain.ServiceProductPrice
import user.payload.B2BEmployeeRequest
import user.payload.ServiceProductRequest
import user.repository.B2BUserRepository
import user.security.auth.JwtAuthenticationProvider
import user.security.config.JwtSettings
import user.security.token.*

@RunWith(SpringRunner::class)
//@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class StoreControllerTest {
	@Autowired
	private lateinit var mockMvc: MockMvc

//	@MockBean
//	private lateinit var usersRepository: B2BUserRepository

	@MockBean
	private lateinit var jwtSettings: JwtSettings

	@MockBean
	private lateinit var jwtAuthenticationProvider: JwtAuthenticationProvider

	//private val jwtToken = Mockito.mock(JwtToken::class.java)

	private val DUMMY_TOKEN_SIGNING_KEY = "&*(ASD*(&S*DSDSDAS"

//	@MockBean
//	private lateinit var jwtTokenFactory: JwtTokenFactory
//
//	@MockBean
//	private lateinit var userDetailsService: UserDetailsService
//
//	@MockBean
//	private lateinit var authenticationManager: AuthenticationManager

	@Test
	fun post_api_auth_login_returns_200_and_token_after_successful_login() {
//		given(usersRepository.findById(USER_ADMIN.id!!)).willReturn(
//			Optional.of(USER_ADMIN.copy(password = BCryptPasswordEncoder().encode(USER_ADMIN.password)))
//		)

		givenJwtSettings()

		mockMvc.perform(
			MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(LOGIN_REQUEST_MIKOLAJ))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.jsonPath("$.token", Matchers.notNullValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.user.id", Matchers.`is`(USER_ADMIN.id)))
	}


	@Test
	fun post_signup_returns_200_and_newly_created_user() {
//		given<B2BUser>(usersRepository.save(ArgumentMatchers.any()))
//			.willReturn(USER_MIKOLAJ)

		mockMvc.perform(
			MockMvcRequestBuilders.post("/api/register/b2b")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonUser(USER_ADMIN)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isCreated)
			.andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.`is`(USER_ADMIN.name)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.`is`(USER_ADMIN.id)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.`is`("")))
	}

//	fun login(userId: String, password: String) : ResultActions {
//		given(usersRepository.findById(USER_ADMIN.id!!)).willReturn(
//			Optional.of(USER_ADMIN.copy(password = BCryptPasswordEncoder().encode(USER_ADMIN.password)))
//		)
//
//		givenJwtSettings()
//
//		return mockMvc.perform(
//			MockMvcRequestBuilders.post("/login")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(LOGIN_REQUEST_MIKOLAJ))
//			.andDo(MockMvcResultHandlers.print())
//
//	}

//	fun extractToken(result: MvcResult) : String {
//		return JsonPath.read(result.response.contentAsString, "$.token")
//	}

	private fun token(expirationTime: Int = 2000): String {
		given(jwtSettings.tokenSigningKey).willReturn(DUMMY_TOKEN_SIGNING_KEY)
		given(jwtSettings.tokenExpirationTime).willReturn(expirationTime)
		val tokenFactory = SettingsBasedJwtTokenFactory(jwtSettings)
		return tokenFactory.generateToken(USER)
	}

	@Test
	//@WithMockUser(username = "test2", password = "12345", roles = arrayOf("ADIMIN"))
	fun get_product_category_returns_200() {
		givenLoggedInUser(USER_ADMIN)

		mockMvc.perform(get("/api/product/style/category")
			.header("X-Authorization", DUMMY_TOKEN)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(jsonUser(updatedUser)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
	}

	@Test
	//@WithMockUser(username = "test2", password = "12345", roles = arrayOf("ADIMIN"))
	fun get_product_service_returns_200() {
		givenLoggedInUser(USER_ADMIN)

		mockMvc.perform(get("/api/product/services/6")
			.header("X-Authorization", DUMMY_TOKEN)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(jsonUser(updatedUser)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
	}

	@Test
	//@WithMockUser(username = "test2", password = "12345", roles = arrayOf("ADIMIN"))
	fun get_store_returns_200() {
		givenLoggedInUser(USER_ADMIN)

		mockMvc.perform(get("/api/stores/6")
			.header("X-Authorization", DUMMY_TOKEN)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(jsonUser(updatedUser)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
	}

	@Test
	//@WithMockUser(username = "test2", password = "12345", roles = arrayOf("ADIMIN"))
	fun get_store_option_rating_returns_200() {
		givenLoggedInUser(USER_ADMIN)

		mockMvc.perform(get("/api/stores/1/options/rating")
			.header("X-Authorization", DUMMY_TOKEN)
			.contentType(MediaType.APPLICATION_JSON))
			//.content(jsonUser(updatedUser)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk)
	}

	@Test
	//@WithMockUser(username = "test2", password = "12345", roles = arrayOf("ADIMIN"))
	fun create_store_member_grade_return_200() {
		givenLoggedInUser(USER_ADMIN)

		val body = JSONObject()
			.put("storeId", "1")
			.put("code", "VIP")
			.put("name", "VIP고객")
			.put("saveRate", "1")
			.put("discountRate", "0.5")
			.toString()

		mockMvc.perform(
			MockMvcRequestBuilders.post("/api/stores/options/membergrade")
				//.header("X-Authorization", DUMMY_TOKEN.substring(7) + token)
				//.header("X-Authorization", "Bearer " + token)
				.header("X-Authorization", DUMMY_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
//				.param("storeId", "1")
//				.param("serviceType", "CUT")
//				.param("onlineYn", "Y")
//				.param("duration", "60")
//				.param("prices", array.toString())
//				.param("desc", "일반컷입니다.")
		).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isCreated)
//			.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.`is`(USER_EMPLYOEE.name)))
//			.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.`is`(USER_EMPLYOEE.id)))
//			.andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.`is`("")))
	}

	@Test
	//@WithMockUser(username = "test2", password = "12345", roles = arrayOf("ADIMIN"))
	fun create_store_schedule_return_200() {
		givenLoggedInUser(USER_ADMIN)

		val array = JSONArray()

		val obj = JSONObject()

		obj.put("dayCode", "SAT")
		obj.put("startTime","0900")
		obj.put("endTime","2200")
		array.put(obj)

//		val obj1 = JSONObject()
//		obj1.put("code", "weekday")
//		obj1.put("name", "평일")
//		obj1.put("dayCode", "TUR")
//		obj1.put("startTime","0900")
//		obj1.put("endTime","2200")
//		array.put(obj1).put(obj)

		val body = JSONObject()
			.put("storeId", "1")
			.put("startDate", "2019-01-01 00:00:00")
			.put("endDate", "9999-12-31 23:59:59")
			.put("optionLists", array)
			.toString()

		mockMvc.perform(
			MockMvcRequestBuilders.post("/api/stores/options/schedule")
				//.header("X-Authorization", DUMMY_TOKEN.substring(7) + token)
				//.header("X-Authorization", "Bearer " + token)
				.header("X-Authorization", DUMMY_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
//				.param("storeId", "1")
//				.param("serviceType", "CUT")
//				.param("onlineYn", "Y")
//				.param("duration", "60")
//				.param("prices", array.toString())
//				.param("desc", "일반컷입니다.")
		).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isCreated)
//			.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.`is`(USER_EMPLYOEE.name)))
//			.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.`is`(USER_EMPLYOEE.id)))
//			.andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.`is`("")))
	}

	private fun givenJwtSettings() {
		given(jwtSettings.tokenIssuer).willReturn("everyonebeauty.com")
		given(jwtSettings.tokenExpirationTime).willReturn(1000)
		given(jwtSettings.tokenSigningKey).willReturn("U9FdVDcRlKRV+WDzSOCmdeKkhR9MWANj1Ksti9GiXPdzgyL4LBGhFLvzDxOkJDg/JvD65s3rbcUda757Re4uAA==")
	}

	companion object {

		private val USER_ADMIN =
			B2BUser("test5",  "김지연", "12345", "F", "010", "4008", "7039")
		private val USER_EMPLYOEE =
			B2BEmployeeRequest("mina84",  "12345", "서미나", 1, "F", "010", "3222", "9245")
//		private val USER_JULIA_WITHOUT_ID =
//			B2BUser(null, "julia", "Julia", "Spolnik", "password1", "julia@mail.com", "Kraków", "Tyniec Team", Role.USER.toString())
//
//		private val USER_ADMIN =
//			B2BUser(2, "admin", "", "", "password2", "admin@mail.com", "Kraków", "Tyniec Team", Role.ADMIN.toString())

		private val USER = User("test2", "", listOf(SimpleGrantedAuthority("ADMIN")))

		private val DUMMY_TOKEN = "Bearer 123j12n31lkmdp012j21d"

		@Language("JSON")
		private fun jsonUser(user: B2BUser) = """{
  "id": "${user.id}",
  "name": "${user.name}",
  "password": "${user.password}",
  "sex": "${user.sex}",
  "mobile1": "${user.mobile1}",
  "mobile2": "${user.mobile2}",
  "mobile3": "${user.mobile3}",
  "role": "${user.role}"
}
"""
		@Language("JSON")
		private fun jsonUser(user: B2BEmployeeRequest) = """{
  "userId": "${user.userId}",
  "name": "${user.name}",
  "password": "${user.password}",
  "sex": "${user.sex}",
  "mobile1": "${user.mobile1}",
  "mobile2": "${user.mobile2}",
  "mobile3": "${user.mobile3}",
  "storeId": "${user.storeId}"
}
"""

		private val LOGIN_REQUEST_MIKOLAJ = """{
	"userId": "${USER_ADMIN.id}",
    "password": "${USER_ADMIN.password}"
}
"""

	}

	private fun givenLoggedInUser(user: B2BUser) {
		//BDDMockito.given(usersRepository.findById(user.id!!)).willReturn(Optional.of(user))

		BDDMockito.given(jwtAuthenticationProvider.supports(JwtAuthenticationToken::class.java)).willReturn(true)
		BDDMockito.given(jwtAuthenticationProvider.authenticate(jwtAuthenticationToken())).willReturn(
			JwtAuthenticationToken(
				User(
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

//	private fun extractToken(): String {
//		return JsonPath.read(result.getResponse().getContentAsString(), "$.token");
//	}


}
