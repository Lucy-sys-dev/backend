package user.common.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.validation.BindingResult
import user.common.exception.UserHandlerException
import user.common.message.ResultCode
import user.common.model.ResultData
import user.common.template.ServiceMethodTemplate
import java.lang.RuntimeException

/**
 * Create by lucy on 2019-02-10
 **/
abstract class AbstractController {
	private val log: Logger = LoggerFactory.getLogger(this.javaClass)

	fun commonServiceCall(service: ServiceMethodTemplate) : ResultData {
		var resultData = ResultData()
		try {
			resultData = service.call()
		} catch (e: DataAccessException) {
			log.error("serviceCall DataAccessException err : ", e)
			resultData.setResultAndException(ResultCode.DB_ERROR, e)  //TODO 개발시 반영
//			resultMaster.setResultAndMessage(AdminCode.DB_ERROR);       //TODO 운영시 반영
		} catch (e: UserHandlerException) {  //사용자 정의 Exception
			log.error("serviceCall UserHandlerException err : ", e)
			resultData.setResultAndException(e.getCode(), e) //TODO 개발시 반영
//			resultMaster.setResultAndMessage(e.getCode());      //TODO 운영시 반영
		} catch (e: RuntimeException) {
			log.error("serviceCall RuntimeException err : ", e)
			resultData.setResultAndException(ResultCode.FAIL, e)
		}
		return resultData
	}

	fun getValidationErrorString(result: BindingResult): String {
		val errors = result.fieldErrors
		val sb = StringBuilder()
		sb.append("[")
		for (error in errors) {
			sb.append(error.field)
				.append(" : ")
				.append(error.defaultMessage)
				.append(" ")
		}
		sb.deleteCharAt(sb.length - 1).append("]")
		return sb.toString()
	}
}