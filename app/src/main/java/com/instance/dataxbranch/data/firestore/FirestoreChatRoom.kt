package com.instance.dataxbranch.data.firestore

data class FirestoreChatRoom (var id: Int = -11,
                              var members: List<String> = listOf(""),
                              var recentMessageText: String = "Message here",
                              var recentMessageSendBy: String = "fsid string",
                              var readBy: List<String> = listOf(""),
                              var fsid: String = "",




                              val conversation: List<Pair<String,String>> =listOf(Pair("","")), //[userid,message]
                              var title: String?=null,
                             var topic: String = "topic"
/*chatID (same as the ID of that particular groups row)
members (userID's of all participating app users in that chat, also used for retrieve the chats for an user)
recentMessageText
recentMessageSentAt
recentMessageSendBy
readBy
chatTitle (can be null, since not needed for 1 on 1)
chatType (to indicate whether it is group or 1 on 1 chat)*/
)

