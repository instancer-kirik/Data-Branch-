package com.instance.dataxbranch.domain.use_case


import com.instance.dataxbranch.data.cloud.SocialRepository

class GetChatRooms (private val repository: SocialRepository

){
    operator fun invoke() = repository.getChatRoomsFromCloud()
}