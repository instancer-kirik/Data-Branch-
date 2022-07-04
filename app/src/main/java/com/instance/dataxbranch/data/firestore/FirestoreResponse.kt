package com.instance.dataxbranch.data.firestore

import com.instance.dataxbranch.quests.Quest


data class FirestoreResponse(var id: Int = -11,
                        var fsid: String = "",
                var description: String = "Message here",
                val authorid: String = "",
                var author: String = "default",
                var rating: Int = 0,
                val linkUrl: String="url goes here if you send me a link or a screenshot",


                var subject: String = "subject"
    )

