package com.instance.dataxbranch.domain.use_case


import com.instance.dataxbranch.data.firestore.SocialRepository

class GetChatRooms (private val repository: SocialRepository

){
    operator fun invoke() = repository.getChatRoomsFromFirestore()
}