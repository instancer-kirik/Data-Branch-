package com.instance.dataxbranch.data.firestore

data class FirestoreChat (
    var name: String,
    var text: String,
    var imgUrl: String,
    var createdDate: String,

    var subject: String,



    ) {
    constructor() : this("", "", "", "","")
}
