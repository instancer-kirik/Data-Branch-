package com.instance.dataxbranch.data.cloud

data class CloudChat (
    var name: String,
    var text: String,
    var imgUrl: String,
    var createdDate: String,

    var subject: String,



    ) {
    constructor() : this("", "", "", "","")
}
