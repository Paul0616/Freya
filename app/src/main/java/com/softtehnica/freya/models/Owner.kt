package com.softtehnica.freya.models

data class PayloadOwner(
    val payload: Owner,
    val status: Int,
    val message: String,
    val isSuccess: Boolean,
    val errorMessage: String
)

data class Owner(
    val uid: String,
    val name: String,
    val alias: String,
    val logo1: String? = null,
    val logo2: String? = null,
    val modifiedAt: String?
)

//files": [],
//"accounts": [],


//"description": "owner created by Vlad",
//"code": "123",
//"uniqueCode": null,
//"identificationCode": null,
//"email": null,
//"jointStockValue": 0.0,
//"imageUid": null,
//"addressDetails": "Sector 2, Bucuresti (zona Piata Iancului – langa Sectia 8 de Politie)",
//"streetName": "Sos. Mihai Bravu",
//"streetNo": "123-125",
//"building": null,
//"buildingNo": null,
//"floor": null,
//"apartment": null,
//"zipCode": null,
//"countryName": null,
//"countryUid": null,
//"districtName": null,
//"districtUid": null,
//"cityName": null,
//"cityUid": null,

//"isDisabled": false,
//"addedAt": null,
//"addedBy": null,
//"modifiedAt": null,
//"modifiedBy": null,
//"userUid": null