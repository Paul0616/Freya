package com.softtehnica.freya.models

data class User(
    val uid: String,
    val firstName: String?,
    val lastName: String?,
    val fullName: String?,
    val userName: String,
    val email: String,
    val hashedPassword: String? = null,
    val imageUid: String?,
    val claims: String? = null,
    val userGroupName: String?,
    val modifiedAt: String?
)



//"birthDate": "22-11-1992",
//"phoneNumber": "0731760061",
//"files": [],
//"userGroupUid": "294fc421e4b14b10a90bf8211e1d504f",
//"userGroup": null,
//"orderFlag": 0,
//"isDisabled": false,
//"addedAt": "2017-11-22T21:16:34.015791",
//"addedBy": "Vlad Dinu",
//"modifiedAt": null,
//"userUid": "4efda8b04ac248398434efb393a2ce84"