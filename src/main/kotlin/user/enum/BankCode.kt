package user.enum

import lombok.Data

/**
 * Create by lucy on 2019-03-14
 **/
@Data
enum class BankCode (var bankName: String) {
	GNB("경남은행"),
	GJB("광주은행"),
	KMB("국민은행"),
	IBB("기업은행"),
	NH("농협은행"),
	DGB("대구은행"),
	BSB("부산은행"),
	KDB("산업은행"),
	FSB("상호저축은행"),
	MGB("새마을금고"),
	SH("수협"),
	SHB("신한은행"),
	CU("신용협동조합"),
	CTB("씨티은행"),
	KEB("KEB하나은행"),
	WRB("우리은행"),
	EPB("우체국"),
	JBB("전북은행"),
	JJB("제주은행"),
	SCB("SC제일은행"),
	DSB("도이치은행"),
	KAB("카카오뱅크"),
	KKB("K뱅크")
	;
}