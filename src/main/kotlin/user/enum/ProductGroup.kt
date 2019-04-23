package user.enum

import java.util.*

/**
 * Create by lucy on 2019-01-08
 **/

enum class ProductGroup(val viewName: String, val containProduct: Array<ProductOption>) {
	STYLE("시술", arrayOf(ProductOption.CUT, ProductOption.PERM)),
	SALE("제품", arrayOf(ProductOption.SHAMPOO, ProductOption.TREATMENT)),
	EMPTY("없음", arrayOf())
	;

	fun findGroup(searchTarget: ProductOption) : ProductGroup {
		return Arrays.stream(ProductGroup.values())
			.filter { t -> hasProductOption(t, searchTarget) }
			.findAny()
			.orElse(EMPTY)
	}

	fun hasProductOption(from: ProductGroup, searchTarget: ProductOption) : Boolean {
		return Arrays.stream(from.containProduct).anyMatch { pay -> pay == searchTarget }
	}

	fun getGroup(searchTarget: String): ProductGroup {
		return Arrays.stream(ProductGroup.values())
			.filter { t -> t.viewName == searchTarget }
			.findAny()
			.orElse(EMPTY)
	}

}

