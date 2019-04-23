package user.enum

import java.util.*

/**
 * Create by lucy on 2019-03-07
 **/
enum class AreaGroup(val viewName:String, val containProduct: Array<AreaList>) {
	SEOUL("서울시", arrayOf(AreaList.GANGNAM, AreaList.GANGBOK, AreaList.GANGDONG, AreaList.GANGSU, AreaList.GWANAK)),
	GYEONGGI("경기도", arrayOf(AreaList.GAPYEONG, AreaList.GOYANG_DEOGYANG)),
	EMPTY("없음", arrayOf())
	;

	fun findArea(searchTarget: AreaList) : AreaGroup {
		return Arrays.stream(AreaGroup.values())
			.filter { t -> hasAreaList(t, searchTarget) }
			.findAny()
			.orElse(EMPTY)
	}

	fun hasAreaList(from: AreaGroup, searchTarget: AreaList): Boolean {
		return Arrays.stream(from.containProduct).anyMatch {
			pay -> pay == searchTarget
		}
	}

	fun getGroup(searchTarget: String): AreaGroup {
		return Arrays.stream(AreaGroup.values())
			.filter { t -> t.name == searchTarget }
			.findAny()
			.orElse(EMPTY)
	}

	fun getAreaGroup(): List<AreaGroup> {
		return AreaGroup.values().toList()
	}
}