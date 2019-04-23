package user.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import user.common.message.ResultCode
import user.common.model.ResultData
import user.domain.*
import user.enum.ProductGroup
import user.enum.ProductOption
import user.exception.ResourceAlreadyException
import user.exception.ResourceNotFoundException
import user.payload.ServiceProductRequest
import user.repository.ProductOptionRepository
import user.repository.ProductPriceRepository
import user.repository.ProductRepository

/**
 * Create by lucy on 2019-01-08
 **/
@Service
class ProductService(
	@Autowired var productRepository: ProductRepository,
	@Autowired var productPriceRepository: ProductPriceRepository,
	@Autowired var productOptionRepository: ProductOptionRepository
) {
	private val log: Logger = LoggerFactory.getLogger(this.javaClass)

	@Autowired
	internal var storeService: StoreService? = null

	fun getStyleCategory(): Array<ProductOption> {
		return ProductGroup.STYLE.containProduct
	}

	fun findProductServiceByStoreId(id: Long) : List<ServiceProduct> = productRepository.findProductServiceByStoreId(id)

	fun findAll() : List<ServiceProduct> = productRepository.findAll()

	fun createServiceProduct(serviceProductRequest: ServiceProductRequest) : ResultData {
		val resultData = ResultData()
		/** validation check
		 *  1. store
		**/

		if (!storeService?.checkIfStoreIdIsExist(serviceProductRequest.storeId)!!) {
			resultData.setResultAndException(ResultCode.FAIL, ResourceNotFoundException("STORE", "id", serviceProductRequest.storeId))
			return resultData
		}

		var serviceProduct = ServiceProduct( name = serviceProductRequest.name,
											 storeId = serviceProductRequest.storeId,
											 serviceType = serviceProductRequest.serviceType,
											 duration = serviceProductRequest.duration,
											 manager = serviceProductRequest.manager!!,
											 target = serviceProductRequest.target!!)


		checkIfProductNameIsAleadyUsed(serviceProduct).let { newProduct ->
			productRepository.save(newProduct.apply {
			})
		}.also {
			for( item in serviceProductRequest.prices!!) {
				var serviceProductPricePk = ServiceProductPriceId(serviceProduct.id!!, item.priceFlag, serviceProductRequest.startDate, serviceProductRequest.endDate, serviceProductRequest.startTime, serviceProductRequest.endTime)
				val serviceProductPrice = ServiceProductPrice(pk = serviceProductPricePk, originPrice = item.originPrice, salePrice = item.salePrice)
				productPriceRepository.save(serviceProductPrice)
			}

			for (item in serviceProductRequest.options!!) {
				var serviceProductOptionPk = ServiceProductOptionId(serviceProduct.id!!, item.optionId, item.optionPriceGroupId, serviceProductRequest.startDate, serviceProductRequest.endDate)
				val serviceProductOption = ServiceProductOption(pk = serviceProductOptionPk, deleteYn = "N")
				productOptionRepository.save(serviceProductOption)
			}

			resultData.setResultAndMessage(ResultCode.SUCCESS)
			return resultData
		}
		return resultData
	}

	fun checkIfProductNameIsAleadyUsed(serviceProduct: ServiceProduct) : ServiceProduct =
		when {
			productRepository.findServiceProductsByStoreIdAndName(
				serviceProduct.storeId!!, serviceProduct.name!!
			).isPresent -> throw ResourceAlreadyException("name", "service_product")
			else -> serviceProduct
		}

//	fun checkIfStoreIdIsAlived(storeId: Long) : Boolean =
//			let { storeService.findStoresById(storeId) }
//
//
//			}

}