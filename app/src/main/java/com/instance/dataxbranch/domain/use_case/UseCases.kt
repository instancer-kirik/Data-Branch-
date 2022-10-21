package com.instance.dataxbranch.domain.use_case

import com.instance.dataxbranch.data.entities.QuestEntity

data class UseCases (

    val getQuests: GetQuests,

    val addQuest: AddQuest,
    val deleteQuest: DeleteQuest,
    val addQuestbyQuest: AddQuestbyQuest,
    val addQuestEntitybyQuest: AddQuestEntitybyQuest,
    val getLocalQuests: GetLocalQuests,
    val addQuestEntity: AddQuestEntity,
    val addNewQuestEntity: AddNewQuestEntity,
    val addNewObjectiveEntityToQuestEntity: AddNewObjectiveEntityToQuestEntity,
    val addObjectiveEntityToQuestEntity: AddObjectiveEntityToQuestEntity,
    val addResponse: AddResponse,
    val getResponses: GetResponses,
    val getChatRooms:  GetChatRooms,
    val addChatRoom:  AddChatRoom,
)