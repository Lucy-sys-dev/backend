package user.common.template

import user.common.model.ResultData

/**
 * Create by lucy on 2019-02-10
 **/
interface ServiceMethodTemplate {
	abstract fun call(): ResultData
}