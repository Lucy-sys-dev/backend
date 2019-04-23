package user.common.exception

import user.common.message.Code
import java.lang.RuntimeException

/**
 * Create by lucy on 2019-02-10
 **/
class UserHandlerException constructor(private var code: Code) : RuntimeException() {
	fun getCode(): Code {
		return code
	}
}
