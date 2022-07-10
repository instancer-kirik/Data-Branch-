package com.instance.dataxbranch.domain.use_case


import com.instance.dataxbranch.data.firestore.SocialRepository

class AddChatRoom (
    private val repo: SocialRepository

) {
    suspend operator fun invoke(
        title: String,
        topic: String,
        members: List<String>,
        recentMessageText: String,
        recentMessageSendBy: String,
        /*subject: String,
        description: String,
        author: String,
        authorid:String*/
    ) = repo.addChatRoomToFirestore(title,
        topic,
        members,
    recentMessageText,
    recentMessageSendBy
    )
}