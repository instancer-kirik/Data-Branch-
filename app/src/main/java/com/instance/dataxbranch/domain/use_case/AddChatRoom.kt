package com.instance.dataxbranch.domain.use_case


import com.instance.dataxbranch.data.firestore.SocialRepository

class AddChatRoom (
    private val repo: SocialRepository

) {
    suspend operator fun invoke(
        subject: String,
        description: String,
        author: String,
        authorid:String
    ) = repo.addChatRoomToFirestore(subject, description, author,authorid)
}