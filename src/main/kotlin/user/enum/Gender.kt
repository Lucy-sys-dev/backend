package user.enum

import lombok.Data

/**
 * Create by lucy on 2019-04-02
 **/
@Data
enum class Gender (val value: String){
	FEMALE("여자"),
	MALE("남성"),
	ALL("남녀공용")
}