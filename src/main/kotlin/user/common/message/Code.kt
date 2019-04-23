package user.common.message

/**
 * Create by lucy on 2019-02-10
 **/
interface Code {
	abstract fun getCode(): String
	abstract fun getMessage(): String
}