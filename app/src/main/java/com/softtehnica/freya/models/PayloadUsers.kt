package com.softtehnica.freya.models

data class PayloadUsers (
    var payload: PayloadUserFindManyUnsynced,
    val status: Int,
    val message: String,
    val isSuccess: Boolean,
    val errorMessage: String
)

data class PayloadUserFindManyUnsynced(
    val up: RecordsUser,
    val down: List<RecordThatCanBeDeleted>
)

data class RecordThatCanBeDeleted(
    val uid: String
)

data class RecordsUser(
    val records: List<User>
)
