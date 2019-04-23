package user.common.model

import lombok.Data
import user.common.message.Code

/**
 * Create by lucy on 2019-02-10
 **/
@Data
class ResultData {
	var code: String? = null
	var message: String? = null

	var totalCnt: Int = 0
	var currentPage: Int = 0

	var records: Int = 0

	var result: Object? = null

	fun setResultAndMessage(code: Code) {
		this.code = code.getCode()
		this.message = code.getMessage()
	}

	fun setResultAndException(code: Code, e: Throwable) {
		this.code = code.getCode()
		this.message = code.getMessage() + " - " + e.toString()
	}
}