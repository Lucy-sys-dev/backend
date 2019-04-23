package user.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import user.repository.ReceptStoreRepository
import user.repository.StoreRepository

@Service
class SignupService(
	@Autowired private val receptStoreRepository: ReceptStoreRepository,
	@Autowired private val storeRepository: StoreRepository
) {


}