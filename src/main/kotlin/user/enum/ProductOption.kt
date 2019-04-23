package user.enum

import lombok.Data

/**
 * Create by lucy on 2019-01-08
 **/

@Data
enum class ProductOption(var viewName: String) {
	CUT("커트"),
	PERM("펌"),
	DYE("염색"),
	CLINIC("클리닉"),
	STYLING("스타일링"),
	MAKEUP("메이크업"),
	SHAMPOO("샴푸"),
	TREATMENT("트리트먼트"),
	SPAY("스프레이");

}