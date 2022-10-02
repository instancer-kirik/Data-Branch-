package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.cloud.QuestsRepository
import com.instance.dataxbranch.data.cloud.ResponseRepository

class AddResponse (
    private val repo: ResponseRepository

    ) {
    suspend operator fun invoke(
        subject: String,
        description: String,
        author: String,
        authorid:String
    ) = repo.addResponseToCloud(subject, description, author,authorid)
}