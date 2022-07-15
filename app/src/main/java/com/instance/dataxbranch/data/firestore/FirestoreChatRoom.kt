package com.instance.dataxbranch.data.firestore

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class FirestoreChatRoom
    (//var id: Int = -11,
                              var members: List<String> = listOf(""),
                              var recentMessageText: String = "Message here",
                              var recentMessageName: String = "NAME",
                              var readBy: List<String> = listOf(""),
                              var fsid: String = "",
                              val conversation: List<FirestoreChat> =listOf(), //[userid,message]
                              var title: String?="Title",
                             var subject: String = "subject"
/*chatID (same as the ID of that particular groups row)
members (userID's of all participating app users in that chat, also used for retrieve the chats for an user)
recentMessageText
recentMessageSentAt
recentMessageSendBy
readBy
chatTitle (can be null, since not needed for 1 on 1)
chatType (to indicate whether it is group or 1 on 1 chat)*/
) {
    fun getNow(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter)
        println("Current Date and Time is: $formatted")
        return formatted
    }

}