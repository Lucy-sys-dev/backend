package user.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import user.domain.*
import user.enum.AreaGroup
import user.enum.BankCode
import user.payload.CoreCodeRequest
import user.repository.CoreCodeRepository
import user.repository.CoreGroupCodeRepository
import user.repository.RegionsRepository
import user.repository.SettingItemRepository
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Create by lucy on 2019-02-17
 **/
@Service
class CoreDataService (
	@Autowired var coreCodeRepository: CoreCodeRepository,
	@Autowired var coreGroupCodeRepository: CoreGroupCodeRepository,
	@Autowired var regionsRepository: RegionsRepository,
	@Autowired var settingItemRepository: SettingItemRepository
) {
	private val log: Logger = LoggerFactory.getLogger(this.javaClass)

	fun getCities(): ArrayList<Map<String, String>> {
		var cities = ArrayList<Map<String, String>>()

		AreaGroup.values().toList().forEach { data : AreaGroup ->
			var city = HashMap<String, String>()
			city.put("value", data.name)
			city.put("label", data.viewName)
			cities.add(city)
		}
		return cities
	}

	fun getGusByCity(city: String) : ArrayList<Map<String, String>> {
		var gus = ArrayList<Map<String, String>>()

		AreaGroup.values().toList().forEach { data: AreaGroup ->
			if (data.name == city) {
				var items = data.getGroup(city).containProduct
				for (item in items) {
					var gu = HashMap<String, String>()
					gu.put("value", item.name)
					gu.put("label", item.viewName)
					gus.add(gu)
				}
			}
		}
		return gus
	}

	fun getBanks() : ArrayList<Map<String, String>> {
		var banks = ArrayList<Map<String, String>>()
		BankCode.values().toList().forEach { data : BankCode ->
			var bankData = HashMap<String, String>()
			bankData.put("code", data.name)
			bankData.put("label", data.bankName)
			banks.add(bankData)
		}
		return banks
	}

	fun findAll() : List<CoreCode> = coreCodeRepository.findAll()

	fun createCoreCode(coreCodeRequest: CoreCodeRequest) {
		/** validation check
		 *  GroupCode
		 **/

		var coreGroupCode = checkIfCoreGroupCodeIsExist(coreCodeRequest.groupCode!!, coreCodeRequest.groupCodeName!!)
		val newCoreCodePK = CoreCodeId()

		if (coreGroupCode!!.isEmpty) {
			var newCoreGroupCode = CoreGroupCode(
				code = coreCodeRequest.groupCode, name = coreCodeRequest.groupCodeName, serviceType = coreCodeRequest.groupServiceType, description = coreCodeRequest.groupCodeDescription)
			coreGroupCodeRepository.save(newCoreGroupCode)
			newCoreCodePK.groupCode = newCoreGroupCode.code
		} else {
			newCoreCodePK.groupCode = coreCodeRequest.groupCode
		}

		newCoreCodePK.code = coreCodeRequest.code
		newCoreCodePK.serviceType = coreCodeRequest.serviceType

		var newCoreCode = CoreCode(pk = newCoreCodePK, name = coreCodeRequest.name, valueType = coreCodeRequest.valueType,
			codeAttribute1 = coreCodeRequest.codeAttribute1, codeAttribute2 = coreCodeRequest.codeAttribute2, description = coreCodeRequest.description)

		coreCodeRepository.save(newCoreCode)
	}

	fun checkIfCoreGroupCodeIsExist(code: String, name: String) : Optional<CoreGroupCode>? {
		coreGroupCodeRepository.findCoreGroupCodesByCode(code).let {
			newGroupCode -> return  newGroupCode }
		coreGroupCodeRepository.findCoreGroupCodesByName(name).let {
			newGroupCode -> return newGroupCode }
		return null
	}

	fun findRegionsByCitys(): List<Area> = regionsRepository.findRegionsByUpperCodeIsNull()

	fun findReginons(): List<Area> = regionsRepository.findAll()

	fun findSettingItemByItem(item: String, serviceType: String) : SettingItem {
		var serviceTypes = mutableListOf("ALL", serviceType)

		return settingItemRepository.findSettingItemsByItemAndServiceTypeIn(item, serviceTypes)
	}

	fun findSettingItemByServiceType(serviceType: String) : List<SettingItem> {
//		var serviceTypes = mutableListOf("ALL", serviceType)
		var serviceTypes = ArrayList<String>()
		serviceTypes.add("ALL")
		serviceTypes.add(serviceType)

		return settingItemRepository.findSettingItemsByServiceTypeInOrderByOrderNoAsc(serviceTypes)
	}

}