package user.common.controller

import org.intellij.lang.annotations.Language
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import user.common.message.ResultCode
import user.common.model.ResultData
import user.common.template.ServiceMethodTemplate
import user.exception.ResourceAlreadyException
import user.exception.ResourceNotFoundException

/**
 * Create by lucy on 2019-02-10
 **/
open class GeneralRestController:AbstractController() {

	private val log = LoggerFactory.getLogger(GeneralRestController::class.java!!)

	//validation check method
	@ExceptionHandler(BindException::class)
	@ResponseBody
	fun processValidationError(ex: BindException): Any {
		val result = ex.bindingResult
		val resultData = ResultData()
		resultData.code = ResultCode.ILLEGAL_ARGUMENT.getCode()
		resultData.message = ResultCode.ILLEGAL_ARGUMENT.getMessage() + " - " + getValidationErrorString(result)

		return resultData
	}

	//레스트컨트롤러 서비스 호출시 공통 로직.
	protected fun serviceCall(service: ServiceMethodTemplate): ResultData {
		// 모든 결과값을 가지고 있는 map
		return commonServiceCall(service)
	}

	@ExceptionHandler
	fun resourceIsAlready(ex: ResourceAlreadyException): ResponseEntity<String>? =
		error(ex.message!!, HttpStatus.CONFLICT)

	@ExceptionHandler
	fun resourceIsNotFound(ex: ResourceNotFoundException): ResponseEntity<String>? =
		error(ex.message!!, HttpStatus.NOT_FOUND)

	@Language("JSON")
	private fun error(message: String, code: HttpStatus) =
		ResponseEntity.status(code)
			.body("{ \"error\": \"Registration Error\", \"message\": \"$message\" }")
}