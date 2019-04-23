package user.common.message

/**
 * Create by lucy on 2019-02-10
 **/
 enum class ResultCode constructor(private var code: String, private var message: String): Code {
	SUCCESS("0000", "성공"),
	FAIL("1001", "실패"),
	DB_ERROR("1002", "디비 처리 오류"),
	ILLEGAL_ARGUMENT("1003", "필수값 오류"),
	COOKIE_ERROR("1004", "쿠키가 만료되었습니다."),
	DB_NO_DATA("1005", "데이타가 없습니다."),
	NO_CHANGE_DATA("1006", "변경된 데이터가 없습니다."),
	NO_PERMISSION("1007", "권한이 없습니다.");

	//private var code: String = ""
	//private var message: String = ""

	override open fun getCode(): String {
		return code
	}

	override fun getMessage(): String {
		return message
	}

	private fun ResultCode(code: String, message: String){
		this.code = code
		this.message = message
	}
}