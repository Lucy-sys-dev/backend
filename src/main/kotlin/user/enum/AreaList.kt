package user.enum

import lombok.Data

/**
 * Create by lucy on 2019-03-07
 **/
@Data
enum class AreaList(var viewName: String) {
	GANGNAM("강남구"),
	GANGDONG("강동구"),
	GANGBOK("강북구"),
	GANGSU("강서구"),
	GWANAK("관악구"),
	GAPYEONG("가평군"),
	GOYANG_DEOGYANG("고양시 덕양구"),
	GOYANG_ILSANDONG("고양시 일산동구"),
	GOYANG_ILSANSEO("고양시 일산서구")
	;

}