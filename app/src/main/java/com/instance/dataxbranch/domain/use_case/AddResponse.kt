package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.firestore.QuestsRepository
import com.instance.dataxbranch.data.firestore.ResponseRepository

class AddResponse (
    private val repo: ResponseRepository

    ) {
    suspend operator fun invoke(
        subject: String,
        description: String,
        author: String,
        authorid:String
    ) = repo.addResponseToFirestore(subject, description, author,authorid)
}